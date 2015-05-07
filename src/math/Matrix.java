package src.math;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Matrix {

    public static void main( String... args ) {
        double[][] arrA = {
            { 1.0, 1.0, 1.0 },
            { 1.0, 1.0, 1.0 },
            { 1.0, 1.0, 1.0 }
        };
        Matrix A = new Matrix( arrA );
        Matrix B = new Matrix( arrA );
        B.set( 0, 0, 1.0000000000000000001 );
        System.out.println( A.minus( B ) );
    }

    public double[][] data;

    public Matrix() {
        super();
    }

    Matrix( int size ) {
        //this( size, 0.0 );
            // Probably unecessary now that we're using primitives.
        data = new double[ size ][ size ];
    }

    Matrix( int size, double fill ) {
        data = new double[ size ][ size ];
        for ( int r = 0; r < size; r++ ) {
            for ( int c = 0; c < size; c++ ) {
                data[ r ][ c ] = fill;
            }
        }
    }

    public Matrix( double[][] inputArray ) {
        data = inputArray;
    }

    public double get( int r, int c ) {
        return data[ r ][ c ];
    }

    void set( int r, int c, double value ) {
        data[ r ][ c ] = value;
    }

    public int size() {
        return data.length;
    }

    Matrix plus( Matrix other ) {
        Matrix ret = new Matrix( size() );
        for ( int r = 0; r < size(); r++ ) {
            for ( int c = 0; c < size(); c++ ) {
                double value = get( r, c ) + other.get( r, c );
                ret.set( r, c, value );
            }
        }
        return ret;
    }

    Matrix minus( Matrix other ) {
        double[][] ret = new double[ size() ][ size() ];
        for ( int r = 0; r < size(); r++ ) {
            for ( int c = 0; c < size(); c++ ) {
                double value = data[ r ][ c ] - other.get( r, c );
                ret[ r ][ c ] = value;
            }
        }
        return new Matrix( ret );
    }

    Matrix stimes( double scalar ) {
        Matrix ret = new Matrix( size() );
        for ( int r = 0; r < size(); r++ ) {
            for ( int c = 0; c < size(); c++ ) {
                ret.set( r, c, scalar * get( r, c ) );
            }
        }
        return ret;
    }

    public Vector vtimes( Vector vector ) {
        Vector ret = new Vector( size() );
        for ( int r = 0; r < size(); r++ ) {
            double val = 0;
            for ( int c = 0; c < size(); c++ ) {
                val += ( data[ r ][ c ] * vector.get( c ) );
            }
            ret.set( r, val );
        }
        return ret;
    }

    public Vector vtimes( Vector vector, double mod ) {
        Vector ret = new Vector( size() );
        for ( int r = 0; r < size(); r++ ) {
            double val = 0;
            for ( int c = 0; c < size(); c++ ) {
                val += ( data[ r ][ c ] * vector.get( c ) );
            }
            ret.set( r, val % mod );
        }
        return ret;
    }

    // Undefined behavior for non-square matrices of equal size.
    public Matrix mtimes( Matrix matrix ) {
        Matrix ret = new Matrix( size() );
        for ( int i = 0; i < size(); i++ ) {
            for ( int j = 0; j < size(); j++ ) {

                double value = 0;
                for ( int k = 0; k < size(); k++ ) {
                    value += get( i, k ) * matrix.get( k, j );
                }

                ret.set( i, j, value );
            }
        }
        return ret;
    }

    // Undefined behavior for non-diagonal non-square matrices.
    Matrix inverse() {
        Matrix ret = new Matrix( size() );
        for ( int i = 0; i < size(); i++ ) {
            ret.set( i, i, ( 1.0 / get( i, i ) ) );
        }
        return ret;
    }

    public Matrix transpose() {
        double[][] ret = new double[ size() ][ size() ];
        for ( int i = 0; i < size(); i++ ) {
            for ( int j = 0; j < size(); j++ ) {
                ret[ j ][ i ] = data[ i ][ j ];
            }
        }
        return new Matrix( ret );
    }

    public double magnitude() {
        double max = 0;
        for ( int r = 0; r < size(); r++ ) {
            for ( int c = 0; c < size(); c++ ) {
                double val =  Math.abs( get( r, c ) );
                max = ( val > max ) ? val : max;
            }
        }
        return max;
    }


    public String toString() {
        return Arrays.stream( data )
                .map( x -> Arrays.stream( x )
                        .mapToObj( y -> String.format( "%4.3f", y ) )
                        .collect( Collectors.joining( "  " ) ) )
                .collect( Collectors.joining( "\n" ) );
    }
}
