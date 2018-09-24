package vt.smt;

import org.apache.spark.api.java.JavaRDD;

import java.util.List;

/**
 * Created by semitro on 24.09.18.
 */
public class ParallelGradientDescentImpl implements GradientDescent {

    public ParallelGradientDescentImpl(JavaRDD<Double[]> dataset) {
        this.dataSet = dataset;
    }

    private JavaRDD<Double[]> dataSet;

    @Override
    public List<Double> minimizeErrorFunction(double epsilon, double step) {

        final Double thetas[] = new Double[dataSet.first().length - 1];
        final Double nextThetas[] = new Double[thetas.length];

        for (int t = 0; t < thetas.length; t++) {
            final int currentTheta = t;
            Double derivativeByTheta = dataSet.aggregate(0., (accum, sample) ->
                            (accum + approximateFunction(thetas, sample) - sample[sample.length -1]) *
                                    ((currentTheta != thetas.length - 1 ) ? sample[currentTheta] : 1)
                    , (acc1, acc2) -> acc1 + acc2);

            nextThetas[currentTheta] = step*derivativeByTheta/dataSet.count(); // count?
        }

        return null;
    }

    private double approximateFunction(Double thetas[], Double x[]) {
        if (thetas.length - x.length != 1)
            throw new IllegalArgumentException("Array of thetas must have len == x.len - 1");

        double res = 0.;
        for (int i = 0; i < thetas.length - 1; i++)
            res += thetas[i] * x[i];
        res += thetas[thetas.length - 1];
        return res;
    }
}
