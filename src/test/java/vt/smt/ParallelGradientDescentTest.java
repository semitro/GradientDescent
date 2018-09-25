package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by semitro on 24.09.18.
 */

public class ParallelGradientDescentTest {
    private JavaSparkContext sparkContext;

    @Before
    public void init() {
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[1]");
        sparkContext = new JavaSparkContext(conf);
    }


    @Test
    public void testAngle45() {
        Double precisely45line[][] = {{0.9, 1., 1., 53.1}, {2., 2.5, 2., 56.}, {3., 3., 3., 59.04}, {300., 297., 302., 851.}};
        List<Double[]> data = new ArrayList<>();
        data.add(precisely45line[0]);
        data.add(precisely45line[1]);
        data.add(precisely45line[2]);
        data.add(precisely45line[3]);
        JavaRDD<Double[]> rdd = sparkContext.parallelize(data);
        GradientDescent descent = new ParallelGradientDescentImpl(rdd);
        System.out.println(descent.minimizeErrorFunction(0.5, 0.000005));
    }
    @Test
    public void verySimple(){
        Double d[] = {0., 1.};
        Double d2[] = {2., 1.};
        List<Double[]> data = new ArrayList<>();
        data.add(d);
        data.add(d2);
        JavaRDD<Double[]> rdd = sparkContext.parallelize(data);
        GradientDescent descent = new ParallelGradientDescentImpl(rdd);
        System.out.println(descent.minimizeErrorFunction(0.05, 0.05));
    }
    @After
    public void endUp(){
        sparkContext.stop();
    }
    @Test
    public void testWithTheta0() {
        Double prec45Line20[][] = {{100., 121.}, {2., 22.}, {3., 25.}, {0.0, 20.}};
        List<Double[]> data = new ArrayList<>(Arrays.asList(prec45Line20));
        JavaRDD<Double[]> rdd = sparkContext.parallelize(data);
        GradientDescent descent = new ParallelGradientDescentImpl(rdd);
        System.out.println(descent.minimizeErrorFunction(1., 0.1 ));
    }
}
