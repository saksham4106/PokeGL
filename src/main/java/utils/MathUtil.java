package utils;

import game.Camera;
import game.Transform;
import game.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class MathUtil {

    public static Transform normalizeTransform(Transform transform){
        Vector2f position = normalizePosition(transform.position);
        Vector2f size = normalizePosition(transform.scale);
        return new Transform(position.x - 1, position.y - 1,
                size.x, size.y);
    }

    public static Vector2f normalizePosition(Vector2f position){
        float halfWidth = Window.width / 2f;
        float halfHeight = Window.height / 2f;
        return new Vector2f((position.x / halfWidth), (position.y / halfHeight));
    }

    public static Vector2f NdcToScreen(Vector2f position){
        float halfWidth = Window.width / 2f;
        float halfHeight = Window.height / 2f;
        return new Vector2f(position.x * halfWidth, position.y * halfHeight);
    }

    public static Vector2f screenToWorld(Vector2f worldCoordinates){
//        float currentX = worldCoordinates.x;
//        currentX = (2.0f * (currentX / Window.width)) - 1.0f;
//        float currentY = worldCoordinates.y;
//        currentY = (2.0f * (1.0f - (currentY / Window.height))) - 1;
        Vector2f position = normalizePosition(worldCoordinates);

        Camera camera = Window.getCurrentScene().camera;
        Vector4f tmp = new Vector4f(position.x, position.y, 0, 1);
        Matrix4f inverseView = new Matrix4f(camera.getInverseView());
        Matrix4f inverseProjection = new Matrix4f(camera.getInverseProjection());
        tmp.mul(inverseView.mul(inverseProjection));
        return new Vector2f(tmp.x, tmp.y);
    }
}
