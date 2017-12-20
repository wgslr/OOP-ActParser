package agh.cs.actparser;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentifierTest {

    @Test
    public void convertStringToIdentifier() {
        Identifier number = Identifier.fromString("12", ElementKind.Article);
        Identifier string = Identifier.fromString("ai", ElementKind.Article);
        Identifier mixed = Identifier.fromString("12a", ElementKind.Article);

        assertEquals(12, number.numericPart);
        assertEquals("", number.stringPart);
        assertEquals(0, string.numericPart);
        assertEquals("ai", string.stringPart);
        assertEquals(12, mixed.numericPart);
        assertEquals("a", mixed.stringPart);
    }


    @Test
    public void compareNumericalIdentifiers() {
        Identifier id1 = Identifier.fromString("2", ElementKind.Article);
        Identifier id2 = Identifier.fromString("11", ElementKind.Article);
        Identifier id3 = Identifier.fromString("11", ElementKind.Article);

        assertTrue(id1.compareTo(id2) < 0);
        assertTrue(id2.compareTo(id1) > 0);
        assertTrue(id2.compareTo(id3) == 0);
    }

    @Test
    public void compareTextIdentifiers() {
        Identifier id1 = Identifier.fromString("a", ElementKind.Article);
        Identifier id2 = Identifier.fromString("c", ElementKind.Article);
        Identifier id3 = Identifier.fromString("ad", ElementKind.Article);

        assertTrue(id1.compareTo(id2) < 0);
        assertTrue(id2.compareTo(id1) > 0);
        assertTrue(id1.compareTo(id3) < 0);
        assertTrue(id3.compareTo(id1) > 0);
        assertTrue(id2.compareTo(id3) > 0);
        assertTrue(id3.compareTo(id2) < 0);
    }

    @Test
    public void equalsTest() {
        Identifier fromString1 = Identifier.fromString("25a",
                ElementKind.Article);
        Identifier fromString2 = Identifier.fromString("25a",
                ElementKind.Article);
        Identifier literal1 = new Identifier(25, "a", ElementKind.Article);
        Identifier literal2 = new Identifier(25, "a", ElementKind.Article);

        assertTrue(fromString1.equals(fromString2));
        assertTrue(literal1.equals(literal2));
        assertTrue(fromString1.equals(literal1));
    }

    @Test
    public void compareEquals() {
        Identifier fromString1 = Identifier.fromString("25a",
                ElementKind.Article);
        Identifier fromString2 = Identifier.fromString("25a",
                ElementKind.Article);
        Identifier literal1 = new Identifier(25, "a", ElementKind.Article);
        Identifier literal2 = new Identifier(25, "a", ElementKind.Article);

        assertTrue(fromString1.compareTo(fromString2) == 0);
        assertTrue(literal1.compareTo(literal2) == 0);
        assertTrue(literal1.compareTo(fromString1) == 0);
    }

    @Test
    public void romanConversionTest() {
        Identifier id1 = Identifier.fromString("IX" );
        assertEquals(9, id1.numericPart);
        assertEquals("", id1.stringPart);

        Identifier id2 = Identifier.fromString("XLVI" );
        assertEquals(46, id2.numericPart);
        assertEquals("", id2.stringPart);
    }

}