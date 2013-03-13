package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Context;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.ErrorMessages;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionInstance;
import de.novensa.ai.dataanalysis.datacleaner.util.ExtractionDeletionStrategy;
import de.novensa.ai.dataanalysis.datacleaner.util.FileUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.IOUtils.*;

/**
 * Class for extracting archives.
 *
 * @author Daniel Schulz
 */
public class ExtractArchives extends Context {

    private final Context context;

    private static final FileFilter CSV_FILE_FILTER = new CsvFileFilter();

    public ExtractArchives(Context context) {
        this.context = context;
    }

    public ExtractionDeletionInstance extract(String workingDirectory,
                                              File fileWithinWorkingDirectory)
            throws IOException {

        return extract(workingDirectory, fileWithinWorkingDirectory, ExtractionDeletionStrategy.DEFAULT, CSV_FILE_FILTER);
    }

    public ExtractionDeletionInstance extract(String workingDirectory,
                                              File fileWithinWorkingDirectory,
                                              ExtractionDeletionStrategy extractionDeletionStrategy,
                                              FileFilter fileFilter)
            throws IOException {

        // TODO: add NIO here
        // TODO: prove stream-in-stream solution

        final String fileMedianOut = FilenameUtils.removeExtension(fileWithinWorkingDirectory.getName());
        final String fileFinalOut = FilenameUtils.removeExtension(fileMedianOut);
        if (null == fileMedianOut || null == fileFinalOut) {
            throw new IllegalStateException(ErrorMessages.getFilenameInvalid(workingDirectory,
                    fileWithinWorkingDirectory));
        }

        final File fileMedian = new File(workingDirectory + fileMedianOut);
        final File fileFinal = new File(workingDirectory + fileFinalOut);

        // extract *.tar.bz2 -> *.tar
        FileInputStream inMedian = new FileInputStream(fileWithinWorkingDirectory);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(inMedian);

        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream outMedian = new FileOutputStream(fileMedian);

        int n;
        while (-1 != (n = bzIn.read(buffer))) {
            outMedian.write(buffer, 0, n);
        }
        closeQuietly(inMedian);
        closeQuietly(bzIn);
        closeQuietly(outMedian);


        // extract *.tar -> *-folder
        FileInputStream inFinal = new FileInputStream(workingDirectory + fileMedianOut);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inFinal);
        BufferedOutputStream bufferedOutputStream = null;
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(bufferedInputStream);
        TarArchiveEntry entry;

        List<File> folders = new ArrayList<File>();
        List<File> files = new ArrayList<File>();

        final String containingFolderName = FilenameUtils.removeExtension(fileMedianOut);

        try {
            while (null != (entry = tarArchiveInputStream.getNextTarEntry())) {
                int count;

                String folderName_local = FileUtils.getFileNameWithUnixPrefix("", containingFolderName);
                String fileName = FileUtils.getFileNameWithUnixPrefix(folderName_local, entry.getName());

                if (!isFilePatternToIgnore(folderName_local, fileName)) {
                    File output = new File(workingDirectory, fileName);

                    if (entry.isDirectory()) {
                        folders.add(output);

                        if (!output.exists()) {
                            //noinspection ResultOfMethodCallIgnored
                            output.mkdirs();
                        }
                    } else if (fileFilter.accept(output)) {
                        files.add(output);

                        FileOutputStream fileOutputStream = new FileOutputStream(output);
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE);
                        while (-1 != (count = tarArchiveInputStream.read(buffer))) {
                            bufferedOutputStream.write(buffer, 0, count);
                        }
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    }
                }
            }
        } finally {
            if (null != bufferedOutputStream) {
                bufferedOutputStream.flush();
                closeQuietly(bufferedOutputStream);
            }
            closeQuietly(tarArchiveInputStream);
        }


        // ExtractionDeletionStrategy: eds
        // up from here is just responsible to delete unwanted files in the end
        ExtractionDeletionStrategy eds = extractionDeletionStrategy;
        if (null == extractionDeletionStrategy ||
                ExtractionDeletionStrategy.DEFAULT.equals(extractionDeletionStrategy)) {

            eds = ExtractionDeletionStrategy.DELETE_MEDIAN_EXTRACTION_LEVEL;
        }

        return new ExtractionDeletionInstance(eds, workingDirectory, folders, files, fileMedian, fileFinal);
    }

    private static boolean isFilePatternToIgnore(String containingFolder, String fileName) {

        if (null != containingFolder && null != fileName) {

            if (fileName.startsWith("./._") || fileName.isEmpty() || containingFolder.isEmpty()) {
                return true;
            }
        }

        return false;
    }


    @Override
    public Context getContext() {
        return this.context.getContext();
    }
}
