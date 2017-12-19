package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;

import java.util.List;

public interface IParserFactory {

    /**
     * Creates parser capable of processing given element kind.
     * @param kind Kind of document element to process
     * @param bodyLines Content to be processed by the parser
     * @return Parser matching given kind
     */
    AbstractParser makeParser(ElementKind kind, List<String>
            bodyLines, List<IElementRegistry> registries);
}
