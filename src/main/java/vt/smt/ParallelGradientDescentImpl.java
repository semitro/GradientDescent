package vt.smt;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by semitro on 24.09.18.
 */
public class ParallelGradientDescentImpl implements GradientDescent, Serializable {

    public ParallelGradientDescentImpl(JavaRDD<Double[]> dataset) {
        this.dataSet = dataset;
    }

    private JavaRDD<Double[]> dataSet;

    @Override
    public List<Double> minimizeErrorFunction(double epsilon, double step) {
        final Double thetas[] = new Double[dataSet.first().length];
        Arrays.fill(thetas, 0.);
        double avgErr = epsilon + 1.;

        while (avgErr > epsilon) {
            final Double aug[] = dataSet.map(sample -> {
                        final Double[] augmentation = new Double[sample.length];
                        final Double diff = sample[sample.length - 1] - approximateFunction(thetas, sample);

                        System.out.println("diff" + diff);
                        for (int i = 0; i < augmentation.length - 1; i++)
                            augmentation[i] = diff * sample[i];

                        augmentation[augmentation.length - 1] = diff;

                        System.out.println(Arrays.asList(augmentation) + " -naug ");
                        return augmentation;
                    }
            ).reduce((a, b) -> {
                Double[] c = new Double[a.length];
                for (int i = 0; i < a.length; i++) c[i] = a[i] + b[i];
                return c;
            });

            for (int i = 0; i < thetas.length; i++) {
                thetas[i] += step * aug[i] / (double) dataSet.count();
                avgErr += Math.abs(aug[i]);
            }
            avgErr /= dataSet.count()*thetas.length;

        }
        return Arrays.asList(thetas);
    }


    // remember about thetas[last]
    private double approximateFunction(Double thetas[], Double x[]) {
        System.out.println(Arrays.asList(thetas));
        System.out.println("************");
        for (Double aDouble : x) {
            System.out.println(aDouble);
        }
        if (x.length - thetas.length < 0)
            throw new IllegalArgumentException("len of x must be no less than thetas len");
        double res = 0.;
        for (int i = 0; i < thetas.length - 1; i++)
            res += thetas[i] * x[i];

        return res + thetas[thetas.length - 1];
    }
}
