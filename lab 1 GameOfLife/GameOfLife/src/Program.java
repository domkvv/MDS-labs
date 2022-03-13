import javax.swing.JFrame;


public class Program extends JFrame {

    private static final long serialVersionUID = 1L;
    private Menu menu;

    public Program() {
        setTitle("Simple simulations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new Menu(this);
        menu.initialize(this.getContentPane());
        this.setSize(800, 600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Program();
    }

}