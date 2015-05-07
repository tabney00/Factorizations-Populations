package src.code;

import src.math.Matrix;
import src.math.Vector;

import java.util.stream.Collectors;
import java.util.Arrays;

public class Transform extends Matrix {

    Transform() {
        super();
    }

    Transform( double[][] data ) {
        this.data = data;
    }

    Transform( byte[] seed, int size ) {
        data = new double[ size ][ size ];
        fill( seed );
    }

    private void fill( byte[] seed ) {
        for ( int r = data.length; r > 0; r-- ) {
            data[ r - 1 ] = insertSeed( new double[ data.length ], seed, r );
        }
    }

    private double[] insertSeed( double[] array, byte[] seed, int length ) {
        int pos = length - 1;
        int bitpos = seed.length - 1;

        for ( int zeros = data.length - 1; zeros > pos; zeros-- ) {
            array[ zeros ] = 0.0;
        }

        while ( pos >= 0 ) {
            if ( bitpos >= 0 ) {
                array[ pos ] = ( double ) seed[ bitpos ];
            } else {
                array[ pos ] = 0.0;
            }
            pos--;
            bitpos--;
        }
        return array;
    }

    Code operateOn( Code input ) {
        Vector output = this.vtimes( input, 2 );
        return new Code( output );
    }

    @Override
    public String toString() {
        return Arrays.stream( data )
                .map( x -> Arrays.stream( x )
                        .mapToObj( y -> Byte.toString( ( byte ) y ) )
                        .collect( Collectors.joining( " " ) ) )
                .collect( Collectors.joining( "\n" ) );
    }
}
