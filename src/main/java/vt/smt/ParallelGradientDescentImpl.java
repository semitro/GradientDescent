package vt.smt;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.util.MutablePair;

import java.util.Arrays;
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
        double avgErr = epsilon + 1.;
        MutablePair<Double, Double> res = null;
        while (avgErr > epsilon) {
            for (int t = 0; t < thetas.length; t++) {
                final int currentTheta = t;
                res = dataSet.aggregate(new MutablePair<Double, Double>(0., 0.), (acc, sample) -> {
                    double prediction = approximateFunction(thetas, sample);
                    double diff = sample[sample.length - 1] - prediction;
                    acc._2 += Math.abs(diff);
                    acc._1 += diff * sample[currentTheta];
                    return acc;
                }, (acc1, acc2) -> new MutablePair<>(acc1._1() + acc2._1(), acc2._2() + acc2._2()));
                nextThetas[t] += res._1() * step / dataSet.count();
                avgErr = res._2() / (dataSet.count() * thetas.length);
//            Double derivativeByTheta = dataSet.aggregate(0., (accum, sample) ->
//                            (accum + approximateFunction(thetas, sample) - sample[sample.length - 1]) *
//                                    ((currentTheta != thetas.length - 1) ? sample[currentTheta] : 1)
//                    , (acc1, acc2) -> acc1 + acc2);
//
//            nextThetas[currentTheta] = step * derivativeByTheta / dataSet.count(); // count?
            }
            System.arraycopy(nextThetas, 0, thetas, 0, thetas.length);
        }
        return Arrays.asList(thetas);
    }

    // remember about thetas[last]
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
