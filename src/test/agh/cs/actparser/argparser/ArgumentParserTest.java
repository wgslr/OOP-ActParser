package agh.cs.actparser.argparser;

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
}