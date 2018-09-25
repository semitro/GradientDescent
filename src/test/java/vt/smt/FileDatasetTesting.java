package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[*]");
        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        sparkContext = new JavaSparkContext(conf);
    }

    @Test
    public void test1(){
        testFileDataset("src/main/resources/dataset1.csv", 1., 0.0005);
    }

    @Test
    public void test2(){
        testFileDataset("src/main/resources/dataset23.csv", 5., 0.00000000000005);
    }

    private void testFileDataset(String filename, double epsilon, double speed){
        CSVRDDFileReader fileReader = new CSVRDDFileReader(sparkContext);
        JavaRDD<Double[]> dataset = fileReader.readFromFile(filename);
        GradientDescent gradientDescent = new ParallelGradientDescentImpl(dataset);
        gradientDescent.minimizeErrorFunction(epsilon, speed);

    }
}
