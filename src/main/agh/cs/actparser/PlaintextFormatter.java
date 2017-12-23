package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;

public class PlaintextFormatter implements IFormatter {

    @Override
    public void print(Collection<AbstractElement> elements) {
        elements.forEach(e -> {
                    System.out.print(
                            formatElement(e)
                    );
                    print(e.getChildren().values());
                });
    }

    private String formatElement(AbstractElement element) {
        return element.toString() + (element.isHeaderInline() ? "" : "\n");
    }

}
