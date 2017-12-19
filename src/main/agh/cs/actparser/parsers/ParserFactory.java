package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class ParserFactory implements IParserFactory {


    @Override
    public AbstractParser makeParser(ElementKind kind, List<String>
            linesToParse) {
        switch (kind) {
            case Document:
                return new DocumentParser(linesToParse);
            case Section:
                return new SectionParser(linesToParse);
            case Chapter:
                return new ChapterParser(linesToParse);
            case Subchapter:
                return new SubchapterParser(linesToParse);
            case Article:
                return new ArticleParser(linesToParse);
            case Paragraph:
                return new ParagraphParser(linesToParse);
            case Point:
                return new PointParser(linesToParse);
            case Letter:
                return new LetterParser(linesToParse);
            case Indent:
                return new IndentParser(linesToParse);
            case Plaintext:
                return new PlaintextParser(linesToParse);
            default:
                throw new IllegalArgumentException();
        }
    }

}
