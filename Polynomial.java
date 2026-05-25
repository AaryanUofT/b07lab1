
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {

    private double[] nonZeroCoefficients;
    private int[] exponents;

    private int getMaxExponent() {
        int maxExponent = 0;
        for (int exponent : this.exponents) {
            if (exponent > maxExponent) {
                maxExponent = exponent;
            }
        }
        return maxExponent;
    }

    private int[] generateExponents(double[] coefficients) {
        int count = 0;
        for (double coefficient : coefficients) {
            if (coefficient != 0) {
                count++;
            }
        }
        int[] exponents = new int[count];
        int index = 0;
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                exponents[index++] = i;
            }
        }
        return exponents;
    }

    private double[] generateNonZeroCoefficients(double[] coefficients) {
        int count = 0;
        for (double coefficient : coefficients) {
            if (coefficient != 0) {
                count++;
            }
        }

        double[] nonZeroCoefficients = new double[count];
        int index = 0;

        for (double coefficient : coefficients) {
            if (coefficient != 0) {
                nonZeroCoefficients[index++] = coefficient;
            }
        }

        return nonZeroCoefficients;
    }

    public Polynomial() {
        this.nonZeroCoefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    public Polynomial(double[] nonZeroCoefficients, int[] exponents) {
        this.nonZeroCoefficients = nonZeroCoefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String polynomialStr = scanner.nextLine();
        String[] terms = polynomialStr.replaceAll("-", "+-").split("\\+");
        double[] coefficients = new double[terms.length];
        int[] exponents = new int[terms.length];
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            if (term.contains("x")) {
                String[] parts = term.split("x");
                coefficients[i] = parts[0].isEmpty() || parts[0].equals("+") ? 1 : (parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]));
                exponents[i] = parts.length > 1 ? Integer.parseInt(parts[1].replace("^", "")) : 1;
            } else {
                coefficients[i] = Double.parseDouble(term);
                exponents[i] = 0;
            }
        }
        this.nonZeroCoefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial other) {
        int maxExponent = Math.max(this.getMaxExponent(), other.getMaxExponent());
        double[] resultCoefficients = new double[maxExponent + 1];

        for (int i = 0; i < this.nonZeroCoefficients.length; i++) {
            int exponent = this.exponents[i];
            resultCoefficients[exponent] += this.nonZeroCoefficients[i];
        }

        for (int i = 0; i < other.nonZeroCoefficients.length; i++) {
            int exponent = other.exponents[i];
            resultCoefficients[exponent] += other.nonZeroCoefficients[i];
        }

        return new Polynomial(
                generateNonZeroCoefficients(resultCoefficients),
                generateExponents(resultCoefficients)
        );
    }

    public Polynomial multiply(Polynomial other) {
        int maxExponent = this.getMaxExponent() + other.getMaxExponent();
        double[] resultCoefficients = new double[maxExponent + 1];

        for (int i = 0; i < this.nonZeroCoefficients.length; i++) {
            for (int j = 0; j < other.nonZeroCoefficients.length; j++) {
                int exponent = this.exponents[i] + other.exponents[j];
                resultCoefficients[exponent] += this.nonZeroCoefficients[i] * other.nonZeroCoefficients[j];
            }
        }

        return new Polynomial(
                generateNonZeroCoefficients(resultCoefficients),
                generateExponents(resultCoefficients)
        );
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.nonZeroCoefficients.length; i++) {
            result += this.nonZeroCoefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String filename) throws FileNotFoundException, IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(this.toString());
        writer.close();
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.nonZeroCoefficients.length; i++) {
            double coefficient = this.nonZeroCoefficients[i];
            int exponent = this.exponents[i];
            if (coefficient > 0 && i > 0) {
                str += "+";
                str += coefficient;
            } else {
                str += coefficient;
            }
            if (exponent > 0) {
                str += "x";
                if (exponent > 1) {
                    str += exponent;
                }
            }
        }
        return str;
    }
}