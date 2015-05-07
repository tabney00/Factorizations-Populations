package src.math;

import src.ui.Printing;

public class Mathematics {
    private static final int MAX_ITERATIONS = 1000;

    public static Vector jacobi( Matrix A, Vector x0, Vector y, double tol )
            throws MatrixDoesNotConvergeException {

        MatrixDecomposition dlu = new MatrixDecomposition( A );
        Matrix Di = dlu.D.inverse();
        Matrix DiLpU = Di.mtimes( dlu.L.plus( dlu.U ) );
        Vector Diy = Di.vtimes( y );

        int iterations = 0;
        double diff;
        Vector xk = x0;
        Vector xkp1 = x0;
        do {
            if ( ++iterations > MAX_ITERATIONS ) {
                throw new MatrixDoesNotConvergeException( MAX_ITERATIONS );
            }

            xk = xkp1;
            xkp1 = Diy.minus( DiLpU.vtimes( xk ) );

            diff = xk.minus( xkp1 ).magnitude();

        } while ( diff > tol );
        System.out.printf( "Jacobi method took %d iterations to complete.\n", iterations );

        return xkp1;
    }

    public static Vector guassSeidel( Matrix A, Vector x0, Vector y, double tol )
            throws MatrixDoesNotConvergeException {
        MatrixDecomposition dlu = new MatrixDecomposition( A );
        Matrix DpL = dlu.D.plus( dlu.L );
        Matrix U = dlu.U;

        int iterations = 0;
        double diff;
        Vector xk = x0;
        Vector xkp1 = x0;
        do {
            if ( ++iterations > MAX_ITERATIONS ) {
                throw new MatrixDoesNotConvergeException( MAX_ITERATIONS );
            }
            xk = xkp1;
            Vector a = forwardSubstitute( DpL, y );
            Vector b = forwardSubstitute( DpL, U.vtimes( xk ) );
            xkp1 = a.minus( b );

            diff = xk.minus( xkp1 ).magnitude();
        } while ( diff > tol );
        System.out.printf( "Guass-Seidel method took %d iterations to complete.\n", iterations );

        return xkp1;
    }

    public static double powerMethod( Matrix A, Vector u0, double tol ) throws MatrixDoesNotConvergeException {

        double prevEigenvalue = 0;
        double nextEigenvalue = 0;
        int iterations = 0;
        double diff;

        Vector vk = u0;
        Vector vkp1;
        do {
            if ( ++iterations > MAX_ITERATIONS ) {
                throw new MatrixDoesNotConvergeException( MAX_ITERATIONS );
            }

            prevEigenvalue = nextEigenvalue;
            vkp1 = A.vtimes( vk );

            nextEigenvalue = vkp1.get( 0 );
            vkp1 = vkp1.stimes( 1.0 / nextEigenvalue );

            vk = vkp1;

            diff = Math.abs( nextEigenvalue - prevEigenvalue );
        } while ( diff > tol );

        System.out.printf( "Power method took %d iterations to complete.\n", iterations );
        System.out.println( Printing.wrap( "Eigenvector", vkp1 ) );
        return nextEigenvalue;
    }

    public static Vector solveLU( AugmentedMatrix amatrix ) {
        MatrixLUDecomposition lu = new MatrixLUDecomposition( amatrix.matrix );
        Vector y = Mathematics.forwardSubstitute( lu.L, amatrix.vector );
        Vector x = Mathematics.backwardsSubstitute( lu.U, y );
        System.out.println( Printing.wrap( "Matrix L", lu.L ) );
        System.out.println( Printing.wrap( "Matrix U", lu.U ) );
        System.out.println( Printing.wrap( "Matrix LU", lu.L.mtimes( lu.U ) ) );
        return x;
    }

    public static Vector solveLU( MatrixLUDecomposition lu, Vector b ) {
        Vector y = Mathematics.forwardSubstitute( lu.L, b );
        Vector x = Mathematics.backwardsSubstitute( lu.U, y );
        return x;
    }

    static Vector forwardSubstitute( Matrix S, Vector y ) {
        double[] ret = new double[ y.size() ];

        for ( int r = 0; r < ret.length; r++ ) {
            double prevSum = 0.0;
            for ( int c = 0; c < r; c++ ) {
                prevSum += S.get( r, c ) * ret[ c ];
            }

            ret[ r ] = ( y.get( r ) - prevSum ) / S.get( r, r );
        }
        return new Vector( ret );
    }

    static Vector backwardsSubstitute( Matrix S, Vector y ) {
        double[] ret = new double[ y.size() ];

        for ( int r = ret.length - 1; r >= 0; r-- ) {
            double prevSum = 0.0;
            for ( int c = ret.length - 1; c >= 0; c-- ) {
                prevSum += S.get( r, c ) * ret[ c ];
            }

            ret[ r ] = ( y.get( r ) - prevSum ) / S.get( r, r );
        }
        return new Vector( ret );
    }

    public static Vector solveQRHH( Matrix A, Vector b ) {
        QRDecomposition qr = new MatrixQRDecompositionHH( A );
        System.out.println( Printing.wrap( "Matrix {Q} by Householder", qr.Q ) );
        System.out.println( Printing.wrap( "Matrix {R} by Householder", qr.R ) );

        return solveQR( qr, b );
    }

    public static Vector solveQRGR( Matrix A, Vector b ) {
        QRDecomposition qr = new MatrixQRDecompositionGR( A );
        System.out.println( Printing.wrap( "Matrix {Q} by Householder", qr.Q ) );
        System.out.println( Printing.wrap( "Matrix {R} by Householder", qr.R ) );
        //calcError( ...... );
        return solveQR( qr, b );
    }

    static Vector solveQR( Matrix A, QRDecomposition qr, Vector b ) {
        Vector x = solveQR( qr, b );
        double matrixError = qr.Q.mtimes( qr.R ).minus( A ).magnitude();
        double solutionError = A.vtimes( x ).minus( b ).magnitude();

        System.out.println( Printing.wrap( "Matrix Error |QR-A|", matrixError ) );
        System.out.println( Printing.wrap( "Solution Error |Ax-b|", solutionError ) );

        return x;
    }

    static Vector solveQR( QRDecomposition qr, Vector b ) {
        Vector Qtb = qr.Q.transpose().vtimes( b );
        return backwardsSubstitute( qr.R, Qtb );
    }
}
