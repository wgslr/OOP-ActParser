package agh.cs.actparser.elements;

import java.util.List;

public class Article extends AbstractElement {
    public final String identifier;

    public Article(List<AbstractElement> children, String identifier){
        super(children);
        this.identifier = identifier;
    }

    public String toString(){
        return "Artykuł " + identifier + ": " + toString();
    }

}
