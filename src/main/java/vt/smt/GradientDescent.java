package vt.smt;

import java.util.List;

/**
 * Created by semitro on 22.09.18.
 * @param dataset - rows are values, columns are samples
 */
public interface GradientDescent {
    List<Double> minimizeErrorFunction(double epsilon, double step);
}
