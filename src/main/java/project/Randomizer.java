package project;

import java.util.Random;

public class Randomizer {

    public static int rand_range(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min  + 1);
    }
}
