package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by semitro on 22.09.18.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[8]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        CSVRDDFileReader fileReader = new CSVRDDFileReader(sparkContext);

    }
}
