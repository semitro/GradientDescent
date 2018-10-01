package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;
import vt.smt.io.DatasetToCsvFileWriter;
import vt.smt.util.DatasetMaker;
import vt.smt.util.FileDemonstrativeHandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Testing on csv datasets
 */
public class FileDatasetTest {
    private static FileDemonstrativeHandling fileDemonstrativeHandling;
    private static final String fs = System.lineSeparator();
    private static final String datasetsDir = "src"+fs+"main"+fs+"resources" + fs;
    private final DatasetMaker datasetMaker = new DatasetMaker();

    @BeforeClass
    public static void init() {
        System.out.println("Testing parallel gradient descent on some datasets");
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[*]");
        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        fileDemonstrativeHandling = new FileDemonstrativeHandling(new JavaSparkContext(conf));
    }

    @Test
    public void test1() {
        testFileDataset(datasetsDir + "dataset1.csv", 1., 0.0005);
    }

    @Test
    public void test2() {
        testFileDataset(datasetsDir + "dataset2.csv", 5., 0.00000000000005);
    }

    @Test
    public void testVeryManyPoints(){
        final int datasetSize = 1000000;
        System.out.println("Generating dataset on " + datasetSize + " points with 5 rows");
        System.out.println("====AWARE: IT'S A LONG TEST :AWARE====");
        final double[] thetasForGeteratedDataset = {2., 12., 85, 107.};
        final double shift = 2.0;
        final DatasetToCsvFileWriter datasetToCsvFileWriter = new DatasetToCsvFileWriter();
        Path datasetFile = null;
        try {
            datasetFile = Files.createTempFile("generatedDataset", "csv");
            datasetToCsvFileWriter.writeDatasetAsCsv(datasetFile,
                    datasetMaker.makeDataSet(thetasForGeteratedDataset, shift, datasetSize, 100., 25.));
        } catch (IOException e) {
            System.err.println("Can't write to temp-file in testMillionPoints!");
            e.printStackTrace();
            assert false;
        }
        System.out.println("Dataset is generated");
       assert fileDemonstrativeHandling.testFileDataset(datasetFile.toString(), 25.0, 0.00005);
    }


    private void testFileDataset(String filename, double epsilon, double speed) {
        assert fileDemonstrativeHandling.testFileDataset(filename, epsilon, speed);
    }
}
