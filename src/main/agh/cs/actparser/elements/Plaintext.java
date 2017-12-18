package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.Collections;
import java.util.List;

public class Plaintext extends AbstractElement {
    public Plaintext(String body) {
        super(body, Collections.emptyList());
    }

    public Plaintext(List<String> bodyLines) {
        super(String.join(" ", bodyLines), Collections.emptyList());
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    public String toString(){
        String indent = String.join("",
                                    Collections.nCopies(getKind().getLevel(),
                                                        " "));
        return indent + "Plaintext: " + identifier + "\n";
    }
}
