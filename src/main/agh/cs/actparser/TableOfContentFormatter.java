package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;
import agh.cs.actparser.parsers.ElementFinder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TableOfContentFormatter implements IFormatter {
    private final static ElementKind MaxPrecision = ElementKind.Title;

    @Override
    public void print(List<AbstractElement> elements) {
        print(elements, 0);
    }

    private void print(Collection<AbstractElement> elements, final int depth) {
        elements.stream()
                .filter(e -> e.getKind().compareTo(MaxPrecision) <= 0)
                .forEach(e -> {
                    System.out.println(
                            formatElement(e, depth) + formatSpecialInfo(e)
                    );
                    print(e.getChildren().values(), depth + 1);
                });
    }

    private String formatElement(AbstractElement element, int depth) {
        String indent = String.join("", Collections.nCopies(depth, " "));
        return indent + element.headerToString();
    }

    // TODO Should consider indirect descendants
    private String formatSpecialInfo(AbstractElement element) {
        // In this case - get the articles range
        List<AbstractElement> articles = element.getChildren()
                .values()
                .stream()
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
