package agh.cs.actparser;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CleanerTest {

    @Test
    public void filter() {
        Cleaner c = new Cleaner(new String[]{"remove"});
        List<String> lines = Arrays.asList("preserve", "remove", "will be " +
                "removed");
        List<String> expected = Arrays.asList("preserve");

        assertEquals(expected, c.filter(lines));
    }

    @Test
    public void filterHandlesMultiplePredicates() {
        List<String> lines =
                Arrays.asList("very bad", "preserve", "evil", "good",
                              "bad evil");
        List<String> expected = Arrays.asList("preserve", "good");

        Cleaner c = new Cleaner(new String[]{"bad", "evil"});
        assertEquals(expected, c.filter(lines));
    }

}