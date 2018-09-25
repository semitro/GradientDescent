package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

/**
 * Created by semitro on 22.09.18.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        for(int i =0; i < 15; i++) {
            if(i > 10)
                return;
        }
        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[8]");
        JavaSparkContext sparkContext= new JavaSparkContext(conf);

        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> data = sparkContext.parallelize(l);
        JavaRDD<Double[]> data2;
    }
}
