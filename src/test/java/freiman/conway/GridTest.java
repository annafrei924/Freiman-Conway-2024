package freiman.conway;

import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    public void rleReaderSameSize() throws IOException {
        //given
        Grid grid = new Grid(3, 3);
        grid.put(1, 0);
        grid.put(2, 1);
        grid.put(0, 2);
        grid.put(1, 2);
        grid.put(2, 2);

        //when
        Grid rleGrid = new Grid(3, 3);
        RleReader rleReader = new RleReader(rleGrid, "https://conwaylife.com/patterns/glider.rle");

        //then
        assertEquals(grid.toString(), rleGrid.toString());
    }

    @Test
    public void rleReaderDifferentSize() throws IOException {
        //given
        Grid grid = new Grid(10, 10);
        grid.put(5, 4);
        grid.put(6, 5);
        grid.put(4, 6);
        grid.put(5, 6);
        grid.put(6, 6);

        //when
        Grid rleGrid = new Grid(10, 10);
        RleReader rleReader = new RleReader(rleGrid, "https://conwaylife.com/patterns/glider.rle");

        //then
        assertEquals(grid.toString(), rleGrid.toString());
    }
}
