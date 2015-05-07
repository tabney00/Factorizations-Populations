package src.ui;

import java.util.Arrays;
import java.util.HashSet;

public class Options {
    private HashSet<Integer> optionSet;

    public Options( Integer... options ) {
        optionSet = new HashSet<Integer>( Arrays.asList( options ) );
    }

    public boolean contains( int option ) {
        return optionSet.contains( option );
    }
}
