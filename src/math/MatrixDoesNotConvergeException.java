package src.math;

public class MatrixDoesNotConvergeException extends Exception {
    int attemptedIterations;

    MatrixDoesNotConvergeException( int attemptedIterations ) {
        this.attemptedIterations = attemptedIterations;
    }

    @Override
    public String getMessage() {
        return String.format( "The matrix did not converge after %d iterations.",
                attemptedIterations);
    }
}
