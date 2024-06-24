package collision;

import game.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {

    private final int capacity;
    private final Transform bounds;
    private List<Point> points;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    private boolean isDivided = false;

    public QuadTree(Transform bounds, int capacity){
        this.bounds= bounds;
        this.capacity = capacity;
    }

    public QuadTree(Transform bounds){
        this(bounds, 4);
    }

    public void insert(Point point){
        if(!CollisionDetection.pointInBox(point, bounds)) return;

        if(points.size() < capacity){
            points.add(point);
        } else{
            if(!isDivided){
                this.subdivide();
            }

            this.northEast.insert(point);
            this.northWest.insert(point);
            this.southEast.insert(point);
            this.southWest.insert(point);
        }
    }

    public List<Point> query(Transform range, List<Point> found){
        if(found == null){
            found = new ArrayList<>();
        }
        if(!CollisionDetection.boxInBox(range, this.bounds)) return new ArrayList<>();

        for (Point point : points) {
            if(CollisionDetection.pointInBox(point, range)){
                found.add(point);
            }
        }
        if(this.isDivided){
            northEast.query(range, found);
            northWest.query(range, found);
            southWest.query(range, found);
            southEast.query(range, found);
        }
        return found;
    }

    private void subdivide(){
        Vector2f scale = new Vector2f(this.bounds.scale.x / 2, this.bounds.scale.y / 2);
        this.northEast = new QuadTree(new Transform(this.bounds.getCenter(), scale));
        this.northWest = new QuadTree(new Transform(bounds.position.x, bounds.position.y + bounds.scale.y / 2, scale));
        this.southWest = new QuadTree(new Transform(bounds.position, scale));
        this.southEast = new QuadTree(new Transform(bounds.position.x + bounds.scale.x / 2, bounds.position.y, scale));

        this.isDivided = true;
    }



}


