package org.example.Utils;

import java.util.Random;

public class Utils {
    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumeration) {
        Random random = new Random();
        T[] values = enumeration.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

}
