package agh.cs.actparser.printers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Printer displaying only headers of main document elements.
 */
public class TableOfContentPrinter implements IPrinter {
    private final static ElementKind MaxSpecificity = ElementKind.Title;

    @Override
    public void print(Collection<AbstractElement> elements) {
        print(elements, 0);
    }

    /**
     * @param elements Elements to print
     * @param depth    Depth of printed element in the document tree
     */
    private void print(Collection<AbstractElement> elements,
                       final int depth) {
        elements.stream()
                .filter(e -> e.getKind().compareTo(MaxSpecificity) <= 0)
                .forEach(e -> {
                    String current = formatElement(e, depth) +
                            formatArticleRange(e);
                    if (!current.isEmpty()) {
                        System.out.println(current);
                    }

                    print(e.getChildren(), depth + 1);
                });
    }

    private String formatElement(AbstractElement element, int depth) {
        String indent = String.join("", Collections.nCopies(depth, " "));
        return indent + element.toString();
    }

    /**
     * Generates information about articles being children of given element
     *
     * @param element Parent of the articles
     * @return Created string
     */
    private String formatArticleRange(AbstractElement element) {
        List<AbstractElement> articles = element.getChildren().stream()
                .filter(e -> e.getKind().equals(ElementKind.Article))
                .collect(Collectors.toList());

        if (articles.isEmpty()) {
            return "";
        } else if (articles.size() == 1) {
            return String.format(" (Art. %s)", articles.get(0).idString);
        } else {
            return String.format(" (Art. %s - %s)", articles.get(0).idString,
                    articles.get(articles.size() - 1).idString);
        }
    }
}
