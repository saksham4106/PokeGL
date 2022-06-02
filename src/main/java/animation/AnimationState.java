package animation;

import renderer.Sprite;
import game.GameObject;

import java.util.ArrayList;
import java.util.List;

public class AnimationState {

    public List<AnimationFrame> animationFrames;
    public boolean loop = true;
    public int currentSpriteIndex = 0;
    private float time;
    public GameObject object;

    public AnimationState(GameObject tile, List<AnimationFrame> animationFrames){
        this.object = tile;
        this.animationFrames = animationFrames;
    }

    public AnimationState(GameObject tile){
        this.object = tile;
        this.animationFrames = new ArrayList<>();
    }

    public void addFrame(Sprite sprite, float time){
        this.animationFrames.add(new AnimationFrame(sprite, time));
    }

    public void addFrames(List<Sprite> sprites, float time){
        for (Sprite sprite : sprites) {
            this.addFrame(sprite, time);
        }
    }

    public void clear(){
        this.animationFrames.clear();
    }



    public void update(float dt){
        if(currentSpriteIndex < animationFrames.size()){
            time -= dt;
            if(time <= 0){
                if((currentSpriteIndex + 1) >= animationFrames.size()){
                    if(loop){
                        currentSpriteIndex = (currentSpriteIndex + 1) % animationFrames.size();
                    }
                }else{
                    currentSpriteIndex++;
                }
                time = animationFrames.get(currentSpriteIndex).frameTime;
                this.object.setSprite(animationFrames.get(currentSpriteIndex).sprite);
                this.object.markDirty(true);

            }
        }
    }
}
