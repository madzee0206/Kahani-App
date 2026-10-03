package kahani.optimization;

import java.util.ArrayList;
import java.util.List;

public class GradientDescent {

    public static double loss(double x) {
        return (x - 3.0) * (x - 3.0);
    }

    public static double gradient(double x) {
        return 2.0 * (x - 3.0);
    }

    public OptimizationResult optimize(double startX, double learningRate,
                                       int iterations) {
        double x = startX;
        double initialLoss = loss(x);

        for (int i = 0; i < iterations; i++) {
            x = x - learningRate * gradient(x);
        }

        return new OptimizationResult(
                startX,
                x,
                initialLoss,
                loss(x),
                iterations
        );
    }

    public List<Double> lossHistory(double startX, double learningRate,
                                    int iterations) {
        List<Double> history = new ArrayList<>();
        double x = startX;

        for (int i = 0; i <= iterations; i++) {
            history.add(loss(x));
            x = x - learningRate * gradient(x);
        }

        return history;
    }

    public double[] linearRegressionStep(double[] x, double[] y,
                                         double weight, double bias,
                                         double learningRate) {
        if (x.length != y.length || x.length == 0) {
            throw new IllegalArgumentException("Invalid training data.");
        }

        double dw = 0.0;
        double db = 0.0;

        for (int i = 0; i < x.length; i++) {
            double prediction = weight * x[i] + bias;
            double error = prediction - y[i];

            dw += error * x[i];
            db += error;
        }

        dw = 2.0 * dw / x.length;
        db = 2.0 * db / x.length;

        weight -= learningRate * dw;
        bias -= learningRate * db;

        return new double[]{weight, bias};
    }
}
