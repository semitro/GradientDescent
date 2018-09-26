package vt.smt.io;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * Created by semitro on 25.09.18.
 */
public class CSVRDDFileReader {

    private JavaSparkContext sparkContext;

    public CSVRDDFileReader(JavaSparkContext context) {
        this.sparkContext = context;
    }

    public JavaRDD<Double[]> readFromFile(String file) {
        JavaRDD<String[]> strings = sparkContext.textFile(file)
                .map(str -> str.split(","));
        JavaRDD<Double[]> rdd = strings.map(strings1 ->{
            double src[] = Arrays.stream(strings1).mapToDouble(Double::valueOf).toArray();
            Double[] arr = new Double[src.length];
            for (int i = 0; i < src.length; i++)
               arr[i] = src[i];
            return arr;
        });
        return rdd;
    }
}
