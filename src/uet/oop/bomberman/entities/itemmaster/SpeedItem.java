package uet.oop.bomberman.entities.itemmaster;

import javafx.scene.image.Image;

public class SpeedItem extends Item{

    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        System.out.println("LOL");
    }
}
