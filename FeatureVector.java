package kahani.ml;

public class FeatureVector {
    private final double[] values;

    public FeatureVector(double... values) {
        this.values = values.clone();
    }

    public double[] getValues() {
        return values.clone();
    }

    public double distanceTo(FeatureVector other) {
        double[] b = other.values;
        if (values.length != b.length) {
            throw new IllegalArgumentException("Feature dimensions must match.");
        }

        double sum = 0.0;
        for (int i = 0; i < values.length; i++) {
            double d = values[i] - b[i];
            sum += d * d;
        }
        return Math.sqrt(sum);
    }
}
