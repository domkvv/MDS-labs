import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 25;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                if (y == 0 || y == 1 || y == 4 || y == 5) {
                    points[x][y].type = 5;
                }
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].next = points[(x + 1) % points.length][y];
            }
        }
    }

    public void iteration() {

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
            }
        }

        updateNeighbours();

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                acceleration(x, y);
                slowingDown(x, y);
                boolean speeded = false;
                boolean returned = false;
                if (y == 2) {
                    if (canReturn(x, y)) {
                        returned = true;
                    }
                }
                if (y == 3) {
                    if (canSpeedUp(x, y)) {
                        speeded = true;
                    }
                }
                if (!speeded && !returned) {
                    moving(x, y);
                }
            }
        }

        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
                if (y == 0 || y == 1 || y == 4 || y == 5) {
                    points[x][y].type = 5;
                }
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        if (points != null) {
            for (x = 0; x < points.length; ++x) {
                for (y = 0; y < points[x].length; ++y) {
                    float a = 1.0F;
                    switch (points[x][y].type) {
                        case 0:
                            g.setColor(new Color(255, 255, 255));
                            break;
                        case 1:
                            points[x][y].velocity = 3;
                            points[x][y].max = 3;
                            g.setColor(new Color(236, 176, 80, 255));
                            break;
                        case 2:
                            points[x][y].velocity = 5;
                            points[x][y].max = 5;
                            g.setColor(new Color(65, 118, 171, 255));
                            break;
                        case 3:
                            points[x][y].velocity = 7;
                            points[x][y].max = 7;
                            g.setColor(new Color(103, 20, 20, 255));
                            break;
                        case 5:
                            g.setColor(new Color(3, 31, 6, 203));
                            break;
                    }
                    // TODO
                    //g.setColor(new Color(R, G, B, 0.7f));

                    g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
                }
            }
        }


    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            } else {
                points[x][y].type = editType;
            }
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            } else {
                points[x][y].type = editType;
            }
            this.repaint();
        }
    }

    public void acceleration(int x, int y) {
        if (points[x][y].velocity < points[x][y].max) {
            points[x][y].velocity += 1;
        }
    }

    public void slowingDown(int x, int y) {
        int i = 1;
        while (i <= points[x][y].velocity) {
            if (points[(x + i) % points.length][y].type != 0) {
                points[x][y].velocity = i - 1;
                break;
            }
            i += 1;
        }
    }

    public void moving(int x, int y) {
        int v = points[x][y].velocity;
        int n = points.length;

        if (points[x][y].type != 0 && points[x][y].type != 5
            && !points[x][y].moved && points[(x + v) % n][y].type == 0) {
            points[(x + v) % n][y].type = points[x][y].type;
            points[x][y].type = 0;
            points[x][y].moved = true;
            points[(x + v) % n][y].moved = true;
            points[x][y].velocity = 0;
            points[(x + v) % n][y].velocity = v;
        }
    }

    public void updateNeighbours() {
        int n = points.length;
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].next_d = -1;
                points[x][y].prev_d = -1;

                boolean is_next = false;
                boolean is_prev = false;

                int i = 1;
                while (i < n) {
                    if (points[(x + i) % n][y].type != 0) {
                        is_next = true;
                        break;
                    }
                    i += 1;
                }

                int j = -1;
                while (j > (-1) * n) {
                    if (points[(x + j + n) % n][y].type != 0) {
                        is_prev = true;
                        break;
                    }
                    j -= 1;
                }

                if (is_next) {
                    points[x][y].next = points[(x + i) % n][y];
                    points[x][y].next_d = i - 1;
                }
                if (is_prev) {
                    points[x][y].prev = points[(x + j + n) % n][y];
                    points[x][y].prev_d = Math.abs(j + 1);
                }

            }
        }
    }

    public boolean canSpeedUp(int x, int y) {
        int v = points[x][y].velocity;
        int type = points[x][y].type;
        int prevR = points[x][y].prev_d;
        int prevL = points[x][y - 1].prev_d;
        int nextL = points[x][y].next_d;

        if (type != 0 && type != 5
            && !points[x][y].moved
            && points[x][y - 1].type == 0
            && (v < points[x][y].max)
            && (prevR == -1 || prevR >= points[x][y].prev.max)
            && (prevL == -1 || prevL >= points[x][y - 1].prev.max)
            && (nextL == -1 || nextL >= v)) {

            points[x][y - 1].type = type;
            points[x][y].type = 0;
            points[x][y].moved = true;
            points[x][y - 1].moved = true;
            points[x][y].velocity = 0;
            points[x][y - 1].velocity = v + 1;
            return true;
        }
        return false;
    }

    public boolean canReturn(int x, int y) {
        int v = points[x][y].velocity;
        int type = points[x][y].type;
        int prevR = points[x][y + 1].prev_d;
        int prevL = points[x][y].prev_d;
        int nextR = points[x][y + 1].next_d;

        if (type != 0 && type != 5
            && !points[x][y].moved
            && points[x][y + 1].type == 0
            && (prevR == -1 || prevR >= points[x][y + 1].prev.max)
            && (prevL != -1 && prevL <= points[x][y].prev.max)
            && (nextR == -1 || nextR >= v)) {

            points[x][y + 1].type = type;
            points[x][y].type = 0;
            points[x][y].moved = true;
            points[x][y + 1].moved = true;
            points[x][y].velocity = 0;
            points[x][y + 1].velocity = v;
            return true;
        }
        return false;
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
