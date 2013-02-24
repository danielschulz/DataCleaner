package de.novensa.ai.dataanalysis.datacleaner.io;

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

/**
 * Class for extracting archives.
 *
 * @author Daniel Schulz
 */
public class ExtractArchives {

    private static final FileFilter CSV_FILE_FILTER = new CsvFileFilter();
    private static final int BUFFER_SIZE = 2048;

    private static String wd = "/Users/Daniel/Downloads/MJFF-Data/";
    private static String fileIn = "HumDynLog_APPLE_LGE_LGE_A0000028AF9C96_20111220_160634_20111220_170000.tar.bz2";

    private static String fileMedianOut = FilenameUtils.removeExtension(fileIn);
    private static String fileFinalOut = FilenameUtils.removeExtension(fileMedianOut);


    public static ExtractionDeletionInstance extract() throws IOException {
        return extract(ExtractionDeletionStrategy.DEFAULT, CSV_FILE_FILTER);
    }

    public static ExtractionDeletionInstance extract(ExtractionDeletionStrategy extractionDeletionStrategy,
                                                     FileFilter fileFilter) throws IOException {
        // TODO: add NIO here
        // TODO: prove stream-in-stream solution

        final File fileMedian = new File(wd + fileMedianOut);
        final File fileFinal = new File(wd + fileFinalOut);

        // extract *.tar.bz2 -> *.tar
        FileInputStream inMedian = new FileInputStream(wd + fileIn);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(inMedian);

        byte[] buffer = new byte[BUFFER_SIZE];
        FileOutputStream outMedian = new FileOutputStream(fileMedian);

        int n = 0;
        while (-1 != (n = bzIn.read(buffer))) {
            outMedian.write(buffer, 0, n);
        }
        inMedian.close();
        bzIn.close();
        outMedian.close();


        // extract *.tar -> *-folder
        FileInputStream inFinal = new FileInputStream(wd + fileMedianOut);
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
                    File output = new File(wd, fileName);

                    if (entry.isDirectory()) {
                        folders.add(output);

                        if (!output.exists()) {
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
                bufferedOutputStream.close();
            }
            tarArchiveInputStream.close();
        }


        // ExractionDeletionStrategy: eds
        // up from here is just responsible to delete unwanted files in the end
        ExtractionDeletionStrategy eds = extractionDeletionStrategy;
        if (null == extractionDeletionStrategy ||
                ExtractionDeletionStrategy.DEFAULT.equals(extractionDeletionStrategy)) {

            eds = ExtractionDeletionStrategy.DELETE_MEDIAN_EXTRACTION_LEVEL;
        }

        return new ExtractionDeletionInstance(eds, folders, files, fileMedian, fileFinal);
    }

    private static boolean isFilePatternToIgnore(String containingFolder, String fileName) {

        if (null != containingFolder && null != fileName) {

            if (fileName.startsWith("./._") || fileName.isEmpty() || containingFolder.isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
