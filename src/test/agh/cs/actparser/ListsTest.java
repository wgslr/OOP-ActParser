package agh.cs.actparser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListsTest {

    @Test
    public void sublist() {
        List<Integer> original = new ArrayList<>();
        original.addAll(Arrays.asList(0, 1, 2, 3, 4, 5));

        List<Integer> sub = original.subList(1, 5);

        assertEquals(Arrays.asList(1, 2, 3, 4), sub);


        sub.add(15);
        sub.add(0, 10);
        assertEquals(Arrays.asList(10, 1, 2, 3, 4, 15), sub);
        assertEquals(Arrays.asList(0, 10, 1, 2, 3, 4, 15, 5), original);

        sub.remove(0);
        sub.add(0, 7);
        sub.add(0, 7);

        assertEquals(Arrays.asList(7, 7, 1, 2, 3, 4, 15), sub);
        assertEquals(Arrays.asList(0, 7, 7, 1, 2, 3, 4, 15, 5), original);
    }

    @Test
    public void appendToCopy(){
        List<Integer> original = new ArrayList<>();
        original.add(0);
        List<Integer> sub = original.subList(0, 1);

        assertEquals(Arrays.asList(0), original);
        assertEquals(Arrays.asList(0), sub);
        System.out.println(sub);

        original.add(0, 1);
        assertEquals(Arrays.asList(1, 0), original);
        //assert(sub.get(0) == 0 && sub.size() == 1);

    }
}
