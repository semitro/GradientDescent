package vt.smt.util;

import java.util.Random;

/**
 * Created by semitro on 28.09.18.
 */
public class DatasetMaker {
    public double[][] makeDataSet(double[] coeffs, double bias, int size, double max, double scatter) {
        Random r = new Random(12); // const seed to make test repeated
        double res[][] = new double[size][coeffs.length + 1];
        for (double[] sample : res) {
            for (int i = 0; i < sample.length-1; i++)
                sample[i] = r.nextDouble()*max;

            for (int i = 0; i < sample.length-1; i++)
                sample[sample.length -1] += sample[i]*coeffs[i] + r.nextDouble()*scatter;

            sample[sample.length - 1] += bias*r.nextDouble()*max + r.nextDouble()*scatter;
        }
        return res;
    }
}
