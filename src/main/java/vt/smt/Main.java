package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by semitro on 22.09.18.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[8]");
        JavaSparkContext sparkContext= new JavaSparkContext(conf);

        List<Integer[]> l = new ArrayList<>();//Arrays.asList({1, 0} , {2, 2} , 3, 4, 5);
        l.add(new Integer[]{1,2});
        l.add(new Integer[]{3,3});
        l.add(new Integer[]{5, 5});
        l.add(new Integer[]{1,1});
        JavaRDD<Integer[]> data = sparkContext.parallelize(l);
        final Integer[] empty = {0,0};
        System.out.println(Arrays.asList(data.fold(empty, (a, s) -> {
            a[0] += s[0];
            return a;
        })));
    }
}
