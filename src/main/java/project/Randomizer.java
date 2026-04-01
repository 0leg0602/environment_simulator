package project;

import java.util.Random;

public class Randomizer {

    /**
     * Generates a random integer in the inclusive range [min, max].
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a random integer between min and max
     */
    public static int rand_range(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min  + 1);
    }
}
