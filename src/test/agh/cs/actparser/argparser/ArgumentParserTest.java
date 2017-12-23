package agh.cs.actparser.argparser;

import agh.cs.actparser.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test
    public void addOptionTest(){

        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option articleOption = new ArgumentParser.Option("article", "a", "",
                ArgumentType.Number);

        parser.addOption(articleOption);

        assertEquals(articleOption, parser.getOptionByName("article"));
    }

    @Test
    public void matchOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option articleOption = new ArgumentParser.Option("article", "a", "",
                ArgumentType.Number);
        parser.addOption(articleOption);

        assertEquals(articleOption, parser.matchOption("--article"));
        assertEquals(articleOption, parser.matchOption("-a"));
    }


    @Test
    public void rangeOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option articleOption = new ArgumentParser.Option
                ("articles", "a", "",
                ArgumentType.Range);
        parser.addOption(articleOption);

        parser.parse(new String[]{"-a", "25..30a"});

        assertEquals(new Range<>("25", "30a"), parser.getResult(articleOption.name));
    }


    @Test
    public void numberOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option pointOption = new ArgumentParser.Option
                ("point", "p", "",
                        ArgumentType.Number);
        parser.addOption(pointOption);

        parser.parse(new String[]{"-p", "13"});

        assertEquals(13, parser.getResult(pointOption.name));
    }

    @Test
    public void stringOptionTest() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentParser.Option opt1 = new ArgumentParser.Option
                ("file", "f", "",
                        ArgumentType.Text);
        ArgumentParser.Option opt2 = new ArgumentParser.Option
                ("opt", "o", "",
                        ArgumentType.Text);
        parser.addOption(opt1);
        parser.addOption(opt2);

        parser.parse(new String[]{"-o", "Hello", "--file", "Goodbye"});

        assertEquals("Hello", parser.getResult(opt2.name));
        assertEquals("Goodbye", parser.getResult(opt1.name));
    }
}