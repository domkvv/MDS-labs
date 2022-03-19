import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 10;
    public int editType = 0;
    public double p = 0.3;

    public Board(int length, int height) {
        this.p = p;
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

        //first version
//        for (int x = 0; x < points.length; ++x) {
//            for (int y = 0; y < points[x].length; ++y) {
//                points[x][y].move();
//            }
//        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                randomizationAndAcceleration(x, y);
                slowingDown(x, y);
                moving(x, y);
            }
        }

        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
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

                    if (points[x][y].type == 1) {
                        g.setColor(new Color(0, 0, 0));
                    } else {
                        g.setColor(new Color(255, 255, 255));
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
            }
            this.repaint();
        }
    }

    public void randomizationAndAcceleration(int x, int y) {
        int flag = 0;
        if (points[x][y].velocity >= 1) {
            int random = ThreadLocalRandom.current().nextInt(0, (int) (1 / p));
            if (random == 0) {
                points[x][y].velocity -= 1;
                flag = 1;
            }
        }
        if (points[x][y].velocity < 5 && flag == 0) {
            points[x][y].velocity += 1;
        }
    }

    public void slowingDown(int x, int y) {
        int i = 1;
        while (i <= points[x][y].velocity) {
            if (points[(x + i) % points.length][y].type == 1) {
                points[x][y].velocity = i - 1;
                break;
            }
            i += 1;
        }
    }

    public void moving(int x, int y) {
        int v = points[x][y].velocity;
        int n = points.length;

        if (points[x][y].type == 1 && !points[x][y].moved && points[(x + v) % n][y].type == 0) {
            points[x][y].type = 0;
            points[(x + v) % n][y].type = 1;
            points[x][y].moved = true;
            points[(x + v) % n][y].moved = true;
        }
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
