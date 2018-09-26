package vt.smt.gd;

import java.io.Serializable;

/**
 * The cost function represents prediction on the current sample
 */
@FunctionalInterface
public interface CostFunction extends Serializable {
    /**
     @param thetas - coefficients
     @param x - vector of arguments
     */
    Double calculateCostFunction(final Double[] thetas,final Double x[]);
}
