package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Plaintext;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class ElementFinderTest {

    IParserFactory parserFactoryMock = (kind, bodyLines) -> new AbstractParser(
            bodyLines) {
        protected ElementKind getKind() {
            return null;
        }

        public AbstractElement makeElement() {
            return null;
        }

        @Override
        protected void parseStructure(List<String> linesToParse) {
        }

        @Override
        protected void parseChildren(List<String> bodyLines) {
        }

        @Override
        protected Pattern getStartPattern() {
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
                ElementKind.Chapter,
                parserFactoryMock);
        assertEquals(4, testSubject.makeChildrenElements().size());
    }

    @Test
    public void getElementsReturnsEmptyListOnEmptyInput() {
        List<String> inputLines = Collections.emptyList();
        ElementFinder testSubject = new ElementFinder(inputLines,
                ElementKind.Chapter,
                parserFactoryMock);

        assertEquals(Collections.emptyList(),
                testSubject.makeChildrenElements());
    }

    @Test
    public void getElementMakesLeafElementWhenNeeded() {
        List<String> inputLines = Arrays.asList(
                "Preface", "Preface 2",
                "Art. 1. Title",
                "Article one body",
                "Art. 2. Title2",
                "Art2 body", "Body etc",
                "Art. 3.",
                "Art. 4.");

        ElementFinder testSubject = new ElementFinder(inputLines,
                // One step above Article
                ElementKind.Chapter,
                parserFactoryMock);
        List<AbstractElement> foundChildren = testSubject
                .makeChildrenElements();

        assertEquals(5, foundChildren.size());
        assert (foundChildren.get(0) instanceof Plaintext);
        assert (!(foundChildren.get(1) instanceof Plaintext));

    }
}