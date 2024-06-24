package collision;

import game.Transform;

public class CollisionDetection {

    public static boolean pointInBox(float pointX, float pointY, float boxX, float boxY, float boxWidth, float boxHeight) {
        return (pointX > boxX && pointX < boxX + boxWidth) &&
                (pointY > boxY && pointY < boxY + boxHeight);
    }

    public static boolean pointInBox(Point point, Transform transform){
        return pointInBox(point.getX(), point.getY(), transform.position.x, transform.position.y,
                transform.scale.x, transform.scale.y);
    }

    public static boolean boxInBox(Transform box1, Transform box2) {
        return boxInBox(box1.position.x, box1.position.y, box1.scale.x, box1.scale.y,
                box2.position.x, box2.position.y, box2.scale.x, box2.scale.y);
    }

    public static boolean boxInBox(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 & y1 < y2 + h2 && h1 + y1 > y2;
    }
}
