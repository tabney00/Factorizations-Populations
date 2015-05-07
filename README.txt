Compile the source code by running the following command in a terminal:

    javac @src/srcs

Launch the program by running:

    java src.Runner


Convolutional Codes:

    Codes can be imported via placing the string of 0s and 1s into
    "dat/code_drop.dat". Spaces or newlines are not allowed.

    More import options can be found within the program.

Mathematical Operations:

    General mathematical operations are selection (4) from the main menu.

    Working example files have been provided. Matrix and vector inputs can be passed in as follows:

        Jacobi and Guass-Seidel:

            The vectors and matrices used are A * x0 = y.
            Place the augmented matrix A|y into "dat/augmented_matrix_drop.dat"
            Place the initial x0 into "dat/x0_vector_drop.dat"

            Note that each element of the x0 vector should be separated
            by a new line (enter key).

        Power method:

            This needs A|y, where A is a nxn matric and y is the nx1
            initial vector.

            Place the augmented matrix into "dat/augmented_matrix_drop.dat"

            A tolerance will be requested from the terminal before
            calculations proceed.

        LU Decomposition and QR Decomposition:

            Input should be a nxn matrix into the file "dat/matrix_drop.dat"
            The results will be printed onto the screen.

            For solving a system of equations, use
            "dat/augmented_matrix_drop.dat"

    In general, methods which solve a system of equations use
    "/dat/augmented_matrix_drop.dat".
