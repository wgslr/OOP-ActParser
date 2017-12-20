package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.*;

public class ArticleRegistry implements IElementRegistry{
    NavigableMap<Identifier, AbstractElement> articles = new TreeMap<>();

    @Override
    public void add(AbstractElement article) {
        if (article instanceof Article) {
            articles.put(Identifier.fromString(article.identifier), article);
        }
    }


    /**
     * Returns articles with ids higher or equal to "from"
     * @return Articles
     */
    public List<AbstractElement> getRange(Identifier from, Identifier to) {
        if(!(articles.containsKey(from) && articles.containsKey(to))) {
            throw new IllegalArgumentException("There is no such article!");
        }

        return new ArrayList<>(articles.subMap(from, true, to, true)
                .values());
    }
}
