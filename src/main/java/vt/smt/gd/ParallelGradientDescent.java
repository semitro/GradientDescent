package vt.smt.gd;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Parallel calculating gradient descent using Spark
 */
public class ParallelGradientDescent implements GradientDescent, Serializable {

    private JavaRDD<Double[]> dataSet;
    private CostFunction costFunction = new LinearRegressionCostFunction();

    public ParallelGradientDescent(final JavaRDD<Double[]> dataset) {
        this.dataSet = dataset;
    }

    @Override
    public List<Double> minimizeErrorFunction(final double epsilon, final double step) {
        // notice that thetas[len-1] is considered the bias
        final Double thetas[] = new Double[dataSet.first().length]; // the coefficients we're looking for
        Arrays.fill(thetas, 0.);
        final long datasetSize = dataSet.count();
        double avgErr = epsilon + 1.;
        double prevErr = Double.MAX_VALUE;

        while (avgErr > epsilon) {
            avgErr = 0.;
            // for each theta we calculate how much do need do add for it
            final Double aug[] = dataSet.map(sample -> calculateAugmentationFromOneSample(sample, thetas))
                    .reduce(this::sumArrays); // and then sum all the augmentations

            for (int i = 0; i < thetas.length; i++) {
                thetas[i] += step * aug[i] / datasetSize; // theta_i = theta_i + 1/m*alpha*aug
                avgErr += Math.abs(aug[i]) / datasetSize;
            }
            avgErr /= thetas.length; // make the error average
            System.out.println(avgErr);
            if (avgErr > prevErr) break; // if the error has increased we don't find better thetas
            prevErr = avgErr;
        }
        return Arrays.asList(thetas);
    }

    private Double[] calculateAugmentationFromOneSample(final Double[] sample, final Double[] thetas){
        // here we remember how much to add for each theta
        final Double[] augmentation = new Double[sample.length];
        final Double prediction = costFunction.calculateCostFunction(thetas, sample); // y_hat
        final Double diff = sample[sample.length - 1] - prediction; // ( y_hat - y )

        augmentation[augmentation.length - 1] = diff; // derivative for theta0 is just (y_hat-y)
        // derivative for others is (y_hat - y)*xi
        for (int i = 0; i < augmentation.length - 1; i++) augmentation[i] = diff * sample[i];

        return augmentation;
    }

    private Double[] sumArrays(final Double[] a, final Double[] b){
        final Double[] c = new Double[a.length];
        for (int i = 0; i < a.length; i++) c[i] = a[i] + b[i];
        return c;
    }
}
