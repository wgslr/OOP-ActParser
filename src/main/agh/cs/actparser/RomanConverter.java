package agh.cs.actparser;

import java.util.regex.Pattern;

public class RomanConverter {
    public static boolean isRomanNumeral(String numeral) {
        return Pattern.matches("^[IVXLCDM]+$", numeral);
    }

    public static int romanToInteger(String romanNumber) {
        if (romanNumber.isEmpty()) {
            return 0;
        }
        if (romanNumber.startsWith("M")) return 1000 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("CM")) return 900 + romanToInteger(romanNumber.substring(2));
        if (romanNumber.startsWith("D")) return 500 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("CD")) return 400 + romanToInteger(romanNumber.substring(2));
        if (romanNumber.startsWith("C")) return 100 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("XC")) return 90 + romanToInteger (romanNumber.substring( 2));
        if (romanNumber.startsWith("L")) return 50 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("XL")) return 40 + romanToInteger(romanNumber.substring(2));
        if (romanNumber.startsWith("X")) return 10 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("IX")) return 9 + romanToInteger(romanNumber.substring(2));
        if (romanNumber.startsWith("V")) return 5 + romanToInteger(romanNumber.substring(1));
        if (romanNumber.startsWith("IV")) return 4 + romanToInteger(romanNumber.substring(2));
        if (romanNumber.startsWith("I")) return 1 + romanToInteger(romanNumber.substring(1));
        throw new IllegalArgumentException("Invalid roman numeral");
    }
}
