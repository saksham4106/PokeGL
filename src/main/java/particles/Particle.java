package particles;

import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import renderer.Renderer;

public class Particle extends GameObject {
    public float life;
    public float age;
    public Vector2f velocity;

    public Particle(Transform transform, Vector2f velocity, float life){
        this.setTransform(transform);
        this.velocity = velocity;
        this.life = life;
        this.age = life;
    }

    public Particle(Vector2f position, Vector2f velocity, float life){
        this.setTransform(new Transform(position, 2,2));
        this.velocity = velocity;
        this.life = life;
        this.age = life;
    }

//    @Override
//    public void tick() {
//        if(this.age < 0) return;
//        super.tick();
//        this.sprite.color.w = this.age / this.life;
//
//    }
//
//    @Override
//    public void update(float dt, Renderer renderer) {
//        super.update(dt, renderer);
//        this.age -= dt;
//    }
}
