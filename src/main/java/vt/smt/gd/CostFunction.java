package vt.smt.gd;

import java.io.Serializable;

/**
 * The cost function j(x).
 */
@FunctionalInterface
public interface CostFunction extends Serializable {
    /**
     @param thetas - coefficients
     @param x - vector of arguments
     */
    Double calculateCostFunction(Double[] thetas, Double x[]);
}
