package vt.smt;
/*
 * Created by semitro on 22.09.18.
 */

import org.junit.Test;

public class GradientDescentSimpleTest {

    @Test
    public void testAngle45(){
        double precisely45line[][] = { {1., 1.}, {2.,2.}, {3.,3.}, {300.0, 300.0}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(precisely45line);
        System.out.println(descent.minimizeErrorFunction(0.00005, 0.000001));
    }

    @Test
    public void testWithTheta0(){
      double prec45Line1[][] = { {100., 121.}, {2., 22.}, {3.,25.}, {0.0, 20.}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(prec45Line1);
        System.out.println(descent.minimizeErrorFunction(0.0000005, 0.0001));

    }
    @Test
    public void testNearly30(){
      double nearly30[][] = { {1.9, 0.9}, {4.1, 8.12}, {1.7, 0.9}, {100.0, 221.12}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(nearly30);
        System.out.println(descent.minimizeErrorFunction(0.00005, 0.00001));
    }
    @Test
    public void multiDimens(){
        double yIs2x5x7xminus2x[][] = { {1.14, 0.85, 1.24, 1.14, 1.15, }, {} };
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(yIs2x5x7xminus2x);
        System.out.println(descent.minimizeErrorFunction(0.00005, 0.01));
    }
}
