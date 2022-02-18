package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreen extends JFrame {

	public class ContentPane extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			Image background;
			try {
				background = ImageIO.read(new File("./rsc/background.png"));
				g2.drawImage(background, 0, 0, 800, 560, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private static final Image imageButtonLoadGame = null;

	ContentPane contentPane = new ContentPane();

	public TitleScreen() {
		setBounds(0, 0, 800, 560);
		setLocationRelativeTo(null);
		setTitle("Endocryne");
		setResizable(false);
		setContentPane(contentPane);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(null);
		drawNewGameButton();
		drawLoadGameButton();
		drawOptionsButton();
		repaint();
//		drawTitle();

	}

/*	public void drawTitle() {
		Image image3 = null;
		try {
			image3 = ImageIO.read(new File("./rsc/Title.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image2 image4 = new Image2(image3);
		image4.setBounds(getWidth() / 2 - image3.getWidth(null) / 2, image3.getHeight(null) / 2, image3.getWidth(null),
				image3.getHeight(null));
		contentPane.add(image4);
	}
*/
	public void drawNewGameButton() {

		try {
			Image imageButtonNewGame = ImageIO.read(new File("./rsc/buttonNewGame1.png"));
			Button btnNewGame = new Button(imageButtonNewGame);
			btnNewGame.setBounds(300, 170, imageButtonNewGame.getWidth(null), imageButtonNewGame.getHeight(null));
			btnNewGame.setBackground(null);
			btnNewGame.setForeground(null);
			btnNewGame.setOpaque(true);
			btnNewGame.addActionListener(e -> {
				Game game = new Game();
				dispose();
			});
			contentPane.add(btnNewGame);

			btnNewGame.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonNewGame1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Not working :(
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonNewGame2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonNewGame1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonNewGame2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonNewGame2.png"));
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

	public void drawLoadGameButton() {
		try {
			Image imageButtonLoadGame = ImageIO.read(new File("./rsc/buttonLoadGame1.png"));
			Button btnLoadGame = new Button(imageButtonLoadGame);
			System.out.println("" + imageButtonLoadGame.getHeight(null));
			btnLoadGame.setBounds(300, 230, imageButtonLoadGame.getWidth(null), imageButtonLoadGame.getHeight(null));
			btnLoadGame.setBackground(null);
			btnLoadGame.setForeground(null);
			btnLoadGame.setOpaque(true);
			btnLoadGame.addActionListener(e -> {
				LoadGame loadgame = new LoadGame();
				dispose();
			});
			contentPane.add(btnLoadGame);

			btnLoadGame.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnLoadGame.image = ImageIO.read(new File("./rsc/buttonLoadGame1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Not working :(
					try {
						btnLoadGame.image = ImageIO.read(new File("./rsc/buttonLoadGame2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnLoadGame.image = ImageIO.read(new File("./rsc/buttonLoadGame1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnLoadGame.image = ImageIO.read(new File("./rsc/buttonLoadGame2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnLoadGame.image = ImageIO.read(new File("./rsc/buttonLoadGame2.png"));
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

	public void drawOptionsButton() {
		try {
			Image imageButtonOptions = ImageIO.read(new File("./rsc/buttonOption1.png"));
			Button btnOptions = new Button(imageButtonOptions);
			btnOptions.setBounds(300, 290, imageButtonOptions.getWidth(null), imageButtonOptions.getHeight(null));
			btnOptions.setBackground(null);
			btnOptions.setForeground(null);
			btnOptions.setOpaque(true);
			btnOptions.addActionListener(e -> {
				Options options = new Options();
				dispose();
			});
			contentPane.add(btnOptions);

			btnOptions.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnOptions.image = ImageIO.read(new File("./rsc/buttonOption1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Not working :(
					try {
						btnOptions.image = ImageIO.read(new File("./rsc/buttonOption1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnOptions.image = ImageIO.read(new File("./rsc/buttonOption1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnOptions.image = ImageIO.read(new File("./rsc/buttonOptions2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnOptions.image = ImageIO.read(new File("./rsc/buttonOptions2.png"));
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
