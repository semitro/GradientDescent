package vt.smt;
/*
 * Created by semitro on 22.09.18.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import vt.smt.gd.GradientDescent;
import vt.smt.gd.SimpleGradientDescentImpl;

import java.util.Random;

public class GradientDescentSimpleTest {

    @BeforeClass
    public static void info(){
        System.out.println("**************************************");
        System.out.println("Testing non-parallel gradient descent");
        System.out.println("In these tests we'll compute coefficients for several simple functions");
        System.out.println("So we'll calculate a, b, c for y = ax1 + bx2 + c");
        System.out.println("If it's nearly fit dataset {x1, x2, y} then descent works right");
        System.out.println("(format: [theta0, theta1, theta2])");
        System.out.println("======================================");
    }
    @AfterClass
    public static void endUp(){
        System.out.println("tests for non-parallel descent are over");
        System.out.println("***************************************\n");
    }
    @Test
    public void testAngle45() {
        double precisely45line[][] = {{1., 1.}, {2., 2.}, {3., 3.}, {300.0, 300.0}};
        doTest(precisely45line, 0.0005, 0.00001);
    }

    @Test
    public void testWithTheta0() {
        double prec45Line20[][] = {{100., 121.}, {2., 22.}, {3., 25.}, {0.0, 20.}};
        doTest(prec45Line20, 1., 0.000001);
    }

    @Test
    public void multiDemens(){
        double coeffs[] = {0.1, -4.7, 50., 3., -0.77};
        doTest(makeDataSet(coeffs, 15., 55, 90, 0.1), 0.85, 0.0001);
    }

    private void doTest(double[][] dataset, double epsilon, double step){
        System.out.print("Dataset:\n[ ");
        for (double v : dataset[0]) {
            System.out.print(Double.toString(v) + " ");
        }
        System.out.println("]");
        System.out.println("with epsilon = " + epsilon + " speed = " + step);
        GradientDescent gradientDescent = new SimpleGradientDescentImpl(dataset);
        System.out.println("These coefficients were computed:\n"
                + gradientDescent.minimizeErrorFunction(epsilon, step));

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
