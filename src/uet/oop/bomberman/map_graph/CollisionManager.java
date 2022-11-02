package uet.oop.bomberman.map_graph;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingobject.MovingObject;
import uet.oop.bomberman.entities.movingobject.enemies.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map_graph.Map;

import java.util.List;

public class CollisionManager {
    private final Map map;
    private final int CENTER_OBJECT_HEIGHT;
    private final int CENTER_OBJECT_WIDTH;

    /**
     * Constructor for collision.
     */
    public CollisionManager(Map map, int objectWidth, int objectHeight) {
        this.map = map;
        CENTER_OBJECT_WIDTH = objectWidth;
        CENTER_OBJECT_HEIGHT = objectHeight;
    }

    public Map getMap() {
        return map;
    }

    public Entity topLeftCorner;
    public Entity topRightCorner;
    public Entity downLeftCorner;
    public Entity downRightCorner;

    /**
     * Check if object collides with wall in map.
     * x and y are pixel position of object,
     * dir and speed is updated continually.
     */
    public <T extends MovingObject> boolean collide(T movingObject, int curX, int curY, String dir, int OBJECT_SPEED) {
        switch (dir) {
            case "UP":
                curY -= OBJECT_SPEED;
                break;
            case "DOWN":
                curY += OBJECT_SPEED;
                break;
            case "LEFT":
                curX -= OBJECT_SPEED;
                break;
            case "RIGHT":
                curX += OBJECT_SPEED;
                break;
        }
        topLeftCorner = map.getEntityAt(curX, curY);
        topRightCorner = map.getEntityAt(curX + CENTER_OBJECT_WIDTH, curY);
        downLeftCorner = map.getEntityAt(curX, curY + CENTER_OBJECT_HEIGHT);
        downRightCorner = map.getEntityAt(curX + CENTER_OBJECT_WIDTH, curY + CENTER_OBJECT_HEIGHT);
        boolean checkIfColliding = false;
        if (curX <= 0 || curX + Sprite.SCALED_SIZE >= map.getWidthPixel() || curY <= 0 || curY + Sprite.SCALED_SIZE >= map.getHeightPixel()) {
            return true;
        }
        List<Class> cannotPassEntityList = movingObject.getCannotPassEntityList();
        for (int i = 0; i < cannotPassEntityList.size(); i++) {
            if (topLeftCorner != null)
                if (topLeftCorner.getClass().equals(cannotPassEntityList.get(i))) {
                    checkIfColliding = true;
                }
            if (!checkIfColliding) {
                if (downLeftCorner != null)
                    if (downLeftCorner.getClass().equals(cannotPassEntityList.get(i))) {
                        checkIfColliding = true;
                    }
            }
            if (!checkIfColliding) {
                if (topRightCorner != null)
                    if (topRightCorner.getClass().equals(cannotPassEntityList.get(i))) {
                        checkIfColliding = true;
                    }
            }
            if (!checkIfColliding) {
                if (downRightCorner != null)
                    if (downRightCorner.getClass().equals(cannotPassEntityList.get(i))) {
                        checkIfColliding = true;
                    }
            }
        }

        if (checkIfColliding) {
            if (movingObject instanceof Enemy)
                ((Enemy) movingObject).randomSpeed(2, 3);
        }
        return checkIfColliding;
    }
}
