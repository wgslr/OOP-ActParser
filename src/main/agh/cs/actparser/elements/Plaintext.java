package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Plaintext extends AbstractElement {
    String body;

    public Plaintext(List<String> bodyLines) {
        super("", null);
        body = joinLines(bodyLines);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    public String toString(){
        String indent = String.join("",
                                    Collections.nCopies(getKind().getLevel(),
                                                        " "));
        return indent + body;
    }

    private String joinLines(List<String> lines) {
        String joined = String.join("\n", lines);
        System.out.println(joined);
        // remove hyphenation on linebreaks
        joined.replace("-\n", "");
        joined.replace("\n", " ");
        return joined;
    }
}
