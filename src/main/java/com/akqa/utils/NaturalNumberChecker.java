package com.akqa.utils;

import java.util.regex.Pattern;

public class NaturalNumberChecker {

    public static final Pattern PATTERN = Pattern.compile("^\\d+$");

    public static boolean isNaturalNumber(CharSequence input) {
        return input != null && PATTERN.matcher(input).matches();
    }
}
