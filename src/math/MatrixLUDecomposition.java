package src.math;

import src.hilbert.DoubleMatrix;
import src.ui.Printing;

import java.util.LinkedList;

public class MatrixLUDecomposition {

    public final Matrix L;
    public final Matrix U;

    public MatrixLUDecomposition( Matrix matrix ) {
        U = matrix.transpose().transpose();
        double[][] larr = new double[ matrix.size() ][ matrix.size() ];

        for ( int i = 0; i < matrix.size(); i++ ) {
            larr[ i ][ i ] = 1.0;
        }

        for ( int i = 0; i < matrix.size() - 1; i++ ) {
            // For each row we use to reduce the lower rows.
            for ( int j = i + 1; j < matrix.size(); j++ ) {
                // For each row that needs to be reduced.


                double aboveValue = matrix.get( i, i );
                if ( aboveValue != 0.0 ) {

                    double belowValue = matrix.get( j, i );
                    double aboveMultiply = belowValue / aboveValue;

                    larr[ j ][ i ] = aboveMultiply;
                        // Saving the factor to build L.

                    for ( int k = i; k < matrix.size(); k++ ) {
                        // For each element in the row j.

                        double reducedValue = U.get( j, k ) - aboveMultiply * matrix.get( i, k );
                            // Add the multiplication factor times its
                            // respective element in the row i.

                        U.set( j, k, reducedValue );
                    }
                }
            }
        }

        L = new Matrix( larr );
    }
}
