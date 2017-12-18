package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ParserFactory implements IParserFactory {

//    public AbstractParser getNestedParsers() {
//        ElementKind[] kinds = ElementKind.values();

//        ArrayList<ElementKind> fineToCoarse = new ArrayList<ElementKind>(
//                Arrays.asList(kinds));
//        Collections.reverse(fineToCoarse);

//        List<AbstractParser> parsers = new ArrayList<>();

//        for (ElementKind kind : fineToCoarse) {
//            parsers.add(0, makeParser(kind, new ArrayList<>(parsers)));
//        }
//        return parsers.get(0);
//    }


    @Override
    public AbstractParser makeParser(ElementKind kind, List<String>
            bodyLines) {
        throw new NotImplementedException();
    }
}
