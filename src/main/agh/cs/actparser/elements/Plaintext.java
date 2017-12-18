package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.Collections;

public class Plaintext extends AbstractElement {
    public Plaintext(String body) {
        super(body, Collections.emptyList());
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    public String toString(){
        String indent = String.join("",
                                    Collections.nCopies(getKind().toLevel(),
                                                        " "));
        return indent + "Plaintext: " + identifier + "\n";
    }
}