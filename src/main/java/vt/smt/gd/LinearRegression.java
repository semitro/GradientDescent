package vt.smt.gd;

/**
 * Created by semitro on 25.09.18.
 */
public class LinearRegression implements CostFunction {

    /**
     *
     * notice that thetas[len-1] is theta0 (it's the bias)
     */
    @Override
    public Double calculateCostFunction(Double thetas[], Double x[]) {
        if (x.length - thetas.length < 0)
            throw new IllegalArgumentException("len of x must be no less than thetas len");

        double res = 0.;
        for (int i = 0; i < thetas.length - 1; i++) res += thetas[i] * x[i];

        return res + thetas[thetas.length - 1];
    }
}
