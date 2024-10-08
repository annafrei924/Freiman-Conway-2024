package freiman.conway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        Grid grid = new Grid(3, 3);
        grid.put(0, 1);
        grid.put(1, 1);
        grid.put(2, 1);

        //when
        grid.nextGen();

        //then
        assertEquals("010\n010\n010\n", grid.toString());
    }

    @Test
    public void clear() {
        //given
        Grid grid = new Grid(3, 3);
        grid.put(0, 1);
        grid.put(1, 1);
        grid.put(2, 1);

        //when
        grid.clear();

        //then
        assertEquals("000\n000\n000\n", grid.toString());
    }

    @Test
    public void clearWithResize() {
        //given
        Grid grid = new Grid(3, 3);
        grid.put(0, 1);
        grid.put(1, 1);
        grid.put(2, 1);

        //when
        grid.clear(2, 2);

        //then
        assertEquals("00\n00\n", grid.toString());
    }

    @Test
    public void rleReader() {
        //given
        Grid grid = new Grid(3, 3);
        grid.put(1, 0);
        grid.put(2, 1);
        grid.put(0, 2);
        grid.put(1, 2);
        grid.put(2, 2);

        //when
        Grid rleGrid = new Grid(3, 3);
        rleGrid.readRle("https://conwaylife.com/patterns/glider.rle");

        //then
        assertEquals(grid.toString(), rleGrid.toString());
    }
}
