package vt.smt.gd;

import java.io.Serializable;

/**
 * Function that computer general error on the whole dataset
 */

@FunctionalInterface
public interface ErrorFunction extends Serializable{
    Double computeError(final Double thetas[], final CostFunction predictor);
}
