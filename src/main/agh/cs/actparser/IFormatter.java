package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;
import java.util.Collections;

public interface IFormatter {

    default void print(AbstractElement element) {
        print(Collections.singletonList(element));
    }

    void print(Collection<AbstractElement> elements);

}
