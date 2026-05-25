public class Polynomial {

    private double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double a = 0;
            double b = 0;

            if (i < this.coefficients.length) {
                a = this.coefficients[i];
            }

            if (i < other.coefficients.length) {
                b = other.coefficients[i];
            }

            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0.0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
