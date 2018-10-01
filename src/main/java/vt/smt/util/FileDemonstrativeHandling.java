package vt.smt.util;

import org.apache.log4j.LogManager;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import vt.smt.gd.*;
import vt.smt.io.CsvRddFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 *  A small dwarf to demonstrate working of the descent at file
 */

public class FileDemonstrativeHandling {
    private JavaSparkContext sparkContext;

    public FileDemonstrativeHandling(final JavaSparkContext javaSparkContext){
       this.sparkContext = javaSparkContext;
    }

    /**
     * Shows how csv dataset in file is processed
     * @param filename - file that contains dataset in csv-format (last number is function result)
     * @return true if errorFunction has decreased, false otherwise
     **/
    public boolean testFileDataset(String filename, double epsilon, double speed) {
        try {
            LogManager.getRootLogger().info("Testing gradient descent at File " + filename +
                    " (" + Files.size(new File(filename).toPath()) / (1024) + " KB)");
        } catch (IOException e) {
            System.err.println("Error during reading file " + filename);
            e.printStackTrace();
            return false;
        }

        LogManager.getRootLogger().info("with epsilon = " + epsilon + ", speed = " + speed);
        final CsvRddFileReader fileReader = new CsvRddFileReader(sparkContext);
        JavaRDD<Double[]> dataset = fileReader.readFromFile(filename);
        Double[] thetas = new Double[dataset.first().length];
        LogManager.getRootLogger().info(thetas.length + " variables are presented in");
        Arrays.fill(thetas, 0.);
        final GradientDescent gradientDescent = new ParallelGradientDescent(dataset);
        final ErrorFunction errorFunction = new SquareErrorFunction(dataset);
        final Double initialError = errorFunction.computeError(thetas, new LinearRegressionCostFunction());
        LogManager.getRootLogger().info("Error function with default coefficients: " + initialError);

        final long startTime = System.currentTimeMillis();
        thetas = (Double[]) gradientDescent.minimizeErrorFunction(epsilon, speed).toArray();
        final long endTime = System.currentTimeMillis();

        LogManager.getRootLogger().info("Got these thetas: \n" + Arrays.asList(thetas));
        final Double resultError = errorFunction.computeError(thetas, new LinearRegressionCostFunction());
        LogManager.getRootLogger().info(String.format("Error function after the descent: %.2f\n", resultError));
        LogManager.getRootLogger().info("Time lapse: " + (endTime - startTime) + " ms");
        return resultError < initialError;
    }
}
