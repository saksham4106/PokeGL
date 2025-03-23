package game.components;

import game.GameObject;

public abstract class Component {

    public GameObject gameObject;
    public abstract void update( float dt);

}
