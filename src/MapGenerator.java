import java.awt.*;

public class MapGenerator {

    private int map[][];
    private int brickWidth;
    private int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    protected void draw(Graphics2D graphics) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    graphics.setColor(Color.white);
                    graphics.fillRect(j * brickWidth + 80, i * this.brickHeight + 50, brickWidth, brickHeight);

                    graphics.setStroke(new BasicStroke(3));
                    graphics.setColor(Color.black);
                    graphics.drawRect(j * brickWidth + 80, i * this.brickHeight + 50, brickWidth, brickHeight);
                }

            }
        }

    }

    protected void setBrickValue(int value, int row, int col) {
        this.map[row][col] = value;
    }

    public int getRowMap() {
        return this.map.length;
    }

    public int getColMap() {
        return this.map[0].length;
    }

    public int getMapValue(int i, int j) {
        return this.map[i][j];
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }
}
