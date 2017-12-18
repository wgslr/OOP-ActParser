package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ElementFinderTest {

    @Test
    public void ElementFinderCreatesElementForEveryLeadTest() {
        IParserFactory pf = new IParserFactory() {
            @Override
            public AbstractParser makeParser(ElementKind kind, List<String>
                    bodyLines) {
                return new AbstractParser(bodyLines) {
                    @Override
                    public AbstractElement makeElement() {
                        return null;
                    }
                };
            }
        };

        List<String> inputLines = Arrays.asList("Art. 1. Title",
                                                "Article one body",
                                                "Art. 2. Title2",
                                                "Art2 body", "Body etc",
                                                "Art. 3.",
                                                "Art. 4.");

        ElementFinder testSubject = new ElementFinder(inputLines,
                                                      ElementKind.Chapter,
                                                      pf);
        assertEquals(4, testSubject.getElements().size());
    }

}