package vt.smt.gd;

import java.io.Serializable;

/**
 * Created by semitro on 25.09.18.
 */
@FunctionalInterface
public interface CostFunction extends Serializable {
    Double calculateCostFunction(Double[] thetas, Double x[]);
}
