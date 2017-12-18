package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ElementFactory {

    AbstractElement makeElement(ElementKind kind) {
        //switch kind
        throw new NotImplementedException();
    }

    AbstractElement makeDefaultElement(List<String> body) {
        return new Plaintext(body);
    }
}
