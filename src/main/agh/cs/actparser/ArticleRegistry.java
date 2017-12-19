package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.LinkedHashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ArticleRegistry implements IElementRegistry{
    NavigableMap<Identifier, AbstractElement> articles = new TreeMap<>();

    @Override
    public void add(AbstractElement article) {
        if (article instanceof Article) {
            articles.put(Identifier.fromString(article.identifier), article);
        }
    }
}
