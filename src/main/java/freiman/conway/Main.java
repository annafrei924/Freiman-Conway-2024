package freiman.conway;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid(100, 100);
        new GridFrame(grid).setVisible(true);
    }

}
