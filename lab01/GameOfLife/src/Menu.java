import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JRadioButton game = new JRadioButton("Game Of Life - classic", true);
    private JRadioButton cities = new JRadioButton("Game Of Life - cities");
    private JRadioButton coral = new JRadioButton("Game Of Life - coral");
    private JRadioButton rain = new JRadioButton("Rain");
    private JFrame frame;

    public Menu(JFrame jf) {
        frame = jf;
    }

    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JLabel text = new JLabel("Select which simulation would you like to see:");
        text.setAlignmentX(CENTER_ALIGNMENT);
        ButtonGroup selection = new ButtonGroup();
        JButton go = new JButton("Go");

        go.setActionCommand("Go");
        go.addActionListener(this);

        game.setAlignmentX(CENTER_ALIGNMENT);
        cities.setAlignmentX(CENTER_ALIGNMENT);
        coral.setAlignmentX(CENTER_ALIGNMENT);
        rain.setAlignmentX(CENTER_ALIGNMENT);
        go.setAlignmentX(CENTER_ALIGNMENT);

        selection.add(game);
        selection.add(cities);
        selection.add(coral);
        selection.add(rain);

        JPanel selectionPane = new JPanel();
        selectionPane.add(text);
        selectionPane.add(game);
        selectionPane.add(cities);
        selectionPane.add(coral);
        selectionPane.add(rain);
        selectionPane.add(go);
        selectionPane.setLayout(new BoxLayout(selectionPane, BoxLayout.PAGE_AXIS));
        container.add(selectionPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Go")) {
            JFrame guiFrame = new JFrame();
            int simulationMode = 1;
            if (cities.isSelected()) {
                simulationMode = 2;
            } else if (coral.isSelected()) {
                simulationMode = 3;
            } else if (rain.isSelected()) {
                simulationMode = 4;
            }
            GUI gof = new GUI(guiFrame, simulationMode);
            gof.initialize(guiFrame.getContentPane());
            guiFrame.setSize(800, 600);
            guiFrame.setVisible(true);
        }

    }
}




