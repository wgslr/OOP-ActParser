package agh.cs.actparser.argparser;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;
import agh.cs.actparser.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test
    public void rangeOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option articleOption = new ArgumentParser.Option
                ("articles", "a", "",
                        OptionParsers.getIdentifierRangeParser(ElementKind
                                .Article));
        parser.addOption(articleOption);

        parser.parse(new String[]{"-a", "25..30a"});

        assertEquals(new Range<>(
                        Identifier.fromString("25", ElementKind.Article),
                        Identifier.fromString("30a", ElementKind.Article)),
                parser.getResult(articleOption.name));
    }


    @Test
    public void numberOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option pointOption = new ArgumentParser.Option
                ("point", "p", "",
                        OptionParsers.getIdentifierParser(ElementKind.Point));
        parser.addOption(pointOption);

        parser.parse(new String[]{"-p", "13"});

        assertEquals(Identifier.fromString("13", ElementKind.Point),
                parser.getResult(pointOption.name));
    }

    @Test
    public void stringOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option opt1 = new ArgumentParser.Option
                ("file", "f", "",
                        OptionParsers.getTextParser());
        ArgumentParser.Option opt2 = new ArgumentParser.Option
                ("opt", "o", "",
                        OptionParsers.getTextParser());
        parser.addOption(opt1);
        parser.addOption(opt2);

        parser.parse(new String[]{"-o", "Hello", "--file", "Goodbye"});

        assertEquals("Hello", parser.getResult(opt2.name));
        assertEquals("Goodbye", parser.getResult(opt1.name));
    }
}