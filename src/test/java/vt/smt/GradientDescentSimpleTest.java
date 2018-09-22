package vt.smt;
/*
 * Created by semitro on 22.09.18.
 */

import org.junit.Test;

import java.util.Random;

public class GradientDescentSimpleTest {

    @Test
    public void testAngle45() {
        double precisely45line[][] = {{1., 1.}, {2., 2.}, {3., 3.}, {300.0, 300.0}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(precisely45line);
        System.out.println(descent.minimizeErrorFunction(0.0005, 0.00001));
    }

    @Test
    public void testWithTheta0() {
        double prec45Line20[][] = {{100., 121.}, {2., 22.}, {3., 25.}, {0.0, 20.}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(prec45Line20);
        System.out.println(descent.minimizeErrorFunction(1., 0.000001));

    }

    @Test
    public void testNearly30() {
        double nearly30[][] = {{1.9, 0.9}, {4.1, 8.12}, {1.7, 0.9}, {100.0, 221.12}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(nearly30);
        System.out.println(descent.minimizeErrorFunction(1., 0.00001));
    }


    @Test
    public void multiDemens(){
        double coeffs[] = {0.1, -4.7, 50., 3., -0.77};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(
                makeDataSet(coeffs, 15.,55,90,0.1 ));
        System.out.println(descent.minimizeErrorFunction(0.85, 0.0001));

    }


    private double[][] makeDataSet(double[] coeffs, double bias, int size, double max, double error) {
        Random r = new Random(12); // const seed to make test repeated
        double res[][] = new double[size][coeffs.length + 1];
        for (double[] sample : res) {
            for (int i = 0; i < sample.length-1; i++)
                sample[i] = r.nextDouble()*max;

            for (int i = 0; i < sample.length-1; i++)
                sample[sample.length -1] += sample[i]*coeffs[i] + r.nextDouble()*error;

            sample[sample.length - 1] += bias + r.nextDouble()*error;
        }
        int a = 5;
        return res;
    }
}
