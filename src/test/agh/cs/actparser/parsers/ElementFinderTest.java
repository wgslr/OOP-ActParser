package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Letter;
import agh.cs.actparser.elements.Plaintext;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementFinderTest {

    IParserFactory parserFactoryMock = (kind, bodyLines, registries) -> new
            AbstractParser(bodyLines, registries) {
        @Override
        protected ElementKind getKind() {
            return ElementKind.Plaintext;
        }

        @Override
        public AbstractElement makeElement() {
            return new Plaintext(bodyLines);
        }
    };

    @Test
    public void getElementsCreatesElementForEveryLeadTest() {
        List<String> inputLines = Arrays.asList("Art. 1. Title",
                "Article one body",
                "Art. 2. Title2",
                "Art2 body", "Body etc",
                "Art. 3.",
                "Art. 4.");

        ElementFinder testSubject = new ElementFinder(inputLines,
                // One step above Article
                ElementKind.Chapter, Collections.emptyList(),
                parserFactoryMock);
        assertEquals(4, testSubject.makeElements().size());
    }

    @Test
    public void getElementsReturnsEmptyListOnEmptyInput() {
        List<String> inputLines = Collections.emptyList();
        ElementFinder testSubject = new ElementFinder(inputLines,
                ElementKind.Chapter, Collections.emptyList(),
                parserFactoryMock);

        assertEquals(Collections.emptyList(),
                testSubject.makeElements());
    }

    @Test
    public void longWordDoesNotMakeALetter() {
        List<String> lines = Arrays.asList(
            "f) umowa przewidująca zarządzanie innym przedsiębiorcą (przedsiębiorcą",
            "zależnym) lub przekazywanie zysku przez takiego przedsiębiorcę");
        ElementFinder testSubject = new ElementFinder(lines, ElementKind
                .Article );

        List<AbstractElement> children = testSubject.makeElements();

        assertEquals(1, children.size());
        assertTrue(children.get(0) instanceof Letter);
    }
}