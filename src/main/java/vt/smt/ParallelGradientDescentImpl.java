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
        //final Double nextThetas[] = new Double[thetas.length];
        Arrays.fill(thetas, 0.);
        //Arrays.fill(nextThetas, 0.);
        double avgErr = epsilon + 1.;
//      MutablePair<Double, Double> res = null;
//
//        while (avgErr > epsilon) {
//            avgErr = 0.;
//            for (int t = 0; t < thetas.length; t++) {
//                final int currentTheta = t;
//                res = dataSet.aggregate(new MutablePair<>(0., 0.), (acc, sample) -> {
//                    double diff = sample[sample.length - 1] - approximateFunction(thetas, sample);
//                    System.out.println("diff: " + diff);
//                    acc._2 += Math.abs(diff);
//                    if (thetas.length - 1 == currentTheta)
//                        acc._1 += diff;
//                    else
//                        acc._1 += diff * sample[currentTheta]; //currentTheta == thetas.length - 1 ? 1. : sample[currentTheta];
//                    System.out.println("acc_1 " + acc._1());
//                    return acc;
//                }, (acc1, acc2) -> new MutablePair<>(acc1._1() + acc2._1(), acc1._2() + acc2._2()));
//                nextThetas[currentTheta] += step * res._1() / dataSet.count();
//                avgErr = res._2() / (dataSet.count() * thetas.length);
//            }
//
//            System.arraycopy(nextThetas, 0, thetas, 0, thetas.length);
//        }

//        while (avgErr > epsilon) {
//            avgErr = 0.;
//            for (int t = 0; t < thetas.length-1; t++) {
//                final int currentTheta = t;
//                Double res = dataSet.aggregate(0., (acc, s) -> {
//                    double diff = s[s.length - 1] - approximateFunction(thetas, s);
//                    if (thetas.length - 1 == currentTheta)
//                        acc += diff;
//                    else acc += diff * s[currentTheta];
//                    return acc;
//                }, (a, b) -> a + b);
//                nextThetas[currentTheta] += step * res / dataSet.count();
//                avgErr += nextThetas[currentTheta] - thetas[currentTheta];
//            }
//            avgErr /= dataSet.count();
//            System.arraycopy(nextThetas, 0, thetas, 0, thetas.length);
//        }

        while (avgErr > epsilon) {
            avgErr = 0.;

            final Double aug[] = dataSet.map(sample -> {
                final Double[] augmentation = new Double[sample.length];
                final Double diff = sample[sample.length - 1] - approximateFunction(thetas, sample);

                System.out.println("diff" + diff);
                System.out.println(Arrays.asList(augmentation) + "aug " + Arrays.asList(sample) + " - sample");

                for (int i = 0; i < augmentation.length - 1; i++)
                    augmentation[i] = diff * sample[i];
                augmentation[augmentation.length - 1] = diff;
                System.out.println(Arrays.asList(augmentation) + " -naug ");
                return augmentation;
                }
            ).reduce((a, b)->{Double[] c = new Double[a.length]; for(int i = 0; i < a.length; i++) c[i] = a[i] + b[i]; return c;});


//            Double aug[] = dataSet.fold(empty, (augmentation, sample) -> {
//                final Double diff = sample[sample.length - 1] - approximateFunction(thetas, sample);
//                System.out.println("diff" + diff);
//                System.out.println(Arrays.asList(augmentation) + "aug " + Arrays.asList(sample) + " - sample");
//
//                for (int i = 0; i < augmentation.length - 1; i++)
//                    augmentation[i] += diff * sample[i];
//
//                augmentation[augmentation.length - 1] += diff;
//                return augmentation;
//            });

            for (int i = 0; i < thetas.length; i++) {
                thetas[i] += step * aug[i] / (double) dataSet.count();

                avgErr += Math.abs(aug[i] / (double) dataSet.count());
            }
            avgErr /= thetas.length;
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
