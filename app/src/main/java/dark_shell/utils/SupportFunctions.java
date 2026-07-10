package dark_shell.utils;

import java.util.Random;

public class SupportFunctions {
    public static long randomAtOneToFive() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public static long randomAtOneToSix() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public static long randomAtOneToEight() {
        Random random = new Random();
        return random.nextInt(8) + 1;
    }

    public static long randomAtOneToTen() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public static long randomAtOneToFifteen() {
        Random random = new Random();
        return random.nextInt(15) + 1;
    }

    public static long randomAtOneToTwenty() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
}
