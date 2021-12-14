package Main;

import java.awt.Color;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TitleScreen extends JFrame {
	
	JPanel contentPane = new JPanel();
	
	public TitleScreen() {
		setBounds(0, 0, 800, 560);
		setLocationRelativeTo(null);
		setTitle("Endocryne");
		setResizable(false);
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(null);
		
		Button btnNewGame = new Button("New Game");
		btnNewGame.setBounds(10, 10, 300, 20);
		btnNewGame.setBackground(new Color(20, 20, 20));
		btnNewGame.setForeground(Color.white);
		btnNewGame.setOpaque(true);
		btnNewGame.addActionListener(e -> {
		});
		contentPane.add(btnNewGame);
	}

}
