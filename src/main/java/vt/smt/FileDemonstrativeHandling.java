package vt.smt;

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
            System.out.println("Testing gradient descent at File " + filename +
                    " (" + Files.size(new File(filename).toPath()) / (1024) + " KB)");
        } catch (IOException e) {
            System.err.println("Error during reading file " + filename);
            e.printStackTrace();
            return false;
        }

        System.out.println("with epsilon = " + epsilon + ", speed = " + speed);
        final CsvRddFileReader fileReader = new CsvRddFileReader(sparkContext);
        JavaRDD<Double[]> dataset = fileReader.readFromFile(filename);
        Double[] thetas = new Double[dataset.first().length];
        System.out.println(thetas.length + " variables are presented in");
        Arrays.fill(thetas, 0.);
        final GradientDescent gradientDescent = new ParallelGradientDescent(dataset);
        final ErrorFunction errorFunction = new SquareErrorFunction(dataset);
        final Double initialError = errorFunction.computeError(thetas, new LinearRegressionCostFunction());
        System.out.println("Error function with default coefficients: " + initialError);

        final long startTime = System.currentTimeMillis();
        thetas = (Double[]) gradientDescent.minimizeErrorFunction(epsilon, speed).toArray();
        final long endTime = System.currentTimeMillis();

        System.out.println("Got these thetas: \n" + Arrays.asList(thetas));
        final Double resultError = errorFunction.computeError(thetas, new LinearRegressionCostFunction());
        System.out.printf("Error function after the descent: %.2f\n", resultError);
        System.out.println("Time lapse: " + (endTime - startTime) + " ms");
        return resultError < initialError;
    }
}
