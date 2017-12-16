package agh.cs.actparser.parsers;

import agh.cs.actparser.DocumentElement;

public class ParserFactory {

    public ParserAbstract getChildrenParser(DocumentElement currentElement) {
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
            default:
                return new DocumentParser();
        }
    }
}
