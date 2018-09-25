package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Before;
import org.junit.Test;
import vt.smt.gd.*;
/**
 * Created by semitro on 25.09.18.
 */
public class FileDatasetTesting {
    private JavaSparkContext sparkContext;
    @Before
    public void init() {
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[1]");
        sparkContext = new JavaSparkContext(conf);
    }

    @Test
    public void test1(){
        CSVRDDFileReader fileReader = new CSVRDDFileReader(sparkContext);
        JavaRDD<Double[]> dataset = fileReader.readFromFile("dataset1.csv");
        GradientDescent gradientDescent = new ParallelGradientDescentImpl(dataset);
        gradientDescent.minimizeErrorFunction(0.5, 0.0005);


    }
}
