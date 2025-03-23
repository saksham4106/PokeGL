package particles;

import renderer.Renderer;

import java.util.Queue;

public class ParticleGenerator {

    public Queue<Particle> particles;
    private int particlesPerSec;



    public ParticleGenerator(int particlesPerSec) {

    }

    public void update(float dt, Renderer renderer){
        int particles = (int) (particlesPerSec * dt);
        

    }
}
