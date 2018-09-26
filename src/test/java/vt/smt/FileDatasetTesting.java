package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;
import vt.smt.gd.GradientDescent;
import vt.smt.gd.ParallelGradientDescentImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by semitro on 25.09.18.
 */
public class FileDatasetTesting {
    private static JavaSparkContext sparkContext;

    @BeforeClass
    public static void init() {
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[*]");
        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        sparkContext = new JavaSparkContext(conf);
    }

    @Test
    public void test1() {
        testFileDataset("src/main/resources/dataset1.csv", 1., 0.0005);
    }

    @Test
    public void test2() {
        testFileDataset("src/main/resources/dataset2.csv", 5., 0.00000000000005);
    }

    private void testFileDataset(String filename, double epsilon, double speed) {
        try {
            System.out.println("Testing gradient descent at File " + filename +
                    " (" + Files.size(new File(filename).toPath()) / (1024) + " KB)");
        } catch (IOException e) {
            System.err.println("Error during reading file " + filename);
            e.printStackTrace();
            return;
        }
        System.out.println("with epsilon = " + epsilon + ", speed = " + speed);
        final CSVRDDFileReader fileReader = new CSVRDDFileReader(sparkContext);
        JavaRDD<Double[]> dataset = null;
        dataset = fileReader.readFromFile(filename);
        final GradientDescent gradientDescent = new ParallelGradientDescentImpl(dataset);

        final long startTime = System.currentTimeMillis();
        System.out.println(gradientDescent.minimizeErrorFunction(epsilon, speed));
        final long endTime = System.currentTimeMillis();
        System.out.println("Time lapse: " + (endTime - startTime) + " ms");

    }
}
