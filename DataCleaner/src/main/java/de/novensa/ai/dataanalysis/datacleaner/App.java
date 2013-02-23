package de.novensa.ai.dataanalysis.datacleaner;

import de.novensa.ai.dataanalysis.datacleaner.io.CsvFileFilter;
import de.novensa.ai.dataanalysis.datacleaner.util.FileUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

    private static final FileFilter CSV_FILEFILTER = new CsvFileFilter();

    private static String wd = "/Users/Daniel/Downloads/MJFF-binary-files-1/";
    private static String fileIn = "HumDynLog_APPLE_LGE_LGE_A0000028AF9C96_20111220_115329_20111220_120000.tar.bz2";
    private static String fileMedianOut = "HumDynLog_APPLE_LGE_LGE_A0000028AF9C96_20111220_115329_20111220_120000.tar";
    private static String fileFinalOut = "HumDynLog_APPLE_LGE_LGE_A0000028AF9C96_20111220_115329_20111220_120000";
    private static final int buffersize = 2048;

    public static void main(String[] args) throws IOException {
        extract();
        exploreFilesInFolders(new File(wd + fileFinalOut));
    }

    private static Map<String, List<File>> exploreFilesInFolders(File parentFolder) {

        parentFolder.listFiles(CSV_FILEFILTER);
        return null;
    }

    public static void extract() throws IOException {
        // extract *.tar.bz2 -> *.tar
        FileInputStream inMedian = new FileInputStream(wd + fileIn);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(inMedian);

        byte[] buffer = new byte[buffersize];
        FileOutputStream outMedian = new FileOutputStream(wd + fileMedianOut);

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

        String prefix = FilenameUtils.removeExtension(fileMedianOut);

        try {
            while(null != (entry = tarArchiveInputStream.getNextTarEntry())) {
                int count;
                File output = new File(wd, FileUtils.getFileNameWithUnixPrefix(prefix, entry.getName()));

                if (entry.isDirectory()) {
                    if (!output.exists()) {
                        output.mkdirs();
                    }
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(output);
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream, buffersize);
                    while (-1 != (count = tarArchiveInputStream.read(buffer))) {
                        bufferedOutputStream.write(buffer, 0, count);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
            }
        } finally {
            if (null != bufferedOutputStream) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            tarArchiveInputStream.close();
        }

        buffer = new byte[buffersize];
        FileOutputStream outFinal = new FileOutputStream(wd + fileFinalOut);


        while (-1 != (n = tarArchiveInputStream.read(buffer))) {
            outFinal.write(buffer, 0, n);
        }
        inFinal.close();
        bufferedInputStream.close();
        tarArchiveInputStream.close();
        outFinal.close();
    }

}
