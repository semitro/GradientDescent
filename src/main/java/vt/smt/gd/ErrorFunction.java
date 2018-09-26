package vt.smt.gd;

import java.io.Serializable;

/**
 * Created by semitro on 26.09.18.
 */
@FunctionalInterface
public interface ErrorFunction extends Serializable{
    Double computeError( Double thetas[] , CostFunction predictor);
}
