package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LoadGame extends JFrame {

	public class ContentPane extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			Image background;
			if (this.getBackground() == null) {

			} else {
				try {
					background = ImageIO.read(new File("./rsc/background.jpg"));
					g2.drawImage(background, 0, 0, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private static final Image imageButtonLoadGame = null;

	ContentPane contentPane = new ContentPane();

	public LoadGame() {
		setBounds(0, 0, 800, 560);
		setLocationRelativeTo(null);
		setTitle("Endocryne");
		setResizable(false);
		setContentPane(contentPane);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(null);
		drawScrollPanel();
		drawBackButton();
		repaint();
	}

	public void drawScrollPanel() {
		File welten = new File(Launcher.mainDirectoryPath + "/worlds");
		Box box = Box.createVerticalBox();
		for (File f : welten.listFiles()) {
			if (f.getName().endsWith(".json")) {

				String name = f.getName();
				// System.out.println(name.substring(0, name.length()-5));
				JButton button = new JButton("" + name.substring(0, name.length() - 5));
				button.setMinimumSize(new Dimension(getWidth() / 2, 24));
				box.add(button);
			}

		}
		JScrollPane scrollPane = new JScrollPane(box);
		scrollPane.setBounds(200, 90, getWidth() / 2, getHeight() - 150);
		contentPane.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setOpaque(true);
		scrollPane.setBackground(null);
		setVisible(true);
		repaint();
	}

	public void drawBackButton() {

		try {
			Image imageBack = ImageIO.read(new File("./rsc/buttonBack1.png"));
			Button btnNewGame = new Button(imageBack);
			btnNewGame.setBounds(getWidth() / 2 - imageBack.getWidth(null) / 2, 20, imageBack.getWidth(null),
					imageBack.getHeight(null));
			btnNewGame.setBackground(null);
			btnNewGame.setForeground(null);
			btnNewGame.setOpaque(true);
			btnNewGame.addActionListener(e -> {
				TitleScreen titlescreen = new TitleScreen();
				dispose();
			});
			contentPane.add(btnNewGame);

			btnNewGame.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonBack1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Not working :(
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/butttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonBack1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
