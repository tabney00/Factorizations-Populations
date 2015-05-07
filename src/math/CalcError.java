package src.math;

import src.hilbert.HilbertMatrix;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class CalcError {

    public ArrayList<Double> luMatrixErrors = new ArrayList<Double>(19);
    public ArrayList<Double> luSolutionErrors = new ArrayList<Double>(19);
    public ArrayList<Double> qrSolutionErrorsHH = new ArrayList<Double>(19);
    public ArrayList<Double> qrMatrixErrorsHH = new ArrayList<Double>(19);
    public ArrayList<Double> qrSolutionErrorsGR = new ArrayList<Double>(19);
    public ArrayList<Double> qrMatrixErrorsGR = new ArrayList<Double>(19);


    public static void main( String... args ) {
        CalcError calcErr = new CalcError();
        try {
            calcErr.export();
        } catch (FileNotFoundException e) {

        }
    }
    public CalcError() {
        luHilbert();
        qrHilbertHH();
        qrHilbertGR();

    }

    private void luHilbert() {
        for (int i = 2; i <= 20; i++) {
            HilbertMatrix hilbertOrig = new HilbertMatrix(i);
            Matrix hilbert = new Matrix( hilbertOrig.array.matrix );
            MatrixLUDecomposition lu = new MatrixLUDecomposition( hilbert );
            double matrixErr = lu.L.mtimes( lu.U ).minus( hilbert ).magnitude();
            luMatrixErrors.add( matrixErr );
            Matrix ltu = lu.L.mtimes( lu.U );
            for ( int r = 0; r < ltu.size(); r++ ) {
                for ( int c = 0; c < ltu.size(); c++ ) {
                    System.out.println(ltu.get(r, c) + " - " + hilbert.get(r, c) + " gives us");
                    System.out.println( ltu.get( r, c ) - hilbert.get( r, c ) );
                }
            }
            Vector b = fillB( i );
            Vector x = Mathematics.solveLU( lu, b );

            double soluErr = hilbert.vtimes( x ).minus( b ).magnitude();
            luSolutionErrors.add( soluErr );
        }
    }

    private void qrHilbertHH() {
        for (int i = 2; i <= 20; i++) {
            HilbertMatrix hilbertOrig = new HilbertMatrix(i);
            Matrix hilbert = new Matrix( hilbertOrig.array.matrix );
            QRDecomposition qr = new MatrixQRDecompositionHH( hilbert );
            double matrixErr = qr.Q.mtimes( qr.R ).minus( hilbert ).magnitude();
            qrMatrixErrorsHH.add( matrixErr );

            Vector b = fillB( i );
            Vector x = Mathematics.solveQR( qr, b );

            double soluErr = hilbert.vtimes( x ).minus( b ).magnitude();
            qrSolutionErrorsHH.add( soluErr );
        }
    }


    public void qrHilbertGR() {
        for (int i = 2; i <= 20; i++) {
            HilbertMatrix hilbertOrig = new HilbertMatrix(i);
            Matrix hilbert = new Matrix( hilbertOrig.array.matrix );
            QRDecomposition qr = new MatrixQRDecompositionGR( hilbert );
            double matrixErr = qr.Q.mtimes( qr.R ).minus( hilbert ).magnitude();
            qrMatrixErrorsGR.add( matrixErr );

            Vector b = fillB( i );
            Vector x = Mathematics.solveQR( qr, b );

            double soluErr = hilbert.vtimes( x ).minus( b ).magnitude();
            qrSolutionErrorsGR.add( soluErr );
        }
    }

    private static Vector fillB( int n ) {
        double[] barr = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            barr[ i ] = Math.pow( 0.1, ( n / 3 ) );
        }
        return new Vector( barr );
    }

    public void export() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("errors.txt");
        out.println("luSolutionErrors");
        for (double each: luSolutionErrors) {
            out.println(each);
        }
        out.println("luMatrixErrors");
        for ( double each : luMatrixErrors ) {
            out.println( each );
        }
        out.println(" qrSolutionErrorsHH");
        for ( double each : qrSolutionErrorsHH ) {
            out.println( each );
        }

        out.println( "qrMatrixErrorsHH" );

        for ( double each : qrMatrixErrorsHH ) {
            out.println( each );
        }

        out.println(" qrSolutionErrorsGR" );

        for ( double each : qrSolutionErrorsGR ) {
            out.println( each );
        }

        out.println( "qrMatrixErrorsGR" );

        for ( double each : qrMatrixErrorsGR ) {
            out.println( each );
        }
        // System.out.println("\nErrors for Hx");
        // for (int i = 0; i < hxErrors.length; i++) {
        //     out.println(hxErrors[i]);
        // }
        out.close();
    }
}
