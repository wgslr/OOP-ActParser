package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ParserFactory {

    public AbstractParser getNestedParsers() {
        ElementKind[] kinds = ElementKind.values();

        ArrayList<ElementKind> fineToCoarse = new ArrayList<ElementKind>(
                Arrays.asList(kinds));
        Collections.reverse(fineToCoarse);

        List<AbstractParser> parsers = new ArrayList<>();

        for (ElementKind kind : fineToCoarse) {
            parsers.add(0, makeParser(kind, new ArrayList<>(parsers)));
        }
        return parsers.get(0);
    }

    public AbstractParser makeParser(ElementKind currentElement,
                                     List<AbstractParser> childrenParsers)

    {
        switch (currentElement) {
            case Document:
                return new DocumentParser(childrenParsers);
            //case Section:
            //    return new SectionParser();
            //case Chapter:
            //    return new ChapterParser();
            case Article:
                return new ArticleParser(childrenParsers);
            case Paragraph:
                return new ParagraphParser(childrenParsers);
            case Point:
                return new PointParser(childrenParsers);
            //case Letter:
            //    return new LetterParser();
            //case Indent:
            //    return new IndentParser();
            default:
                return new PlaintextParser(childrenParsers);
        }
    }
}
