package src.hilbert;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DoubleMatrix {

    public double[][] matrix;
    private int height;
    private int width;

    public static void main(String[] args) {
        DoubleMatrix mat0 = new DoubleMatrix(3, 3);
        double num0 = 1;
        for (int j = 0; j < mat0.getHeight(); j++) {
            for (int i = 0; i < mat0.getWidth(); i++) {
                mat0.insert(num0, i, j);
                num0 = 2 * num0 + 1;
            }
        }
        mat0.printMatrix();
        // Matrix mat1 = new Matrix(2, 3);
        // Fraction num1 = new Fraction(0, 1);
        // for (int j = 0; j < mat1.getHeight(); j++) {
        //     for (int i = 0; i < mat1.getWidth(); i++) {
        //         mat1.insert(num1, i, j);
        //         num1 = num1.add(new Fraction(1, 1));
        //     }
        // }
        DoubleMatrix mat1 = mat0.transpose();
        mat1.printMatrix();
    }

    public DoubleMatrix(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Must have height and width of at least one");
        }
        matrix = new double[width][height];
        // for (int i = 0; i < width; i++) {
        //     for (int j = 0; j < height; j++) {
        //         matrix[i][j] = 0.0;
        //     }
        // }
        this.width = width;
        this.height = height;
    }

    public DoubleMatrix( double[][] data ) {
        matrix = data;
        height = data.length;
        width = data.length;
    }

    public void insert(double insert, int i, int j) {
        matrix[i][j] = insert;
    }

    public DoubleMatrix addMatrix(DoubleMatrix addTo) {
        if (height != addTo.getHeight() && width != addTo.getWidth()) {
            throw new IllegalArgumentException();
        }
        DoubleMatrix result = new DoubleMatrix(height, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sum = this.getValue(i, j) + addTo.getValue(i, j);
                result.insert(sum, i, j);
            }
        }
        return result;
    }

    public DoubleMatrix subtractMatrix(DoubleMatrix subtractThis) {
        DoubleMatrix result = new DoubleMatrix(subtractThis.getHeight(), subtractThis.getWidth());
        for (int j = 0; j < subtractThis.getHeight(); j++) {
            for (int i = 0; i < subtractThis.getWidth(); i++) {
                result.insert(-subtractThis.getValue(i, j), i, j);
            }
        }
        return this.addMatrix(result);
    }

    public DoubleMatrix multiplyMatrix(DoubleMatrix factor) {
        if (width != factor.getHeight()) {
            throw new IllegalArgumentException();
        }
        DoubleMatrix result = new DoubleMatrix(height, factor.getWidth());
        double sum;
        int i;
        int j;
        int k;
        for (i = 0; i < factor.getWidth(); i++) {
            for (j = 0; j < height; j++) {
                sum = 0.0;
                for (k = 0; k < factor.getHeight(); k++) {
                    sum += this.getValue(k, j) * factor.getValue(i, k);
                }
                result.insert(sum, i, j);
            }
        }
        return result;
    }

    public DoubleMatrix scale(double scaler) {
        DoubleMatrix copy = new DoubleMatrix(height, width);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                copy.insert(matrix[i][j] * scaler, i, j);
            }
        }
        return copy;
    }

    public double getValue(int width, int height) {
        return matrix[width][height];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void printMatrix() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public DoubleMatrix transpose() {
        DoubleMatrix copy = new DoubleMatrix(width, height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                copy.insert(matrix[i][j], j, i);
            }
        }
        return copy;
    }

    public DoubleMatrix copy() {
        DoubleMatrix copy = new DoubleMatrix(height, width);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                copy.insert(matrix[i][j], i, j);
            }
        }
        return copy;
    }

    public double magnitude() {
        if (width != 1) {
            throw new IllegalArgumentException("This is not a vector");
        }
        double sum = 0.0;
        for (int j = 0; j < height; j++) {
            sum += Math.pow(matrix[0][j], 2);
        }
        return Math.sqrt(sum);
    }

    public static DoubleMatrix identity(int dim) {
        DoubleMatrix i = new DoubleMatrix(dim, dim);
        for (int j = 0; j < dim; j++) {
            for (int k = 0; k < dim; k++) {
                if (j == k) {
                    i.insert(1.0, j, k);
                }
            }
        }
        return i;
    }

    public String toString() {
        return Arrays.stream( matrix )
            .map( x -> Arrays.stream( x )
                    .mapToObj( y -> String.format( "%4.3f", y ) )
                    .collect( Collectors.joining( "  " ) ) )
            .collect( Collectors.joining( "\n" ) );
    }
}
