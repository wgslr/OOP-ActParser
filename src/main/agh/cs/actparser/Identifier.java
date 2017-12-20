package agh.cs.actparser;

import javax.xml.bind.Element;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describes an identifier which my consist of a numeric and alphabetic parts.
 */
public class Identifier implements Comparable<Identifier> {

    /**
     * Kind of identified element. May be null if it's not relevant to the
     * Identifier usage.
     */
    public final ElementKind kind;
    public final int numericPart;
    public final String stringPart;

    public Identifier(int numericPart, String stringPart, ElementKind
            kind) {
        this.numericPart = numericPart;
        this.stringPart = stringPart.toLowerCase();
        this.kind = kind;
    }

    public static Identifier fromString(String idString) {
        return fromString(idString, null);
    }

    public static Identifier fromString(String idString, ElementKind kind) {
        if(Pattern.matches("^[IVXL]+$", idString)){
            return fromRoman(idString, kind);
        } else {
            return fromMixed(idString, kind);
        }
    }

    private static Identifier fromMixed(String idString, ElementKind kind)
            throws
            NumberFormatException {
        Pattern splitter = Pattern.compile("^(\\d*)([a-z]*)$");
        Matcher m = splitter.matcher(idString);

        if (!m.matches()) {
            throw new NumberFormatException("Invalid identifier string");
        }

        Integer number = 0;
        String digits = m.group(1);
        String chars = m.group(2);
        if (!digits.isEmpty()) {
            number = Integer.parseInt(digits);
        }
        return new Identifier(number, chars, kind);
    }

    /**
     * Creates Identifier basing on a roman numeral.
     * @param romanNumeral Numeral string to be parsed
     * @param kind Kind of element this identifier describes
     * @return Created Identifier
     */
    private static Identifier fromRoman(String romanNumeral, ElementKind kind) {
        return new Identifier(romanToInteger(romanNumeral), "", kind);
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "numericPart=" + numericPart +
                ", stringPart='" + stringPart + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(numericPart, stringPart);
    }

    @Override
    public int compareTo(Identifier identifier) {
        if (numericPart != identifier.numericPart) {
            return numericPart - identifier.numericPart;
        } else {
            return compareLetterPart(stringPart, identifier.stringPart);
        }
    }

    private int compareLetterPart(String left, String right) {
        for (int i = 0; i < left.length() && i < right.length(); ++i) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.charAt(i) - right.charAt(i);
            }
        }
        // If one string is a prefix of another, the longer one compares higher
        return left.length() - right.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return this.compareTo(that) == 0;
    }

    private static int romanToInteger(String romanNumber) {
        if (romanNumber.isEmpty()) {
            return 0;
        }
        if (romanNumber.startsWith("L")) {
            return 50 + romanToInteger(romanNumber.substring(1));
        }
        if (romanNumber.startsWith("XL")) {
            return 40 + romanToInteger(romanNumber.substring(2));
        }
        if (romanNumber.startsWith("X")) {
            return 10 + romanToInteger(romanNumber.substring(1));
        }
        if (romanNumber.startsWith("IX")) {
            return 9 + romanToInteger(romanNumber.substring(2));
        }
        if (romanNumber.startsWith("V")) {
            return 5 + romanToInteger(romanNumber.substring(1));
        }
        if (romanNumber.startsWith("IV")) {
            return 4 + romanToInteger(romanNumber.substring(2));
        }
        if (romanNumber.startsWith("I")) {
            return 1 + romanToInteger(romanNumber.substring(1));
        }
        throw new IllegalArgumentException("Invalid roman numeral");
    }
}
