package agh.cs.actparser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describes an idString which my consist of a numeric and alphabetic parts.
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

    public static Identifier fromString(String idString, ElementKind kind) {
        if (RomanConverter.isRomanNumeral(idString)) {
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
            throw new NumberFormatException(
                    "Invalid idString '" + idString + "'");
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
     *
     * @param romanNumeral Numeral string to be parsed
     * @param kind         Kind of element this idString describes
     * @return Created Identifier
     */
    private static Identifier fromRoman(String romanNumeral, ElementKind kind) {
        return new Identifier(RomanConverter.romanToInteger(romanNumeral),
                "", kind);
    }

    @Override
    public String toString() {
        return String.format("%s(%s%s)", kind,
                numericPart != 0 ? numericPart : "",
                stringPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, numericPart, stringPart);
    }

    @Override
    public int compareTo(Identifier other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if (kind != other.kind) {
            // more specific kinds should be first
            // at least when being children of the same element
            return -kind.compareTo(other.kind);
        } else if (numericPart != other.numericPart) {
            return numericPart - other.numericPart;
        } else {
            return compareLetterPart(stringPart, other.stringPart);
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

}
