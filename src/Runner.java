package src;

import src.ui.*;
import src.code.*;
import src.population.*;
import src.math.*;
import src.hilbert.*;

import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Runner {

    private static final String GOUP = "Go up a level.";
    private static final String EXIT = "Exit the program.";

    private static final String TITLE = "\t\tMATH2605 Project: Tyler Abney & Christian Demsar\t\t";

    private static final String READFROMSTDIN = "Manually type in the data.";
    private static final String READFROMFILE = "Data from the file ";

    private static final String CODEDROP = "dat/code_drop.dat";
    private static final String AUGEMENTEDMATRIXDROP = "dat/augmented_matrix_drop.dat";
    private static final String X0VECTORDROP = "dat/x0_vector_drop.dat";
    private static final String MATRIXDROP = "dat/matrix_drop.dat";


    private static final String HILBERT = "The Hilbert Matrix";
    private static final String CODES = "Convolutional Codes";
    private static final String POPULATIONS = "Urban Population Dynamics";
    private static final String MATH = "Mathematical Functions";

    public static void main( String... args ) {
        Printing.wraptitle( TITLE );
        Printing.makeOptions( EXIT, HILBERT, CODES, POPULATIONS, MATH );

        int option = requestOption( 0, 1, 2, 3, 4 );

        if ( option == 1 ) {
            hilbertMains();
        } else if ( option == 2 ) {
            codesMain( new Code( DEFAULTCODE, 3 ) );
        } else if ( option == 3 ) {
            populationsMain();
        } else if ( option == 4 ) {
            mathsMain();
        }

    }

    public static int requestOption( Integer... options ) {
        String msg = "\nSelect an option by typing in the number and pressing enter.\n";
        Options validOptions = new Options( options );
        boolean set = false;
        int option = -1;
        while ( !set ) {
            msg( msg );
            try {
                option = ( new Scanner( System.in ) ).nextInt();
                set = validOptions.contains( option );
                if ( !set ) {
                    throw new InputMismatchException();
                }
            } catch ( InputMismatchException e ) {
            }
        }
        return option;
    }

    private static int requestInt() {
        String msg =  "\nPlease enter a positive integer.\n";
        int input = -1;
        while ( input <= 0 ) {
            msg( msg );
            try {
                input = ( new Scanner( System.in ) ).nextInt();
            } catch ( InputMismatchException e ) {
            }
        }
        return input;
    }

    private static double requestTol() {
        String msg = "\nPlease enter a tolerance (double value greater than zero).\n";
        double tol = -1.0;
        while ( tol <= 0.0 ) {
            msg( msg );
            try {
                tol = ( new Scanner( System.in ) ).nextDouble();
            } catch ( InputMismatchException e ) {
            }
        }
        return tol;
    }

    private static AugmentedMatrix readAugmentedMatrix( String filename ) {
        msg( READFROMFILE + filename );
        double[][] matrixArr = null;
        double[] vectorArr = null;
        while ( matrixArr == null  || vectorArr == null ) {
            try {

                ArrayList<String> lines = getLines( filename );

                matrixArr = new double[ lines.size() ][ lines.size() ];
                vectorArr = new double[ lines.size() ];

                for ( int i = 0; i < lines.size(); i++ ) {
                    Scanner rowsc = new Scanner( lines.get ( i ) );
                    for ( int j = 0; j < lines.size(); j++ ) {
                        matrixArr[ i ][ j ] = rowsc.nextDouble();
                    }
                    vectorArr[ i ] = rowsc.nextDouble();
                }

            } catch ( IOException io ) {
                msg( "The file " + filename + " was not found. Please check that it exists." );
            } catch ( NumberFormatException n ) {
                msg( "File must contain double values separated only by whitespace (tabs and spaces)." );
                matrixArr = null;
            } catch ( InputMismatchException m ) {
                msg( "File must contain double values separated only by whitespace (tabs and spaces)." );
                matrixArr = null;
            }
        }
        AugmentedMatrix amatrix = new AugmentedMatrix( matrixArr, vectorArr );
        msg( Printing.wrap( "Imported Matrix {A}", amatrix.matrix ) );
        msg( Printing.wrap( "Imported Vector {b}", amatrix.vector ) );
        return amatrix;
    }

    private static Matrix readMatrix( String filename ) {
        msg( READFROMFILE + filename );
        double[][] matrixArr = null;
        while ( matrixArr == null  ) {
            try {

                ArrayList<String> lines = getLines( filename );

                matrixArr = new double[ lines.size() ][ lines.size() ];

                for ( int i = 0; i < lines.size(); i++ ) {
                    Scanner rowsc = new Scanner( lines.get ( i ) );
                    for ( int j = 0; j < lines.size(); j++ ) {
                        matrixArr[ i ][ j ] = rowsc.nextDouble();
                    }
                }

            } catch ( IOException io ) {
                msg( "The file " + filename + " was not found. Please check that it exists." );
            } catch ( NumberFormatException n ) {
                msg( "File must contain double values separated only by whitespace (tabs and spaces)." );
                matrixArr = null;
            } catch ( InputMismatchException m ) {
                msg( "File must contain double values separated only by whitespace (tabs and spaces)." );
                matrixArr = null;
            }
        }
        Matrix matrix = new Matrix( matrixArr );
        msg( Printing.wrap( "Imported Matrix {A}", matrix ) );
        return matrix;

    }

    private static ArrayList<String> getLines( String filename ) throws IOException {
        java.util.ArrayList<String> lines = new java.util.ArrayList<>();
        for ( String line : Files.readAllLines( Paths.get( filename ) ) ) {
            lines.add( line );
        }
        return lines;
    }

    private static Vector readVector( String filename ) {
        msg( READFROMFILE + filename );
        double[] vectorArr = null;
        while ( vectorArr == null ) {
            try {

                ArrayList<String> lines = getLines( filename );

                vectorArr = new double[ lines.size() ];

                for ( int i = 0; i < lines.size(); i++ ) {
                    vectorArr[ i ] = ( new Scanner( lines.get ( i ) ) ).nextDouble();
                }

            } catch ( IOException io ) {
                msg( "The file " + filename + " was not found. Please check that it exists." );
            } catch ( NumberFormatException n ) {
                msg( "File must contain vertical double values separated by newlines." );
                vectorArr = null;
            } catch ( InputMismatchException m ) {
                msg( "File must contain vertical double values separated by newlines." );
                vectorArr = null;
            }
        }
        Vector vec = new Vector( vectorArr );
        msg( Printing.wrap( "Imported Vector {x0}", vec ) );
        return vec;

    }

    public static void msg( String str ) {
        System.out.printf( "\n%s\n\n", str );
    }

    private static void printInputTypes( String filepath ) {
        Printing.makeOptions( GOUP, READFROMSTDIN, READFROMSTDIN + filepath );
    }

    //
    // Hilbert Matrix section begins here.
    //

    private static final String HILBERTMAKE = "Make a Hilbert matrix of a chosen size.";
    private static final String HILBERTERROR = "Calculate Hilbert matrices.";

    private static void hilbertMains() {
        Printing.wraptitle( HILBERT );
        Printing.makeOptions( GOUP, HILBERTMAKE, HILBERTERROR );

        int option = requestOption( 0, 1, 2 );

        if ( option == 0 ) {
            main();
        } else if ( option == 1 ) {
            hilbertMake();
        } else if ( option == 2 ) {
            hilbertError();
        }

    }

    private static void hilbertMake() {
        Printing.wraptitle( HILBERTMAKE );

        int size = requestInt();

        HilbertMatrix hmatrix = new HilbertMatrix( size );
        msg( Printing.wrap( "Hilbert Matrix n = " + size, hmatrix.array ) );

        hilbertMains();
    }

    private static void hilbertError() {
        Printing.wraptitle( HILBERTERROR );

        msg( "Output is saved to \"error.txt\"." );

        hilbertMains();
    }

    //
    // Convolutional Codes section begins here.
    //

    private static final String ENCODE = "Encode the saved bitstring.";
    private static final String DECODE = "Decode the saved bitstring.";
    private static final String IMPORTCODE = "Import or create a code.";

    private static final double[] DEFAULTCODE = { 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0 };

    private static void codesMain( Code code ) {
        Printing.wraptitle( CODES );
        Printing.makeOptions( GOUP, ENCODE, DECODE, IMPORTCODE );

        Code savedCode = code;
        msg( Printing.wrap( "Saved Code", code ) );

        int option = requestOption( 0, 1, 2, 3 );

        if ( option == 0 ) {
            main();
        } else if ( option == 1 ) {
            codesEncode( savedCode );
        } else if ( option == 2 ) {
            codesDecode( savedCode );
        } else if ( option == 3 ) {
            codesImport( savedCode );
        }

    }

    private static final String GENERATERANDOM = "Generate a random bitstring of a certain length.";

    private static void codesEncode( Code code ) {
        msg( Printing.wrap(  "Input Bitstring {x}", code ) );
        Code encoded = Codec.encode( code );
        msg( Printing.wrap( "Output Bitstring {y}", encoded ) );
        codesMain( encoded );
    }

    private static final String JACOBI = "Use the Jacobi iterative method.";
    private static final String GUASS_SEIDEL = "Use the Guass-Seidel iterative method.";

    private static void codesDecode( Code code ) {
        Printing.wraptitle( DECODE );
        Printing.makeOptions( GOUP, JACOBI, GUASS_SEIDEL );

        IterativeMethod method = null;

        int option = requestOption( 0, 1, 2 );

        if ( option == 0 ) {
            codesEncode( code );
        } else if ( option == 1 ) {
            method = IterativeMethod.JACOBI;
        } else if ( option == 2 ) {
            method = IterativeMethod.GUASS_SEIDEL;
        }

        double tol = requestTol();

        msg( Printing.wrap(  "Input Bitstring {y}", code ) );
        try {
            Code decoded = Codec.decode( code, tol, method );
            msg( Printing.wrap( "Output Bitstring {x}", decoded ) );
            codesMain( decoded );
        } catch ( MatrixDoesNotConvergeException e ) {
            msg( "\n The matrix did not converge.");
            msg( e.getMessage() );
            codesMain( new Code( DEFAULTCODE, 3 ) );
        }
    }

    private static void codesImport( Code code ) {
        Printing.wraptitle( IMPORTCODE );
        Printing.makeOptions( GOUP, READFROMSTDIN, READFROMFILE + CODEDROP, GENERATERANDOM );

        int option = requestOption( 0, 1, 2, 3 );

        if ( option == 0 ) {
            codesMain( code );
        } else if ( option == 1 ) {
            codesImportManual();
        } else if ( option == 2 ) {
            codesImportFile();
        } else if ( option == 3 ) {
            codesImportRandom();
        }
    }

    private static void codesImportManual() {
        Printing.wraptitle( READFROMSTDIN );
        String bitstring;
        double[] codeArr = null;
        while ( codeArr == null ) {
            msg( "\nEnter a binary code made of 1s and 0s. You may not use spaces.\n" );
            try {
                bitstring = ( new Scanner( System.in ) ).nextLine().trim();
                codeArr = new double[ bitstring.length() ];
                for ( int i = 0; i < bitstring.length(); i++ ) {
                    codeArr[ i ] = ( int ) Integer.parseInt( new String( new char[] { bitstring.charAt( i ) } ) );
                    if ( codeArr[ i ] != 1 && codeArr [ i ] != 0 ) {
                        throw new InputMismatchException();
                    }
                }
            } catch ( NumberFormatException n ) {
                codeArr = null;
            } catch ( InputMismatchException e ) {
                codeArr = null;
            }
        }

        Code code = new Code( codeArr, 3 );
        codesMain( code );
    }

    private static void codesImportFile() {
        Printing.wraptitle( READFROMFILE + CODEDROP );
        String bitstring = null;
        double[] codeArr = null;
        while ( codeArr == null ) {
            try {
                java.util.ArrayList<String> lines = new java.util.ArrayList<>();
                for ( String line : Files.readAllLines( Paths.get( CODEDROP ) ) ) {
                    lines.add( line );
                }

                if ( lines.size() > 1 ) {
                    throw new NumberFormatException();
                }

                bitstring = lines.get( 0 );
                codeArr = new double[ bitstring.length() ];
                for ( int i = 0; i < bitstring.length(); i++ ) {
                    codeArr[ i ] = ( int ) Integer.parseInt( new String( new char[] { bitstring.charAt( i ) } ) );
                    if ( codeArr[ i ] != 1 && codeArr [ i ] != 0 ) {
                        throw new InputMismatchException();
                    }
                }
            } catch ( IOException io ) {
                msg( "\nThe file " + CODEDROP + " was not found. Please check that it exists." );
            } catch ( NumberFormatException n ) {
                msg( "\nFile must contain a binary code made of 1s and 0s. "
                        + "You may not use spaces or split it across lines.\n" );
                codeArr = null;
            } catch ( InputMismatchException m ) {
                msg( "\nFile must contain a binary code made of 1s and 0s. "
                        + "You may not use spaces or split it across lines.\n" );
                codeArr = null;
            }
        }

        Code code = new Code( codeArr, 3 );
        codesMain( code );
    }

    private static void codesImportRandom() {
        Printing.wraptitle( GENERATERANDOM );
        int codelength = requestInt();

        Code code = new Code( codelength, 3 );
        msg( Printing.wrap(  "Generated Code", code ) );
        codesMain( code );
    }

    //
    // Convolutional Codes ends here.
    //

    //
    // Population Dynamics starts here.
    //

    private static void populationsMain() {
        Printing.wraptitle( POPULATIONS );
        Printing.makeOptions( GOUP );

        msg( "This portion of the project is not interactive. See Discussion.pdf for population predictions." );
        msg( "You can evaluate the functions used in this portion through selection (4) on the main menu." );

        int option = requestOption( 0 );
        if ( option == 0 ) {
            main();
        }

    }

    private static final String POWER_METHOD = "Power method for finding the largest eigenvalue.";
    private static final String LU_DECOMP_SOLVE = "Solve a system by using LU decomposition on an augmented matrix.";
    private static final String QR_DECOMP_SOLVE = "Solve a system by using QR decomposition on an augmented matrix.";
    private static final String LU_DECOMP = "Perform an LU decomposition.";
    private static final String QR_DECOMP = "Perform a QR decomposition.";

    private static void mathsMain() {
        Printing.wraptitle( MATH );
        Printing.makeOptions( GOUP, JACOBI, GUASS_SEIDEL, POWER_METHOD, LU_DECOMP_SOLVE, QR_DECOMP_SOLVE, LU_DECOMP, QR_DECOMP );

        int option = requestOption( 0, 1, 2, 3, 4, 5, 6, 7 );
        if ( option == 0 ) {
            main();
        } else if ( option == 1 ) {
            mathsJacobi();
        } else if ( option == 2 ) {
            mathsGuassSeidel();
        } else if ( option == 3 ) {
            mathsPowerMethod();
        } else if ( option == 4 ) {
            mathsSolveLUDecomposition();
        } else if ( option == 5 ) {
            mathsSolveQRDecomposition();
        } else if ( option == 6 ) {
            mathsDoLU();
        } else if ( option == 7 ) {
            mathsDoQR();
        }
    }

    private static void mathsJacobi() {
        Printing.wraptitle( JACOBI );
        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        Vector x0 = readVector( X0VECTORDROP );
        double tol = requestTol();
        Vector vec = null;
        try {
             vec = Mathematics.jacobi( amatrix.matrix, amatrix.vector, x0, tol );
        } catch ( MatrixDoesNotConvergeException c ) {
            msg( c.getMessage() );
            mathsMain();
        }
        msg( Printing.wrap( "Jacobi solution for tol = " + tol, vec ) );
        mathsMain();
    }

    private static void mathsGuassSeidel() {
        Printing.wraptitle( GUASS_SEIDEL );
        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        Vector x0 = readVector( X0VECTORDROP );
        double tol = requestTol();
        Vector vec = null;
        try {
            vec = Mathematics.guassSeidel( amatrix.matrix, amatrix.vector, x0, tol );
        } catch ( MatrixDoesNotConvergeException c ) {
            msg( c.getMessage() );
            mathsMain();
        }
        msg( Printing.wrap( "Guass-Seidel solution for tol = " + tol, vec ) );
        mathsMain();
    }

    private static void mathsPowerMethod() {
        Printing.wraptitle( POWER_METHOD );
        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        double tol = requestTol();
        double eigenvalue;
        try {
            eigenvalue = Mathematics.powerMethod( amatrix.matrix, amatrix.vector, tol );
            msg( Printing.wrap( "Largest Eigenvalue", eigenvalue ) );
        } catch ( MatrixDoesNotConvergeException c ) {
            msg( c.getMessage() );
        }
        mathsMain();
    }

    private static void mathsSolveLUDecomposition() {
        Printing.wraptitle( LU_DECOMP_SOLVE );

        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        Vector x = Mathematics.solveLU( amatrix );
        msg( Printing.wrap( "Solution {x}", x ) );


        mathsMain();
    }

    private static void mathsDoLU() {
        Printing.wraptitle( LU_DECOMP );
        Matrix matrix = readMatrix( MATRIXDROP );
        MatrixLUDecomposition lu = new MatrixLUDecomposition( matrix );

        msg( Printing.wrap( "L", lu.L ) );
        msg( Printing.wrap( "U", lu.U ) );
        msg( Printing.wrap( "LU", lu.L.mtimes( lu.U ) ) );

        mathsMain();

    }

    private static void mathsDoQR() {
        Printing.wraptitle( QR_DECOMP );
        Matrix matrix = readMatrix( MATRIXDROP );
        MatrixQRDecompositionHH qr = new MatrixQRDecompositionHH( matrix );

        msg( Printing.wrap( "Q", qr.Q ) );
        msg( Printing.wrap( "R", qr.R ) );
        msg( Printing.wrap( "QR", qr.Q.mtimes( qr.R ) ) );

        mathsMain();
    }

    private static final String HHREFLECTION = "Use Householder reflections.";
    private static final String GIVENSROTATION = "Use Givens rotations.";

    private static void mathsSolveQRDecomposition() {
        Printing.wraptitle( QR_DECOMP_SOLVE );
        Printing.makeOptions( GOUP, HHREFLECTION, GIVENSROTATION );

        int option = requestOption( 0, 1, 2 );
        if ( option == 0 ) {
            mathsMain();
        } else if ( option == 1 ) {
            mathsHouseHolder();
        } else if ( option == 2 ) {
            mathsGivens();
        }
    }

    private static void mathsHouseHolder() {
        Printing.wraptitle( HHREFLECTION );
        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        Vector x = Mathematics.solveQRHH( amatrix.matrix, amatrix.vector );
        msg( Printing.wrap( "Vector {x} solution", x ) );
        mathsSolveQRDecomposition();
    }

    private static void mathsGivens() {
        Printing.wraptitle( GIVENSROTATION );
        AugmentedMatrix amatrix = readAugmentedMatrix( AUGEMENTEDMATRIXDROP );
        Vector x = Mathematics.solveQRGR( amatrix.matrix, amatrix.vector );
        msg( Printing.wrap( "Vector {x} solution", x ) );
        mathsSolveQRDecomposition();
    }

}
