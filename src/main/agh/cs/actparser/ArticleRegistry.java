package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.LinkedHashMap;

public class ArticleRegistry implements IElementRegistry{
    LinkedHashMap<Identifier, AbstractElement> articles;

    @Override
    public void Add(AbstractElement article) {
        if (article instanceof Article) {
            //articles.put(new Identifier(article.identifier), article);
        }
    }
}
