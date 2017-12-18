package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ElementFinderTest {

    // mock parser factory
    IParserFactory pf = (kind, bodyLines) -> new AbstractParser(bodyLines) {
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
                                                      ElementKind.Chapter,
                                                      pf);
        assertEquals(4, testSubject.getChildrenElements().size());
    }

    @Test
    public void getElementsReturnsEmptyListOnEmptyInput(){
        List<String> inputLines = Collections.emptyList();
        ElementFinder testSubject = new ElementFinder(inputLines,
                                                      ElementKind.Chapter,
                                                      pf);

        assertEquals(Collections.emptyList(), testSubject.getChildrenElements());
    }

}