package vt.smt.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Put dataset into file in csv format
 */
public class DatasetToCsvFileWriter {
    public void writeDatasetAsCsv(final Path file, final double[][] dataset) throws IOException {
        try (final BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.WRITE);) {
            for (double[] sample : dataset) {
                writer.write(sampleToCsvStr(sample));
                writer.newLine();
            }
        }
    }

    private String sampleToCsvStr(double[] sample) {
        StringBuilder res = new StringBuilder();
        for (double value : sample) {
            res.append(Double.toString(value));
            res.append(",");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
