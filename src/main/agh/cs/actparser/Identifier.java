package agh.cs.actparser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describes an identifier which my consist of a numeric and alphabetic parts.
 */
public class Identifier implements Comparable<Identifier>{
    public final int number;
    public final String letters;

    public Identifier(int number, String letters) {
        this.number = number;
        this.letters = letters.toLowerCase();
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "number=" + number +
                ", letters='" + letters + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, letters);
    }

    @Override
    public int compareTo(Identifier identifier) {
        if(number != identifier.number) {
            return number - identifier.number;
        } else {
            return compareLetterPart(letters, identifier.letters);
        }
    }

    private int compareLetterPart(String left, String right) {
        for(int i = 0; i < left.length() && i < right.length(); ++i) {
            if(left.charAt(i) != right.charAt(i)){
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
        return number == that.number &&
                Objects.equals(letters, that.letters);
    }

    static Identifier fromString(String stringId) throws NumberFormatException{
        Pattern splitter = Pattern.compile("^(\\d*)([a-z]*)$");
        Matcher m = splitter.matcher(stringId);
        if(!m.matches()) {
            throw new NumberFormatException("Invalid identifier string");
        }

        Integer number = 0;
        String digits = m.group(1);
        String chars = m.group(2);
        if(!digits.isEmpty()) {
            number = Integer.parseInt(digits);
        }
        return new Identifier(number, chars);
    }
}
