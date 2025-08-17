package game.components;

import game.GameObject;
import org.joml.Vector2f;

public class FadeOutComponent extends Component {
    private Vector2f diff;
    private final float time;
    private float life;
    private final float scale;
    private Vector2f endPosition;
    private onFade onFade;

    public FadeOutComponent(GameObject gameObject, Vector2f endPosition, float time, float scale, onFade onFade) {
        this.gameObject = gameObject;
        this.time = time;
        this.scale = scale;
        this.endPosition = endPosition;
        this.onFade = onFade;
    }

    private void init() {

        this.life = time;
        if(endPosition != null) {
            Vector2f initPos = gameObject.getTransform().position;
            endPosition.sub(initPos);
//            endPosition.div(time);
            this.diff = endPosition;
        }else{
            this.diff = new Vector2f();
        }
    }

    boolean first = true;
    @Override
    public void update(float dt) {
        if(first){
            init();
            first = false;
        }

        this.life -= dt;

        if(time != 0){
            gameObject.getSprite().color.w = curve(life/time);
        }

//        System.out.println(life);
        if(life < 0){
            this.onFade.onFade();
            gameObject.remove = true;
        }


        gameObject.markDirty(true);

//        float t = 1 - (1 - scale) * (1 - life/time) * dt;
//        gameObject.getTransform().scale.mul(t);
//        gameObject.setPosition(new Vector2f(gameObject.getPosition()).add(new Vector2f(diff).mul(dt)));
//        gameObject.getTransform().position.add(new Vector2f(diff).mul(dt).div(time));

//        float timeElapsed = time - life;
//        float curve = this.curve(timeElapsed / time);
//        gameObject.getTransform().position.add(new Vector2f(diff).mul(curve * dt));
//        gameObject.getTransform().scale.mul(curve * dt);
    }

    private float curve(float x){
        return (float) (1 - Math.pow(1 - x, 9));

    }

    public interface onFade{
        void onFade();
    }
}
