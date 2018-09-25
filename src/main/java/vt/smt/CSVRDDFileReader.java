package vt.smt;

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
        return sparkContext.textFile(file)
                .map(str -> (Double[])Arrays.stream(str.split(",")).map(Double::valueOf).toArray());
    }
}
