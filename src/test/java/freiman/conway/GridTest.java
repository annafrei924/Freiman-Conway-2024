package freiman.conway;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GridTest {

    @Test
    public void string() {
        // given
        Grid grid = new Grid(3, 3);

        // when
        String actual = grid.toString();

        // then
        assertEquals("000\n000\n000\n", actual);
    }

    @Test
    public void nextGen() {
        //given
        Grid grid = new Grid(3,3);
        grid.put(0,0);
        grid.put(1,0);
        grid.put(2,1);
        grid.put(2,2);

        //when
        grid.nextGen();

        //then
        assertEquals("010\n001\n000\n", grid.toString());

    }
}
