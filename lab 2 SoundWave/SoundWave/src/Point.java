public class Point {

    public Point nNeighbor;
    public Point wNeighbor;
    public Point eNeighbor;
    public Point sNeighbor;
    public float nVel;
    public float eVel;
    public float wVel;
    public float sVel;
    public float pressure;
    public static Integer[] types = {0, 1, 2};
    public int type;
    public int sinInput = 0;

    public Point() {
        clear();
        type = 0;
    }

    public void clicked() {
        pressure = 1;
    }

    public void clear() {
        pressure = 0;
        nVel = 0;
        eVel = 0;
        sVel = 0;
        wVel = 0;
        type = 0;
    }

    public void updateVelocity() {
        // TODO: velocity update
        if (type == 0) {
            nVel = nVel - (nNeighbor.getPressure() - getPressure());
            eVel = eVel - (eNeighbor.getPressure() - getPressure());
            sVel = sVel - (sNeighbor.getPressure() - getPressure());
            wVel = wVel - (wNeighbor.getPressure() - getPressure());
        }
    }

    public void updatePresure() {
        // TODO: pressure update
        if (type == 0) {
            pressure = (float) (pressure - 0.5 * (nVel + eVel + sVel + wVel));
        } else if (type == 2) {
            double radians = Math.toRadians(sinInput);
            pressure = (float) (Math.sin(radians));
        }
    }

    public float getPressure() {
        return pressure;
    }
}