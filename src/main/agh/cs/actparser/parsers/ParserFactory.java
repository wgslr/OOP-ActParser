package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;

import java.util.List;

public class ParserFactory implements IParserFactory {


    @Override
    public AbstractParser makeParser(ElementKind kind, List<String>
            linesToParse, List<IElementRegistry> registries) {
        switch (kind) {
            case Document:
                return new DocumentParser(linesToParse, registries);
            case Section:
                return new SectionParser(linesToParse, registries);
            case Chapter:
                return new ChapterParser(linesToParse, registries);
            case Subchapter:
                return new SubchapterParser(linesToParse, registries);
            case Article:
                return new ArticleParser(linesToParse, registries);
            case Paragraph:
                return new ParagraphParser(linesToParse, registries);
            case Point:
                return new PointParser(linesToParse, registries);
            case Letter:
                return new LetterParser(linesToParse, registries);
            case Indent:
                return new IndentParser(linesToParse, registries);
            case Plaintext:
                return new PlaintextParser(linesToParse, registries);
            default:
                throw new IllegalArgumentException();
        }
    }

}
