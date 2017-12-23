package agh.cs.actparser;

import sun.rmi.server.Activation$ActivationSystemImpl_Stub;

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
        System.out.println(idString);
        if(idString.isEmpty()) {
            return new Identifier(0, "", kind);
        }

        Pattern splitter = Pattern.compile("^(\\d+|[IVXCDL]+|)([\\p{L}\\h]*)$");
        Matcher m = splitter.matcher(idString);

        if (!m.matches()) {
            throw new NumberFormatException(String.format("idString '%s' " +
                    "cannot be parsed", idString));
        }

        String digits = m.group(1);
        String chars = m.group(2).toLowerCase();

        System.out.println(String.format("digits: %s, chars: %s", digits,
                chars));

        int numeric = 0;

        if(RomanConverter.isRomanNumeral(digits)) {
            System.out.println(digits + " seems to be a roman nnumer");
            numeric = RomanConverter.romanToInteger(digits);
        } else if (!digits.isEmpty()) {
            System.out.println(String.format("Parsing %s as decimal", digits));
            numeric = Integer.parseInt(digits);
        }

        return new Identifier(numeric, chars, kind);
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
            if(kind == null) {
                return -1;
            } else if (other.kind == null){
                return 1;
            } else {
                return -kind.compareTo(other.kind);
            }
        } else if (numericPart != other.numericPart) {
            return numericPart - other.numericPart;
        } else {
            return stringPart.compareToIgnoreCase(other.stringPart);
        }
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
