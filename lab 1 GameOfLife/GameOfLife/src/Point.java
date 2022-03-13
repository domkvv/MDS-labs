import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Point {
    private ArrayList<Point> neighbors;
    private int currentState;
    private int nextState;
    private int numStates = 6;
    private int mode;

    public Point() {
        currentState = 0;
        nextState = 0;
        neighbors = new ArrayList<Point>();
    }

    public void clicked() {
        currentState = (++currentState) % numStates;
    }

    public int getState() {
        return currentState;
    }

    public void setState(int s) {
        currentState = s;
    }

    public void calculateNewState() {
        //TODO: insert logic which updates according to currentState and
        //number of active neighbors
        int alive = this.aliveNeighbours();
        if (this.mode == 1) {
            if (this.currentState == 0) {
                if (alive == 3) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            } else {
                if (alive == 2 || alive == 3) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            }

        } else if (this.mode == 2) {
            if (this.currentState == 0) {
                if (alive == 4 || alive == 5 || alive == 6 || alive == 7 || alive == 8) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            } else {
                if (alive == 2 || alive == 3 || alive == 4 || alive == 5) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            }

        } else if (this.mode == 3) {
            if (this.currentState == 0) {
                if (alive == 3) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            } else {
                if (alive == 4 || alive == 5 || alive == 6 || alive == 7 || alive == 8) {
                    this.nextState = 1;
                } else {
                    this.nextState = 0;
                }
            }

        } else if (this.mode == 4) {
            if (this.currentState > 0) {
                this.nextState = this.currentState - 1;
            } else if (this.currentState == 0) {
                if (!this.neighbors.isEmpty() && this.neighbors.get(0).currentState > 0) {
                    this.nextState = 6;
                }

            }
        }

    }

    public void changeState() {
        currentState = nextState;
    }

    public void addNeighbor(Point nei) {
        neighbors.add(nei);
    }

    //TODO: write method counting all active neighbors of THIS point
    public int aliveNeighbours() {
        int alive = 0;
        for (Point i : this.neighbors) {
            if (i.currentState == 1) {
                alive += 1;
            }
        }
        return alive;
    }

    public void drop() {
        int random = ThreadLocalRandom.current().nextInt(20);
        if (random == 1) {
            this.nextState = 6;
        }
    }

    public void setPointMode(int mode) {
        this.mode = mode;
    }

}
