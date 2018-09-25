package vt.smt.gd;

import java.util.List;

/**
 * This interface provides calculation of the gradient descent
 */
public interface GradientDescent {
    /**
     * Adjust the coefficients of the predictor's function
     * @param epsilon - precision
     * @param step    - speed of learning
     */
    List<Double> minimizeErrorFunction(double epsilon, double step);
}
