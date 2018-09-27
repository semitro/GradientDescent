package vt.smt.io;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * Class to get JavaRdd<Double[]> from csv file
 */
public class CsvRddFileReader {

    private JavaSparkContext sparkContext;

    public CsvRddFileReader(final JavaSparkContext context) {
        this.sparkContext = context;
    }

    public JavaRDD<Double[]> readFromFile(final String file) {
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
