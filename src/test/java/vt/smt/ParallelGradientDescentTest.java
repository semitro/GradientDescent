package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vt.smt.gd.GradientDescent;
import vt.smt.gd.ParallelGradientDescent;

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
        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        sparkContext = new JavaSparkContext(conf);
    }

    @Test
    public void testAngle45() {
        Double precisely45line[][] = {{1.,1.4, 0.9,  1.}, {2., 2.1, 2.1, 2.}, {3.,3.4, 2.9, 3.}, {300., 300., 300.,301.} };
        List<Double[]> data = new ArrayList<>(Arrays.asList(precisely45line));
        JavaRDD<Double[]> rdd = sparkContext.parallelize(data);
        GradientDescent descent = new ParallelGradientDescent(rdd);
        System.out.println(descent.minimizeErrorFunction(0.1, 0.00001));
    }


    @After
    public void endUp(){
        sparkContext.stop();
    }

}
