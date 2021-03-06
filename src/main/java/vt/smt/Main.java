package vt.smt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import vt.smt.util.FileDemonstrativeHandling;

import java.io.File;
import java.nio.file.Files;

/**
 *  Test task on the parallel gradient descent performed by Oshchepkov Artem
 */

public class Main {
    public static void main(String[] args) {
        if (!(args.length == 3 || args.length == 4)) {
            System.out.println("Usage: file.csv epsilon alpha master-url");
            System.exit(0);
        }

        if (Files.notExists(new File(args[0]).toPath()) ||
                !Files.isReadable(new File(args[0]).toPath())) {

            System.err.println("Error: file " + args[0] + " does not exist (or there's no access to read)");
            //System.exit(1);
        }
        try {
            Double.valueOf(args[1]);
            Double.valueOf(args[2]);
        } catch (NumberFormatException nfe) {
            System.err.println("make sure that epsilon and alpha are real numbers");
            System.exit(2);
        }
        final SparkConf conf = args.length == 4 ? new SparkConf().setAppName("GD").setMaster(args[3]) : new SparkConf();
        final JavaSparkContext sparkContext = new JavaSparkContext(conf);

        Logger.getLogger("akka").setLevel(Level.OFF);
        Logger.getLogger("org").setLevel(Level.OFF);
        final FileDemonstrativeHandling fileDemonstrativeHandling = new FileDemonstrativeHandling(sparkContext);
        fileDemonstrativeHandling.testFileDataset(args[0], Double.valueOf(args[1]), Double.valueOf(args[2]));
    }
}
