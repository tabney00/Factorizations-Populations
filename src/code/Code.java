package src.code;

import src.math.Vector;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Code extends Vector {

    Code() {
        super();
    }

    Code( Vector inputVector ) {
        this( inputVector.data );
    }

    Code( double[] inputVector ) {
        data = new double[ inputVector.length ];
        for ( int i = 0; i < inputVector.length; i++ ) {
            data[ i ] = Math.abs( inputVector[ i ] ) % 2;
        }
    }

    public Code( double[] inputVector, int extension ) {
        data = new double[ inputVector.length + extension ];
        for ( int i = 0; i < inputVector.length; i++ ) {
            data[ i ] = inputVector[ i ];
        }
        extend( extension );

    }

    public Code( int length, int extension ) {
        data = new double[ length + extension ];
        Random rand = new Random();
        for ( int i = 0; i < length; i++ ) {
            if ( rand.nextBoolean() ) {
                data[ i ] = 1.0;
            } else {
                data[ i ] = 0.0;
            }
        }
        extend( extension );
    }

    private void extend( int amount ) {
        for ( int i = data.length - amount; i < data.length ; i++ ) {
            data[ i ] = 0.0;
        }
    }

    @Override
    public String toString() {
        return String.format( "(%s)",
               Arrays.stream( data )
                      .mapToObj( x -> Byte.toString( ( byte ) x ) )
                      .collect( Collectors.joining( ", " ) ) );
    }
}
