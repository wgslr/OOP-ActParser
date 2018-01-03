package agh.cs.actparser;

import org.junit.Test;

import static org.junit.Assert.*;

public class RomanConverterTest {

    @Test
    public void romanToIntegerTest() {
        assertEquals(1, RomanConverter.romanToInteger("I"));
        assertEquals(3, RomanConverter.romanToInteger("III"));
        assertEquals(5, RomanConverter.romanToInteger("V"));
        assertEquals(49, RomanConverter.romanToInteger("XLIX"));
        assertEquals(8, RomanConverter.romanToInteger("VIII"));
    }
}