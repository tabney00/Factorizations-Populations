package src.math;

import src.hilbert.DoubleMatrix;

public class MatrixQRDecompositionHH extends QRDecomposition {

    public MatrixQRDecompositionHH( Matrix A ) {
        DoubleMatrix matrix = new DoubleMatrix( A.transpose().data );

        DoubleMatrix[] qandr = new DoubleMatrix[2];

        // To self: don't touch.
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

        R = new Matrix( result.transpose().matrix );

        DoubleMatrix q = houseList[0];
        qandr[0] = houseList[0];

        for (int a = 1; a < houseList.length; a++) {
            q = q.multiplyMatrix(houseList[a]);
        }

        Q = new Matrix( q.transpose().matrix );
    }
}
