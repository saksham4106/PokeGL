package tiles;

public class TilePosition {
    public int x, y, z;

    public TilePosition(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "{" +
                "" + x +
                ", " + y +
                ", " + z + '}';
    }
}
