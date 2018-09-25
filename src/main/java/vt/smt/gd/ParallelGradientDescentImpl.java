package vt.smt.gd;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Calculating gradient descent usi in parallel using Spark
 */
public class ParallelGradientDescentImpl implements GradientDescent, Serializable {

    private JavaRDD<Double[]> dataSet;
    private CostFunction costFunction = new LinearRegression();

    public ParallelGradientDescentImpl(JavaRDD<Double[]> dataset) {
        this.dataSet = dataset;
    }


    @Override
    public List<Double> minimizeErrorFunction(double epsilon, double step) {
        final Double thetas[] = new Double[dataSet.first().length];
        Arrays.fill(thetas, 0.);
        double avgErr = epsilon + 1.;

        while (avgErr > epsilon) {
            avgErr = 0.;
            final Double aug[] = dataSet.map(sample -> {
                        final Double[] augmentation = new Double[sample.length];
                        final Double prediction = costFunction.calculateCostFunction(thetas, sample);
                        final Double diff = sample[sample.length - 1] - prediction;

                        augmentation[augmentation.length - 1] = diff;

                        for (int i = 0; i < augmentation.length - 1; i++) augmentation[i] = diff * sample[i];

                        return augmentation;
                    }
            ).reduce((a, b) -> {
                Double[] c = new Double[a.length];
                for (int i = 0; i < a.length; i++) c[i] = a[i] + b[i];
                return c;
            });

            for (int i = 0; i < thetas.length; i++) {
                thetas[i] += step * aug[i] / dataSet.count();
                avgErr += Math.abs(aug[i])/ dataSet.count();
            }
            System.out.println(Arrays.asList(thetas));
            avgErr /=  thetas.length;
            System.out.println("avgErr: " + avgErr);
        }
        return Arrays.asList(thetas);
    }


    // remember about thetas[last]
}
