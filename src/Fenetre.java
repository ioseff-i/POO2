import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
public class Fenetre extends JFrame {
        private Placement drawing = new Placement();
	private JPanel pan =new JPanel();
	private String path="";
	private Color menu_color;
	private Color txt_color;
	public static void main(String[] args){
		Fenetre fen = new Fenetre();
	}       
	private void ChooseButtonActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(path);
    	fileChooser.setFileFilter(new FileNameExtensionFilter("SVG format",
        "svg", "SVG"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileChooser.getSelectedFile();
			path=selectedFile.getPath();
		}
	}
	public Fenetre(){
		this.setTitle("PROJECT");
		this.setSize(1300, 800);
		this.setResizable(false);
		JButton bouton1 = new JButton("Select SVG PANEL");
		bouton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				ChooseButtonActionPerformed(event);
				drawing.setFic(path,false);
			}
		});

		JButton button = new JButton("Exit");
        add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fenetre.this.processWindowEvent(
                        new WindowEvent(
                                Fenetre.this, WindowEvent.WINDOW_CLOSING));
            }
        });

                JPanel cell1 = new JPanel();
		drawing.setPreferredSize(new Dimension(1000, 700));
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(1000, 500));
		GridBagConstraints gbc = new GridBagConstraints();
		content.setLayout(new GridBagLayout());
		cell1.setLayout(new GridBagLayout());
		content.add(cell1, gbc);
		content.add(drawing, gbc);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 10;
		cell1.add(bouton1, gbc);
		gbc.gridy = 1;
		cell1.add( Box.createVerticalGlue(),gbc);
		this.setContentPane(content);
		this.setVisible(true);
                this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}