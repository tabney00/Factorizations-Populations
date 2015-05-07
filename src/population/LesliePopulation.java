package src.population;

import src.math.Matrix;
import src.math.Vector;

import java.util.ArrayList;

public class LesliePopulation {
    private final int YEAR_INCREMENT;
    private final int STARTING_YEAR;

    private int year;

    private Matrix matrix;
    private Vector initialPop;
    private ArrayList<LeslieData> pops;

    public LesliePopulation( Matrix matrix, Vector initialPop, int startingYear, int yearIncrement ) {

        YEAR_INCREMENT = yearIncrement;
        STARTING_YEAR = startingYear;
        this.year = startingYear;

        this.matrix = matrix;
        this.initialPop = initialPop;

        LeslieData initialPopData = new LeslieData( initialPop, STARTING_YEAR );

        pops = new ArrayList<>();
        pops.add( initialPopData );
        year += YEAR_INCREMENT;

    }

    // Need to test this independently.
    public LeslieData getPopulationByYear( int year ) {

        int relativeYears = year - STARTING_YEAR;
        if ( relativeYears % YEAR_INCREMENT != 0 ) {
            throw new IllegalArgumentException( "Years must be by the given year-increment." );
        }

        int index = relativeYears / YEAR_INCREMENT;
        if ( year < STARTING_YEAR || index >= pops.size() ) {
            throw new IllegalArgumentException( "The year " + year + " was not calculated." );
        }

        return pops.get( index );
    }

    public LeslieData calcNextPopulation() {
        LeslieData prevPopData = pops.get( pops.size() - 1 );
        Vector currentPop = matrix.vtimes( prevPopData.population );
        LeslieData currentPopData = new LeslieData( currentPop, year, prevPopData.popTotal );
        pops.add( currentPopData );

        year += YEAR_INCREMENT;

        return currentPopData;
    }

    //Testing only
    public ArrayList<LeslieData> getPops() {
        return pops;
    }
}
