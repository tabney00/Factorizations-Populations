package src.math;

import src.ui.Printing;

import java.util.LinkedList;
import java.util.HashSet;

public class MatrixQRDecompositionGR extends QRDecomposition {

    public static void main( String... args ) {
        double[][] arr = {
            { 1, 2, 3 },
            { 0, 5, 6 },
            { 0, 0, 1 } };
        MatrixQRDecompositionGR qr = new MatrixQRDecompositionGR( new Matrix( arr ) );
    }

    public MatrixQRDecompositionGR( Matrix A ) {
        LinkedList<Matrix> rotations = new LinkedList<>();
        R = A;
        for ( int pivot = 0; pivot < A.size() - 1; pivot++ ) {
            double pivotVal = A.get( pivot, pivot );
            if ( pivotVal != 0 ) {
                for ( int zeros = pivot + 1; zeros < A.size(); zeros++ ) {
                    Matrix rotation = makeRotationMatrix( R, pivot, zeros );
                    rotations.add( rotation );
                    R = rotation.mtimes( R );
                }
            }
        }

        Q = rotations.removeLast();
        for ( int i = 0; i < rotations.size(); i++ ) {
            Q = rotations.removeLast().mtimes( Q );
        }
    }

    private static Matrix makeRotationMatrix( Matrix A, int pivot, int zeroIndex ) {
        Matrix rot = new Matrix( A.size() );

        for ( int i = 0; i < rot.size(); i++ ) {
            rot.set( i, i, 1.0 );
        }

        double a = A.get( pivot, pivot );
        double b = A.get( zeroIndex, pivot );

        double cos = makeCos( a, b );
        double sin = makeSin( a, b );

        int len = A.size() - 1;
        if ( pivot == 0 && zeroIndex == len ) {
            rot.set( 0, 0, cos );
            rot.set( len , 0, sin );
            rot.set( 0, len , -sin );
            rot.set( len, len, cos );
        } else {
            rot.set( pivot, pivot, cos );
            rot.set( zeroIndex, pivot, sin );
            rot.set( pivot, pivot + 1, -sin );
            rot.set( pivot + 1, pivot + 1, cos );
        }

        return rot;
    }

    private static double makeCos( double a, double b ) {
        return a / Math.sqrt( a * a + b * b );
    }

    private static double makeSin( double a, double b ) {
        return -b / Math.sqrt( a * a + b * b );
    }
}
