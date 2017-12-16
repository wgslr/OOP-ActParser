package agh.cs.actparser.parsers;

import javax.xml.bind.Element;
import java.util.List;

public abstract class ElementParser {

    public List<ElementParser> childrenParsers;
}
