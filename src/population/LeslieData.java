package src.population;

import src.math.Vector;
import src.ui.Printing;

public class LeslieData {

    Vector population;
    double popTotal;
    double popTotalDiff;

    int year;

    LeslieData( Vector initialPopulation, int year ) {
        this.population = initialPopulation;
        this.year = year;
        popTotal = initialPopulation.cumulativeSum();
    }

    public LeslieData( Vector population, int year, double prevPopTotal ) {
        this( population, year );
        popTotalDiff = popTotal - prevPopTotal;
    }

    public String toString() {
        return Printing.wrap( "Year: " + year,
                String.format( "%s\nΔ Total Populations = %4.3f\nTotal Population = %4.3f",
                    population, popTotalDiff, popTotal ) );
    }
}
