package animation;

import renderer.Sprite;

public class AnimationFrame {

    public Sprite sprite;
    public float frameTime;

    public AnimationFrame(Sprite sprite, float frameTime) {
        this.sprite = sprite;
        this.frameTime = frameTime;
    }
}
