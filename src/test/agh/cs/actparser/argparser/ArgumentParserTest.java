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
}