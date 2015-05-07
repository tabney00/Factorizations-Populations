package src.hilbert;

import java.util.Scanner;
import java.util.LinkedList;

public class HilbertMatrix {

    public DoubleMatrix array;
    private int dimension;


    public HilbertMatrix(int dim) {
        dimension = dim;
        array = new DoubleMatrix(dim, dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == 0 && j == 0) {
                    array.insert(1.0, i, j);
                } else {
                    array.insert(1.0 / (i + j + 1), i, j);
                }
            }
        }
    }

    public void printHilbert() {
        array.printMatrix();
    }

    public DoubleMatrix[] lu_factor(DoubleMatrix matrix) {
        DoubleMatrix[] landu = new DoubleMatrix[2];
        DoubleMatrix l = new DoubleMatrix(matrix.getHeight(), matrix.getWidth());
        DoubleMatrix u = matrix.copy();
        int i = 0;
        int j = 0;
        double factor;
        LinkedList<Double> listOfFactors = new LinkedList<Double>();


        while (i < matrix.getWidth() && j < matrix.getHeight()) {
            for (int row = 1; (j + row) < matrix.getHeight(); row++) {
                factor = - array.getValue(i, j + row) / (array.getValue(i, j));
                listOfFactors.add(factor);
                for (int col = i; col < matrix.getWidth(); col++) {
                    double answer = (u.getValue(col, j) * factor) + u.getValue(col, j + row);
                    u.insert(answer, col, j + row);
                }
            }
            i++;
            j++;
        }

        for (int a = 0; a < matrix.getWidth(); a++) {
            for (int b = 0; b < matrix.getHeight(); b++) {
                if (a == b) {
                    l.insert(1.0, a, b);
                } else if (!listOfFactors.isEmpty() && b > a) {
                    l.insert(-listOfFactors.remove(), a, b);
                } else {
                    l.insert(0.0, a, b);
                }
            }
        }
        landu[0] = l;
        landu[1] = u;
        return landu;
    }

    public static DoubleMatrix[] qr_fact_househ(DoubleMatrix matrix) {
        DoubleMatrix[] qandr = new DoubleMatrix[2];
        DoubleMatrix[] houseList = new DoubleMatrix[matrix.getWidth() - 1];
        DoubleMatrix result = matrix.copy();

        for (int k = 0; k < matrix.getWidth() - 1; k++) {
            DoubleMatrix x = new DoubleMatrix(matrix.getHeight() - k, 1);
            for (int j = k; j < matrix.getHeight(); j++) {
                x.insert(result.getValue(k, j), 0, j - k);
            }
            double xMag = x.magnitude();
            DoubleMatrix e1 = new DoubleMatrix(matrix.getHeight() - k, 1);
            e1.insert(xMag, 0, 0);
            DoubleMatrix numerator = x.addMatrix(e1);
            double denominator = numerator.magnitude();
            DoubleMatrix uHouse = numerator.scale(1.0 / denominator);
            DoubleMatrix i = DoubleMatrix.identity(matrix.getHeight() - k);
            DoubleMatrix houseHat = i.subtractMatrix((uHouse.multiplyMatrix(uHouse.transpose())).scale(2));
            DoubleMatrix house = new DoubleMatrix(matrix.getHeight(), matrix.getWidth());

            if (houseHat.getWidth() != result.getHeight()) {
                for (int y = 0; y < house.getHeight(); y++) {
                    for (int z = 0; z < house.getWidth(); z++) {
                        if (matrix.getWidth() - z <= houseHat.getWidth() && matrix.getHeight() - y <= houseHat.getHeight()) {
                            house.insert(houseHat.getValue(houseHat.getWidth() - (matrix.getWidth() - z), houseHat.getHeight() - (matrix.getHeight() - y)), z, y);
                        } else if (z == y) {
                            house.insert(1.0, z, y);
                        } else {
                            house.insert(0.0, z, y);
                        }
                    }
                }
            } else {
                house = houseHat;
            }
            houseList[k] = house;
            result = house.multiplyMatrix(result);
        }
        qandr[1] = result;
        qandr[0] = houseList[0];

        for (int a = 1; a < houseList.length; a++) {
            qandr[0] = qandr[0].multiplyMatrix(houseList[a]);
        }
        return qandr;
    }

    public void qr_fact_givens() {

    }
}
