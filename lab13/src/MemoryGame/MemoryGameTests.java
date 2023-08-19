package MemoryGame;

import org.junit.Test;

public class MemoryGameTests {
    @Test
    public void testRandomStringGeneration(){
        MemoryGame m = new MemoryGame(50, 50, 1234);
        System.out.println(m.generateRandomString(5));

    }
}
