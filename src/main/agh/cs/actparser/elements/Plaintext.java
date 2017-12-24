package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class Plaintext extends AbstractElement {
    public Plaintext(String idString, String content,
            LinkedHashMap<Identifier, AbstractElement> children) {
        super(idString, content, children);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    public String toString(){
        return content + "\n";
    }
}
