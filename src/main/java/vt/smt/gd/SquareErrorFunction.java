package vt.smt.gd;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Collections;

/**
 * Created by semitro on 26.09.18.
 */
public class SquareErrorFunction implements ErrorFunction {
    private JavaRDD<Double[]> dataset;

    public SquareErrorFunction(JavaRDD<Double[]> dataset) {
        this.dataset = dataset;
    }

    @Override
    public Double computeError(Double[] thetas, CostFunction predictor) {
        return dataset.flatMap((FlatMapFunction<Double[], Double>) sample -> {
            final Double prediction = predictor.calculateCostFunction(thetas, sample);
            final Double realValue = sample[sample.length - 1];
            return Collections.singletonList(0.5 * Math.pow((prediction - realValue), 2)).iterator();
        })
        .reduce((a, b) -> a + b);
    }
}
