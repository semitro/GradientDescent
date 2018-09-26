package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;
import vt.smt.gd.GradientDescent;
import vt.smt.gd.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Created by semitro on 25.09.18.
 */
public class FileDatasetTesting {
    private static JavaSparkContext sparkContext;

    @BeforeClass
    public static void init() {
        System.out.println("Testing parallel gradient descent on some datasets");
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
        Double[] thetas = new Double[dataset.first().length];
        System.out.println(thetas.length + " variables are presented in");
        Arrays.fill(thetas, 0.);
        final GradientDescent gradientDescent = new ParallelGradientDescentImpl(dataset);
        final ErrorFunction errorFunction = new SquareErrorFunction(dataset);
        final Double initalError = errorFunction.computeError(thetas, new LinearRegression());
        System.out.println("Error function with default coefficients: " + initalError);

        final long startTime = System.currentTimeMillis();
        thetas = (Double[])gradientDescent.minimizeErrorFunction(epsilon, speed).toArray();
        final long endTime = System.currentTimeMillis();

        System.out.println("Got these thetas: \n" + Arrays.asList(thetas));
        final Double resultError = errorFunction.computeError(thetas, new LinearRegression());
        System.out.printf("Error function after the descent: %.2f\n", resultError);
        System.out.println("Time lapse: " + (endTime - startTime) + " ms");
        assert resultError < initalError;
    }
}
