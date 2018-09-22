package vt.smt;
/*
 * Created by semitro on 22.09.18.
 */

import org.junit.Test;

public class GradientDescentSimpleTest {

    @Test
    public void testAngle45(){
        double precisely45line[][] = { {1., 1.}, {2.,2.}, {3.,3.}};
        SimpleGradientDescentImpl descent = new SimpleGradientDescentImpl(precisely45line);
        System.out.println(descent.minimizeErrorFunction(0.5, 0.1));
    }

    @Test
    public void testWithTheta0(){
      double prec45Line1[][] = { {1., 2.}, {2., 3.}, {3.,4.}};

    }

    public void testNearly30(){
      double nearly30[][] = { {1.9, 0.9}, {4.1, 8.12}, {1.7, 0.9}, {100.0, 221.12}};
    }

    public void multiDimens(){
        double yIs2x5x7xminus2x[][] = { {1.14, 0.85, 1.24, 1.14, 1.15, }, {} };
    }
}
