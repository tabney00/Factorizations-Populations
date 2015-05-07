package src.math;

public class AugmentedMatrix {
    public Matrix matrix;
    public Vector vector;

    public AugmentedMatrix( double[][] matrixArr, double[] vectorArr ) {
        matrix = new Matrix( matrixArr );
        vector = new Vector( vectorArr );
    }
}

