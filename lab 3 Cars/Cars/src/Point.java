public class Point {
    public int type;
    public Point next;
    public boolean moved;
    public int velocity;

    public void move() {
        if (this.type == 1 && !this.moved && this.next.type == 0) {
            this.type = 0;
            this.next.type = 1;
            this.moved = true;
            this.next.moved = true;
        }
    }

    public void clicked() {
        this.type = 1;
    }

    public void clear() {
        this.type = 0;
    }

}

