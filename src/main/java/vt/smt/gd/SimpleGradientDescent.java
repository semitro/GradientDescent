package vt.smt.gd;

import java.util.ArrayList;
import java.util.List;

/**
 * Non-parallel batch gradient descent
 */
public class SimpleGradientDescent implements GradientDescent {


    public SimpleGradientDescent(double[][] dataset) {
        this.dataset = dataset;
    }

    private double[][] dataset;

    /**
     * @param epsilon - precision. Less epsilon produces more precision
     * @param step    - speed of changing of the coefficients
     **/
    @Override
    public List<Double> minimizeErrorFunction(double epsilon, double step) {
        double avgError = epsilon + 1; // average difference between new thetaj and old thetaj
        double theta0 = 0.; //  the bias of the prediction model
        double nextTheta0 = 0.0;
        final double thetas[] = new double[dataset[0].length - 1];
        final double nextThetas[] = new double[thetas.length];

        while (Math.abs(avgError) > epsilon) {
            avgError = 0.;
            double accum = 0.;
            for (double[] sample : dataset) {
                double prediction = approximateFunction(thetas, sample, theta0);
                accum += sample[sample.length - 1] - prediction;
            }

            nextTheta0 += accum * step / dataset.length;
            for (int t = 0; t < thetas.length; t++) {
                accum = 0.;
                for (double[] sample : dataset) {
                    double prediction = approximateFunction(thetas, sample, theta0);
                    double diff = sample[sample.length - 1] - prediction;
                    avgError += Math.abs(diff);
                    accum += diff * sample[t]; // sample[last] = y real
                }
                nextThetas[t] += step * accum / dataset.length;
            }

            avgError /= thetas.length * dataset.length; // make it average. +1 because of theta0

            System.arraycopy(nextThetas, 0, thetas, 0, thetas.length);
            theta0 = nextTheta0;
        }

        List<Double> res = new ArrayList<>(thetas.length + 1);
        res.add(theta0);
        for (double theta : thetas) res.add(theta);

        return res;
    }

    private double approximateFunction(double thetas[], double x[], double theta0) {
        for (int i = 0; i < thetas.length; i++)
            theta0 += thetas[i] * x[i];

        return theta0;
    }
}
