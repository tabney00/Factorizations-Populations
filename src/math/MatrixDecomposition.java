package src.math;

public class MatrixDecomposition {

    final Matrix D;
    final Matrix L;
    final Matrix U;


    MatrixDecomposition( Matrix A ) {
        D = makeD( A );
        L = makeL( A );
        U = makeU( A );
    }

    private Matrix makeD( Matrix A ) {
        Matrix D = new Matrix( A.size() );
        for ( int i = 0; i < A.size(); i++ ) {
            D.set( i, i, A.get( i, i ) );
        }
        return D;
    }

    private Matrix makeL( Matrix A ) {
        Matrix L = new Matrix( A.size() );
        for ( int r = 1; r < A.size() ; r++ ) {
            for ( int c = r - 1; c >= 0; c-- ) {
                L.set( r, c, A.get( r, c ) );
            }
        }
        return L;
    }

    private Matrix makeU( Matrix A ) {
        Matrix U = new Matrix( A.size() );
        for ( int r = 0; r < A.size() - 1; r++ ) {
            for ( int c = r + 1; c < A.size(); c++ ) {
                U.set( r, c, A.get( r, c ) );
            }
        }
        return U;
    }
}
