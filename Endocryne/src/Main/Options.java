package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options extends JFrame {

	static Image2 image4;
	public static Image skin;
	public static int difficulty = 2;

	public class ContentPane extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
			Image background;
			try {
				background = ImageIO.read(new File("./rsc/background.jpg"));
				g2.drawImage(background, 0, 0, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	ContentPane contentPane = new ContentPane();

	public Options() {
		try {
			setVisible(true);
			setBounds(0, 0, 800, 560);
			setLocationRelativeTo(null);
			setTitle("Endocryne");
			setResizable(false);
			setContentPane(contentPane);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			contentPane.setLayout(null);
			int height = 0;
			if (skin == null) {
				skin = ImageIO.read(new File("./rsc/player.png"));
			}

			Image image = ImageIO.read(new File("./rsc/buttonBack1.png"));

			Button btnBack = new Button(image);
			btnBack.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnBack.image = ImageIO.read(new File("./rsc/buttonBack1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					try {
						btnBack.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnBack.image = ImageIO.read(new File("./rsc/buttonBack1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnBack.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnBack.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btnBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TitleScreen alpha = new TitleScreen();
					alpha.setVisible(true);
					dispose();
				}

			});

			btnBack.setBounds(getWidth() / 2 - image.getWidth(null) / 2, 20, image.getWidth(null),
					image.getHeight(null));
			btnBack.setBackground(null);
			btnBack.setForeground(Color.white);
			btnBack.setOpaque(true);
			contentPane.add(btnBack);
			repaint();

			Image image1 = ImageIO.read(new File("./rsc/buttonResetskin1.png"));

			Button btnResetSkin = new Button(image1);
			btnResetSkin.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnResetSkin.image = ImageIO.read(new File("./rsc/buttonResetskin1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					try {
						btnResetSkin.image = ImageIO.read(new File("./rsc/buttonResetskin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnResetSkin.image = ImageIO.read(new File("./rsc/buttonResetskin1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnResetSkin.image = ImageIO.read(new File("./rsc/buttonResetskin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnResetSkin.image = ImageIO.read(new File("./rsc/buttonResetskin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btnResetSkin.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						skin = ImageIO.read(new File("./rsc/mob.png"));	
						dispose();
						Options options = new Options();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			});
			btnResetSkin.setBounds(getWidth() / 2 - image1.getWidth(null) / 2, 20 + 10 * 1 + image1.getHeight(null),
					image1.getWidth(null), image1.getHeight(null));
			btnResetSkin.setBackground(null);
			btnResetSkin.setForeground(Color.white);
			btnResetSkin.setOpaque(true);
			btnResetSkin.addActionListener(e -> {
			});
			contentPane.add(btnResetSkin);
			repaint();

			Image image2 = ImageIO.read(new File("./rsc/buttonUploadSkin1.png"));

			Button btnUploadSkin = new Button(image2);
			btnUploadSkin.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						btnUploadSkin.image = ImageIO.read(new File("./rsc/buttonUploadSkin1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// Not working :(
					try {
						btnUploadSkin.image = ImageIO.read(new File("./rsc/buttonUploadSkin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					try {
						btnUploadSkin.image = ImageIO.read(new File("./rsc/buttonUploadSkin1.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {
						btnUploadSkin.image = ImageIO.read(new File("./rsc/buttonUploadSkin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						btnUploadSkin.image = ImageIO.read(new File("./rsc/buttonUploadSkin2.png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			btnUploadSkin.addActionListener(new ActionListener() {

				/**
				 * @Override public void actionPerformed(ActionEvent e) { JFileChooser chooser =
				 *           new JFileChooser(); FileNameExtensionFilter filter = new
				 *           FileNameExtensionFilter("*.png", "png");
				 *           chooser.setFileFilter(filter); // Dialog zum Oeffnen von Dateien
				 *           anzeigen int rueckgabeWert = chooser.showOpenDialog(null);
				 * 
				 *           if (rueckgabeWert == JFileChooser.APPROVE_OPTION) { // Ausgabe der
				 *           ausgewaehlten Datei System.out.println("Die zu öffnende Datei ist:
				 *           " + chooser.getSelectedFile().getName()); try { skin =
				 *           ImageIO.read(chooser.getSelectedFile()); image4.image = skin;
				 *           image4.repaint(); } catch (IOException e1) { // TODO Auto-generated
				 *           catch block e1.printStackTrace(); }
				 */
				public void actionPerformed(ActionEvent e) {

					SelectSkins selectskin = new SelectSkins();
					dispose();
				}
			}

			);
			btnUploadSkin.setBounds(getWidth() / 2 - image2.getWidth(null) / 2,
					20 + 10 * 2 + image2.getHeight(null) * 2, image2.getWidth(null), image2.getHeight(null));
			height = 10 * 2 + image2.getHeight(null) * 2;
			btnUploadSkin.setBackground(null);
			btnUploadSkin.setForeground(Color.white);
			btnUploadSkin.setOpaque(true);
			btnUploadSkin.addActionListener(e -> {
			});
			contentPane.add(btnUploadSkin);
			repaint();

			Image image3 = skin;
			image4 = new Image2(image3);
			image4.setBounds(getWidth() / 2 - image3.getWidth(null) / 2, getHeight() / 2 - skin.getHeight(null) / 2,
					image3.getWidth(null), image3.getHeight(null));
			height = height + image.getHeight(null);
			contentPane.add(image4);
			repaint();

			JCheckBox debug = new JCheckBox("Debug");
			debug.setSelected(Launcher.DEBUG);
			debug.setBounds(-35 + getWidth() / 2, height + 250, 70, 16);
			debug.setBackground(null);
			debug.setOpaque(false);
			debug.setForeground(new Color(255, 255, 255));
			contentPane.add(debug);
			repaint();
			debug.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {// checkbox has been selected
						// do something...
						Launcher.DEBUG = true;
					} else {// checkbox has been deselected
						// do something...
						Launcher.DEBUG = false;
					}
					;
				}
			});

			// JSlider-Objekt wird erzeugt
			JSlider diffJS = new JSlider();
			diffJS.setBounds(-150 + getWidth() / 2, height + 265, 300, 100);
			diffJS.setOpaque(false);
			diffJS.setForeground(new Color(255, 255, 255));
			contentPane.add(diffJS);

			// Mindestwert wird gesetzt
			diffJS.setMinimum(1);
			// Maximalwert wird gesetzt
			diffJS.setMaximum(3);

			// Die Abstände zwischen den
			// Teilmarkierungen werden festgelegt
			diffJS.setMajorTickSpacing(1);
			diffJS.setMinorTickSpacing(1);

			// Standardmarkierungen werden erzeugt
			diffJS.createStandardLabels(1);

			// Zeichnen der Markierungen wird aktiviert
			diffJS.setPaintTicks(true);

			// Zeichnen der Labels wird aktiviert
			diffJS.setPaintLabels(true);

			// Schiebebalken wird auf den Wert 9 gesetzt
			diffJS.setValue(difficulty);

			diffJS.setVisible(true);
			
			diffJS.addChangeListener(new ChangeListener() {	
				@Override
				public void stateChanged(ChangeEvent e) {
					if (!diffJS.getValueIsAdjusting()) {
			             difficulty = (int)diffJS.getValue();
			        }
				}
			});
			repaint();
			
			repaint();

			JLabel difficulty = new JLabel("Difficulty");
			difficulty.setBounds(220 + diffJS.getWidth() / 2, height + 265, 100, 20);
			difficulty.setBackground(null);
			difficulty.setForeground(new Color(255, 255, 255));
			contentPane.add(difficulty);
			repaint();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Options otions = new Options();
		otions.repaint();
	}

}
