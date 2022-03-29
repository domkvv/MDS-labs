public class Point {
    public int type;
    public Point next;
    public Point prev;
    public boolean moved;
    public int velocity;
    public int max;
    public static Integer[] types = {0, 1, 2, 3, 5};
    public int next_d = -1;
    public int prev_d = -1;

    public void move() {
        if (this.type == 1 && !this.moved && this.next.type == 0) {
            this.type = 0;
            this.next.type = 1;
            this.moved = true;
            this.next.moved = true;
        }
    }

    public void clicked() {
        this.type = 0;
    }

    public void clear() {
        this.type = 0;
    }

}

