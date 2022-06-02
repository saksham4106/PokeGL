package ui;

import game.GameObject;
import game.Transform;
import renderer.Sprite;

public class UIObject extends GameObject {

    public UIObject(Transform transform, Sprite sprite, int zIndex) {
        super(transform, sprite, zIndex);
    }
}
