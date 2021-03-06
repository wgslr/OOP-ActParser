package agh.cs.actparser.printers;

import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;

/**
 * Printer displaying full element's content.
 */
public class PlaintextPrinter implements IPrinter {

    @Override
    public void print(Collection<AbstractElement> elements) {
        elements.forEach(e -> {
                    System.out.print(
                            formatElement(e)
                    );
                    print(e.getChildren());
                });
    }

    private String formatElement(AbstractElement element) {
        return element.toString() + (element.isHeaderInline() ? "" : "\n");
    }

}
