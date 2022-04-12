package euromillions;

import java.util.Objects;

import sets.SetOfNaturals;

import java.util.Random;

/**
 * A set of 5 numbers and 2 starts according to the Euromillions ranges.
 *
 * @author ico0
 */
public class Dip {


    private SetOfNaturals numbers;
    private SetOfNaturals starts;

    /* ------ (c) ------ */
    private static int NUMBER_MAX_RANGE = 49;
    private static int STAR_MAX_RANGE = 11;
    private static int BOTH_MIN_RANGE = 0;
    private static int NUMBER_ARRAY_LEN = 5;
    private static int STAR_ARRAY_LEN = 2;

    public Dip() {
        numbers = new SetOfNaturals();
        starts = new SetOfNaturals();
    }

    public Dip(int[] arrayOfNumbers, int[] arrayOfStarts) {
        this();

        if (NUMBER_ARRAY_LEN == arrayOfNumbers.length && STAR_ARRAY_LEN == arrayOfStarts.length) {
            boolean validNumbers = true;
            boolean validStars = true;
            for (int num : arrayOfNumbers) {
                if (!(num>BOTH_MIN_RANGE && num<=(NUMBER_MAX_RANGE+1))) {
                    validNumbers = false;
                    break;
                }
            }
            for (int num : arrayOfStarts) {
                if (!(num>BOTH_MIN_RANGE && num<=(STAR_MAX_RANGE+1))) {
                    validStars = false;
                    break;
                }
            }
            if (validNumbers && validStars) {
                numbers.add(arrayOfNumbers);
                starts.add(arrayOfStarts);
            }
            else {
                throw new IllegalArgumentException("wrong number ranges for either numbers or stars (or both)");
            }
        } else {
            throw new IllegalArgumentException("wrong number of elements in numbers/stars");
        }

    }

    public SetOfNaturals getNumbersColl() {
        return numbers;
    }

    public SetOfNaturals getStarsColl() {
        return starts;
    }

    public static Dip generateRandomDip() {
        Random generator = new Random();

        Dip randomDip = new Dip();
        for (int i = 0; i < NUMBER_ARRAY_LEN; ) {
            int candidate = generator.nextInt(NUMBER_MAX_RANGE) + 1;
            if (!randomDip.getNumbersColl().contains(candidate)) {
                randomDip.getNumbersColl().add(candidate);
                i++;
            }
        }
        for (int i = 0; i < STAR_ARRAY_LEN; ) {
            int candidate = generator.nextInt(STAR_MAX_RANGE) + 1;
            if (!randomDip.getStarsColl().contains(candidate)) {
                randomDip.getStarsColl().add(candidate);
                i++;
            }
        }
        return randomDip;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.numbers);
        hash = 29 * hash + Objects.hashCode(this.starts);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dip other = (Dip) obj;
        if (!Objects.equals(this.numbers, other.numbers)) {
            return false;
        }
        return Objects.equals(this.starts, other.starts);
    }


    /**
     * prepares a string representation of the data structure, formated for
     * printing
     *
     * @return formatted string with data
     */
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("N[");
        for (int number : getNumbersColl()) {
            sb.append(String.format("%3d", number));
        }
        sb.append("] S[");
        for (int star : getStarsColl()) {
            sb.append(String.format("%3d", star));
        }
        sb.append("]");
        return sb.toString();
    }
}
