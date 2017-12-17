package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;

public class ParserFactory {

    public AbstractParser makeParser(ElementKind currentElement) {
        switch (currentElement) {
            case Document:
                return new DocumentParser();
            case Section:
                return new SectionParser();
            case Chapter:
                return new ChapterParser();
            case Article:
                return new ArticleParser();
            case Paragraph:
                return new ParagraphParser();
            case Point:
                return new PointParser();
            case Letter:
                return new LetterParser();
            case Indent:
                return new IndentParser();
            default:
                return new DocumentParser();
        }
    }
}
