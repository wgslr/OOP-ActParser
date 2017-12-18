package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ParserFactory implements IParserFactory {

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
            case Section:
                return new SectionParser(childrenParsers);
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

    @Override
    public AbstractParser makeParser(ElementKind kind, List<String>
            bodyLines) {
        throw new NotImplementedException();
    }
}
