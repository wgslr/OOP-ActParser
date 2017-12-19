package agh.cs.actparser;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentifierTest {

    @Test
    public void convertStringToIdentifier() {
        Identifier number = Identifier.fromString("12");
        Identifier string = Identifier.fromString("ai");
        Identifier mixed = Identifier.fromString("12a");

        assertEquals(12, number.number);
        assertEquals("", number.letters);
        assertEquals(0, string.number);
        assertEquals("ai", string.letters);
        assertEquals(12, mixed.number);
        assertEquals("a", mixed.letters);
    }


    @Test
    public void compareNumericalIdentifiers() {
        Identifier id1 = Identifier.fromString("2");
        Identifier id2 = Identifier.fromString("11");
        Identifier id3 = Identifier.fromString("11");

        assertTrue(id1.compareTo(id2) < 0);
        assertTrue(id2.compareTo(id1) > 0);
        assertTrue(id2.compareTo(id3) == 0);
    }

    @Test
    public void compareTextIdentifiers() {
        Identifier id1 = Identifier.fromString("a");
        Identifier id2 = Identifier.fromString("c");
        Identifier id3 = Identifier.fromString("ad");

        assertTrue(id1.compareTo(id2) < 0);
        assertTrue(id2.compareTo(id1) > 0);
        assertTrue(id1.compareTo(id3) < 0);
        assertTrue(id3.compareTo(id1) > 0);
        assertTrue(id2.compareTo(id3) > 0);
        assertTrue(id3.compareTo(id2) < 0);
    }

    @Test
    public void equalsTest() {
        Identifier fromString1 = Identifier.fromString("25a");
        Identifier fromString2 = Identifier.fromString("25a");
        Identifier literal1 = new Identifier(25, "a");
        Identifier literal2 = new Identifier(25, "a");

        assertTrue(fromString1.equals(fromString2));
        assertTrue(literal1.equals(literal2));
        assertTrue(fromString1.equals(literal1));
    }

    @Test
    public void compareEquals() {
        Identifier fromString1 = Identifier.fromString("25a");
        Identifier fromString2 = Identifier.fromString("25a");
        Identifier literal1 = new Identifier(25, "a");
        Identifier literal2 = new Identifier(25, "a");

        assertTrue(fromString1.compareTo(fromString2) == 0);
        assertTrue(literal1.compareTo(literal2) == 0);
        assertTrue(literal1.compareTo(fromString1) == 0);

    }


}