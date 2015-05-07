package src.math;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Vector {

    public double[] data;

    public Vector() {
        super();
    }

    Vector( int size ) {
        //this( size, 0.0 );
        data = new double[ size ];
    }

    public Vector( int size, double fill ) {
        data = new double[size];
        Arrays.fill( data, fill );
    }

    public Vector( double[] inputVector ) {
        data = inputVector;
    }

    public double get( int index ) {
        return data[ index ];
    }

    void set( int index, double val ) {
        data[ index ] = val;
    }

    public double cumulativeSum() {
        return Arrays.stream( data )
            .reduce( 0.0, ( sum, nextValue ) -> sum + nextValue );
    }

    double magnitude() {
        return Math.sqrt( Arrays.stream( data ).reduce( 0.0, ( a, b ) -> a + b * b ) );
    }

    double maxValue() {
        double max = 0;
        for ( int i = 0; i < size(); i ++ ) {
            double val = Math.abs( get( i ) );
            max = ( val > max ) ? val : max;
        }
        return max;
    }

    Vector stimes( double scalar ) {
        Vector ret = new Vector( size() );
        for ( int i = 0; i < data.length; i++ ) {
            ret.set( i, ( data[ i ] * scalar ) );
        }
        return ret;
    }

    //Vector stimes( double scalar, int index ) {
    //    Vector ret = new Vector( size() );
    //    for ( int i = 0; i < data.legnth; i++ ) {
    //        ret.set( i, data[ i ] );
    //    }
    //    ret.set( index, data[ i ] * scalar );
    //    return ret;
    //}

    Vector minus( Vector vector ) {
        Vector ret = new Vector( size() );
        for ( int i = 0; i < size(); i++ ) {
            ret.set( i, data[ i ] - vector.get( i ) );
        }
        return ret;
    }

    Vector plus( Vector vector ) {
        Vector ret = new Vector( size() );
        for ( int i = 0; i < size(); i++ ) {
            ret.set( i, data[ i ] + vector.get( i ) );
        }
        return ret;
    }

    public int size() {
        return data.length;
    }

    public String toString() {
        return String.format( "(%s)",
               Arrays.stream( data )
                      .mapToObj( o -> String.format( "%4.3f", o ) )
                      .collect( Collectors.joining( ", " ) ) );
    }
}
