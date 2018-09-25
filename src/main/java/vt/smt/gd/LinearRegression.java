package vt.smt.gd;

/**
 * Using Linear combination as a model of prediction
 *
 */
public class LinearRegression implements CostFunction {

    /**
     * returns linear combination of x and theta: res = x*thetas + thetas[last]
     * notice that thetas[theta.len - 1] is the bias,
     * and x[x.len - 1 ] is not an argument but the actual result
     * for example, if thetas={1, 2, 4} and x={3, 6, 7} the result is 1*3 + 2*6 + 4
     * @param thetas - vector of coefficients
     * @param x - vector of variables
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
