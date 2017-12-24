package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class ElementFactory implements IElementFactory {

    @Override
    public AbstractElement makeElement(
            ElementKind kind, String idString, String content,
            LinkedHashMap<Identifier, AbstractElement> children) {
        switch (kind) {

            case Document:
                return new Document(idString, content, children);
            case Section:
                return new Section(idString, content, children);
            case Chapter:
                return new Chapter(idString, content, children);
            case Title:
                return new Title(idString, content, children);
            case Article:
                return new Article(idString, content, children);
            case Paragraph:
                return new Paragraph(idString, content, children);
            case Point:
                return new Point(idString, content, children);
            case Letter:
                return new Letter(idString, content, children);
            case Plaintext:
                return new Plaintext(idString, content, children);
            default:
                throw new InternalError("Invalid Enum state!");
        }
    }
}
