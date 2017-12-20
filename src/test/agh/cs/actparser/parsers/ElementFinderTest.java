package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class ElementFinderTest {

    IParserFactory parserFactoryMock = (kind, bodyLines, registries) -> new
            AbstractParser(bodyLines, registries) {
        @Override
        protected ElementKind getKind() {
            return ElementKind.Plaintext;
        }

        @Override
        public AbstractElement makeElement() {
            return null;
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
        assertEquals(4, testSubject.makeChildrenElements().size());
    }

    @Test
    public void getElementsReturnsEmptyListOnEmptyInput() {
        List<String> inputLines = Collections.emptyList();
        ElementFinder testSubject = new ElementFinder(inputLines,
                ElementKind.Chapter, Collections.emptyList(),
                parserFactoryMock);

        assertEquals(Collections.emptyList(),
                testSubject.makeChildrenElements());
    }

}