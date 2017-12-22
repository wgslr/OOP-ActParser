package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;

public interface IFormatter {

    void print(Collection<AbstractElement> elements);

}
