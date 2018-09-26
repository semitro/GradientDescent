package vt.smt.gd;

import java.io.Serializable;

/**
 * Function that computer general error on the whole dataset
 */

@FunctionalInterface
public interface ErrorFunction extends Serializable{
    Double computeError( Double thetas[] , CostFunction predictor);
}
