package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by semitro on 25.09.18.
 */
public class FileDatasetTesting {
    private static FileDemonstrativeHandling fileDemonstrativeHandling;

    @BeforeClass
    public static void init() {
        System.out.println("Testing parallel gradient descent on some datasets");
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[*]");
        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        fileDemonstrativeHandling = new FileDemonstrativeHandling(new JavaSparkContext(conf));
    }

    @Test
    public void test1() {
        testFileDataset("src/main/resources/dataset1.csv", 1., 0.0005);
    }

    @Test
    public void test2() {
        testFileDataset("src/main/resources/dataset2.csv", 5., 0.00000000000005);
    }


    private void testFileDataset(String filename, double epsilon, double speed) {
        assert fileDemonstrativeHandling.testFileDataset(filename, epsilon, speed);
    }
}
