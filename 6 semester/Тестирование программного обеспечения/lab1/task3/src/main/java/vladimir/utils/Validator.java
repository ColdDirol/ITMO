package vladimir.utils;

import java.util.Objects;

public class Validator {

    public static void checkStrings(String... strings) {
        for (String str : strings) {
            Objects.requireNonNull(str, "String cannot be null");
            if (str.trim().isEmpty()) {
                throw new IllegalArgumentException("String cannot be empty");
            }
        }
    }

    public static void checkPositiveIntegers(Integer... integers) {
        for (Integer integer : integers) {
            Objects.requireNonNull(integer, "Integer cannot be null");
            if (integer < 0) {
                throw new IllegalArgumentException("Integer cannot be negative");
            }
        }
    }

    public static void checkEnum(Object enumValue) {
        Objects.requireNonNull(enumValue, "Enum cannot be null");
    }

    public static void checkObject(Object object) {
        Objects.requireNonNull(object, "Object cannot be null");
    }
}
