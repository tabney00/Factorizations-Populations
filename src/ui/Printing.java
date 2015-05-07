package src.ui;

import java.util.Scanner;

public class Printing {
    public static void wraptitle( String title ) {
        System.out.println( String.format( "\n\n---- %s ----", title ) );
    }

    public static String wrap( String title, Object guts ) {
        String gutString = guts.toString();

        int headerlength = ( new Scanner( gutString ) ).nextLine().length();
        double flanklengthExact = ( headerlength - 2 - title.length() ) / 2.0;
        int flanklength = ( int ) flanklengthExact;

        String header = new String();

        for ( int i = flanklength; i > 0; i-- ) {
            header += "=";
        }

        title = String.format( "%1$s %2$s %1$s", header, title );

        if ( ( flanklengthExact - flanklength ) > 0.0 ) {
            title += "=";
        }

        return String.format( "\n%s\n%s\n", title , guts );
    }

    public static void makeOptions( String... strings ) {
        System.out.println();
        for ( int i = 0; i < strings.length; i++ ) {
            System.out.printf( "( %d ) %s\n", i, strings[ i ] );
        }
    }
}
