package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class Plaintext extends AbstractElement {
    String body;

    public Plaintext(List<String> bodyLines) {
        super("", new LinkedHashMap< Identifier,AbstractElement>());
        body = joinLines(bodyLines);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    public String toString(){
        return body;
    }

    private String joinLines(List<String> lines) {
        String joined = String.join("\n", lines);
        return joined + "\n";
    }
}
