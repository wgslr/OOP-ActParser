package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;

import java.util.List;

public interface IParserFactory {
    AbstractParser makeParser(ElementKind kind, List<String>
            bodyLines);
}
