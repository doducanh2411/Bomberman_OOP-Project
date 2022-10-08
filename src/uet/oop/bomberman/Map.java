package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemiesmaster.Balloom;
import uet.oop.bomberman.entities.enemiesmaster.Enemy;
import uet.oop.bomberman.entities.stillobjectmaster.Brick;
import uet.oop.bomberman.entities.stillobjectmaster.Grass;
import uet.oop.bomberman.entities.stillobjectmaster.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.BombermanGame.*;

public class Map {
    private List<List<Entity>> mapInfo = new ArrayList<>(); //Can be changed.
    private List<Entity> renderObject = new ArrayList<>();
    int LEVEL;

    public Map(int LEVEL) {
        this.LEVEL = LEVEL;
        readMapFromFile();
    }

    /**
     * Read map.
     */
    public void readMapFromFile() {
        File file = new File("res/levels/Level" + (LEVEL + 1) + ".txt");
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println("No file exist");
        }

        scanner.nextLine(); //Read first line in Level.txt.
        String rowString = ""; //Save info of a row into string.

        for (int i = 0; i < HEIGHT; i++) {
            rowString = scanner.nextLine();
            List<Entity> stillObject = new ArrayList<>();
            for (int j = 0; j < WIDTH; j++) {
                switch (rowString.charAt(j)) {
                    case 'p':
                        stillObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        renderObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        Entity bomberman = new Bomber(j, i, new CollisionManager(this, Bomber.WIDTH, Bomber.HEIGHT));
                        GameController.entities.get(LEVEL).add(bomberman);
                        break;
                    case '#':
                        stillObject.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        renderObject.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                    case '*':
                        stillObject.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        renderObject.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    case '1':
                        stillObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        renderObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        Enemy ballom = new Balloom(j, i, Sprite.balloom_left1.getFxImage(), new CollisionManager(this, Enemy.WIDTH, Enemy.HEIGHT));
                        GameController.entities.get(LEVEL).add(ballom);
                        break;
                    default:
                        stillObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        renderObject.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                }
            }
            mapInfo.add(stillObject);
        }
        scanner.close();
    }
    public void mapRender(GraphicsContext gc) {
        for (Entity x : renderObject) {
            x.render(gc);
        }
    }

    public Entity getEntityAt(int x, int y) {
        return mapInfo.get(y / Sprite.SCALED_SIZE).get(x / Sprite.SCALED_SIZE);
    }
    public void reset() {
        renderObject.clear();
        GameController.entities.get(LEVEL).clear();
        readMapFromFile();
    }
}
