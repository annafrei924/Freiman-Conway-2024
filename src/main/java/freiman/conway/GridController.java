package freiman.conway;

import java.io.IOException;

public class GridController
{
    private final Grid model;
    private final GridComponent view;
    private final RleParser parser;

    public GridController(Grid model, GridComponent view, RleParser parser)
    {
        this.model = model;
        this.view = view;
        this.parser = parser;
    }

    public void toggleCell(int screenX, int screenY)
    {
        int x = screenX / view.getCellSize();
        int y = screenY / view.getCellSize();

        if (model.isAlive(x, y)) {
            model.remove(x, y);
        } else {
            model.put(x, y);
        }
        view.repaint();
    }

    public void paste(String data) throws IOException {
        parser.loadFromRle(data);
        view.repaint();
    }

    public void startTimer()
    {

    }

    public void stopTimer()
    {

    }

}
