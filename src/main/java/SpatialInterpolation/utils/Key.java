package SpatialInterpolation.utils;

import java.util.Map;

public class Key {

    private int x;
    private int y;

    public Key(int firstValue, int secondValue) {
        this.x = firstValue;
        this.y = secondValue;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
