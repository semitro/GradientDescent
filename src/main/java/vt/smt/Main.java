package vt.smt;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import vt.smt.gd.GradientDescent;
import vt.smt.gd.ParallelGradientDescent;
import vt.smt.io.CSVRDDFileReader;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by semitro on 22.09.18.
 */

public class Main {
    public static void main(String[] args) {
        if (args.length != 3 ) {
            System.out.println("Usage: file.csv epsilon alpha");
            System.exit(0);
        }
        if(Files.notExists(new File(args[0]).toPath()) ||
                !Files.isReadable(new File(args[0]).toPath())){
            System.err.println("Error: file " + args[0] + " does not exist (or there's no access to read)");
            System.exit(1);
        }

        final SparkConf conf = new SparkConf().setAppName("GD").setMaster("local[8]");
        final JavaSparkContext sparkContext = new JavaSparkContext(conf);
        final CSVRDDFileReader fileReader = new CSVRDDFileReader(sparkContext);


        final GradientDescent minimaizer = new ParallelGradientDescent(fileReader.readFromFile(args[1]));
        try{
            minimaizer.minimizeErrorFunction(Double.valueOf(args[1]), Double.valueOf(args[2]));
        }catch (NumberFormatException nfe){
            System.err.println(" make sure that epsilon and alpha are real numbers");
            System.exit(2);
        }
    }
}
