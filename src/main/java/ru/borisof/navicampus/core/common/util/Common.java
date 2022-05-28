package ru.borisof.navicampus.core.common.util;

import java.util.Random;

public class Common {

    private static final Random random = new Random();
    private static final int LEFT_CHAR_BOUND = 97;
    private static final int RIGHT_CHAR_BOUND = 122;

    public static String getRandomAlphabeticalString(int length) {
        return random.ints(LEFT_CHAR_BOUND, RIGHT_CHAR_BOUND + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.abs(Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
    }



}
