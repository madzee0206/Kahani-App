package kahani.optimization;

public class OptimizationResult {
    private final double initialX;
    private final double finalX;
    private final double initialLoss;
    private final double finalLoss;
    private final int iterations;

    public OptimizationResult(double initialX, double finalX,
                              double initialLoss, double finalLoss,
                              int iterations) {
        this.initialX = initialX;
        this.finalX = finalX;
        this.initialLoss = initialLoss;
        this.finalLoss = finalLoss;
        this.iterations = iterations;
    }

    public double getInitialX() {
        return initialX;
    }

    public double getFinalX() {
        return finalX;
    }

    public double getInitialLoss() {
        return initialLoss;
    }

    public double getFinalLoss() {
        return finalLoss;
    }

    public int getIterations() {
        return iterations;
    }

    @Override
    public String toString() {
        return "x: " + initialX + " -> " + finalX +
                ", loss: " + initialLoss + " -> " + finalLoss +
                ", iterations: " + iterations;
    }
}
