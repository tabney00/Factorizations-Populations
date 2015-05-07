package src.code;

import src.math.IterativeMethod;
import src.math.Mathematics;
import src.math.MatrixDoesNotConvergeException;
import src.math.Vector;
import src.ui.Printing;

public class Codec {

    private static final byte[] seedA0 = { 1, 1, 0, 1 };
    private static final byte[] seedA1 = { 1, 0, 1, 1 };

    public static Code encode( int length, int extension ) {
        return encode( new Code( length, extension ) );
    }

    public static Code encode( Code xInput ) {

        Transform matrixA0 = new Transform( seedA0, xInput.size() );
        Transform matrixA1 = new Transform( seedA1, xInput.size() );

        System.out.println( Printing.wrap( "Matrix A0", matrixA0 ) );
        System.out.println( Printing.wrap( "Matrix A1", matrixA1 ) );

        Code y0Output = matrixA0.operateOn( xInput );
        Code y1Output = matrixA1.operateOn( xInput );

        return interlace( y0Output, y1Output );
    }

    public static Code decode( Code yInput, double tol, IterativeMethod method ) throws MatrixDoesNotConvergeException {
        Transform matrixA0 = new Transform( seedA0, yInput.size() / 2 );
        Transform matrixA1 = new Transform( seedA1, yInput.size() / 2 );
        Transform bigA = combine( matrixA0, matrixA1 );

        System.out.println( Printing.wrap( "Combined Matrix A", bigA ) );

        Vector x0 = new Vector( yInput.size(), 1.0 );
            // Initial guess must be 1-vec.

        Vector ret;

        switch (method) {
            case JACOBI:
                ret = Mathematics.jacobi( bigA, x0, yInput, tol );
                break;
            case GUASS_SEIDEL:
                ret = Mathematics.guassSeidel( bigA, x0, yInput, tol );
                break;
            default:
                throw new RuntimeException( "No method selected!" );
        }

        return deinterlace( ret );
    }

    private static Code interlace( Code y0, Code y1 ) {
        double[] y = new double[ y0.size() + y1.size() ];
        for (int lp = 0; lp < y0.size(); lp ++ ) {
            y[ 2 * lp ] = y0.get( lp );
            y[ 2 * lp + 1 ] = y1.get( lp );
        }
        return new Code( y );
    }

    private static <T extends Vector> Code deinterlace( T y ) {

        double[] xArr = new double[ y.size() / 2 ];
        for ( int i = 0; i < xArr.length; i++ ) {
            xArr[ i ] = y.get( 2 * i );
        }
        return new Code( xArr );
    }

    // Despite all the loops, the efficieny of this method is not terrible. Many of them loop over small sets of
    // numbers, such as i = 0, 1. It's about O(mn) where m is the number of matrices and n is the number of elements in
    // each one. However, only two matrices should be passed in.
    private static Transform combine( Transform... matrices ) {
        int newSize = matrices[ 0 ].size() * matrices.length;
        double[][] big = new double[ newSize ][ newSize ];

        for ( int offset = 0; offset < matrices.length ; offset++ ) {
            for ( int r = 0; r < matrices[ 0 ].size(); r++ ) {

                int bigRow = matrices.length * r + offset;
                int bigCol;
                    // `bigCol` needs to be recalculated for each element.

                for ( int c = 0; c < matrices[ 0 ].size(); c++ ) {
                    bigCol = matrices.length * c + offset;
                    big[ bigRow ][ bigCol ] = matrices[ offset ].get( r, c );
                }
            }
        }
        return new Transform( big );
    }
}
