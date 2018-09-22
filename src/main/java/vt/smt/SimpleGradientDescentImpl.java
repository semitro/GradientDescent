package vt.smt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by semitro on 22.09.18.
 */
public class SimpleGradientDescentImpl implements  GradientDescent {


    public SimpleGradientDescentImpl(double[][] dataset){
        this.dataset = dataset;
    }

    private double[][] dataset;
    /*
     *
     * On the one hand, I could not used theta0
     * and fill every first column of each sample by 1 reducing the algorithm to a more general form
     * but on the the other, It's cost to much to fill the whole dataset by 1
     *
     */
    @Override
    public List<Double> minimizeErrorFunction(double epsilon, double step) {
        double augmentation = epsilon + 1; // average difference between new thetaj and old thetaj
        double theta0 = 0.; //  the bias of the prediction model
        double nextTheta0 = 0.0;
        final Double thetas[]     = new Double[dataset.length];
        final double nextThetas[] = new double[dataset.length];

        while( augmentation > epsilon){
            for(int t = 0; t < dataset.length; t++){
                double accum = 0.;
                for(double[] sample : dataset){
                    double prediction = approximateFunction(thetas, sample, theta0);
                    accum      += sample[sample.length -1 ] * prediction * sample[t];
                    nextTheta0 += sample[sample.length -1 ] * prediction;
                }
                nextThetas[t] += step*accum/dataset.length;
                augmentation += nextThetas[t] - thetas[t];
            }
            nextTheta0 *= step/dataset.length;
            augmentation += nextTheta0 - theta0;
            augmentation /= thetas.length + 1; // make it average. +1 because of theta0

            System.arraycopy(nextThetas, 0, thetas, 0, thetas.length);
            theta0 = nextTheta0;
        }

        List<Double> res = new ArrayList<>(thetas.length+1);
        res.set(0, theta0);
        res.addAll( Arrays.asList(thetas));

        return res;
    }

    private double approximateFunction(Double thetas[], double x[], double theta0){
        for (int i = 0; i < thetas.length ; i++)
           theta0 += thetas[i]*x[i];

        return  theta0;
    }
}
