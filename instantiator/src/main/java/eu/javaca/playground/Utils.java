package eu.javaca.playground;

import java.util.Map;

public class Utils {
    public static final Map<String, String> CONSTANTS = Map.of("String", "stringValue",
            "Boolean", "true",
            "Character", "'a'",
            "Byte", "0",
            "Short", "0",
            "Float", "0.0",
            "Double", "0.0",
            "Long", "0L",
            "Integer", "0");

    public static String getCamelCaseClassName(Class<?> any) {
        StringBuilder stringBuilder = new StringBuilder(any.getSimpleName());
        stringBuilder.setCharAt(0, Character.toLowerCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}
