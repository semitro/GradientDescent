package vt.smt;

import java.util.List;

/**
 * Created by semitro on 22.09.18.
 */
public interface GradientDescent {
    List<Float> minimizeErrorFunction(List<Float> coef, float[] dataset);
}
