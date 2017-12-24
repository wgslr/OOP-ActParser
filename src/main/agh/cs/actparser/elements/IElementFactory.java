package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public interface IElementFactory {
    AbstractElement makeElement(
            ElementKind kind, String idString, String content,
            LinkedHashMap<Identifier, AbstractElement> children);
}
