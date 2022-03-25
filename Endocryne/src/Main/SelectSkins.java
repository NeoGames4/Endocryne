package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SelectSkins extends JFrame {

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

	public SelectSkins() {
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
		File welten = new File(Launcher.mainDirectoryPath + "/skins");
		Box box = Box.createVerticalBox();
		for (File f : welten.listFiles()) {
			if (f.getName().endsWith(".png")) {

				String name = f.getName();
				// System.out.println(name.substring(0, name.length()-5));
				JButton button = new JButton("" + name.substring(0, name.length() - 4));
				button.setMinimumSize(new Dimension(getWidth() / 2, 24));
				box.add(button);
				button.addActionListener(e -> {
					try {
						Options.skin = ImageIO.read(new File(Launcher.mainDirectoryPath + "/skins/" + name));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
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
				Options otions = new Options();
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
						btnNewGame.image = ImageIO.read(new File("./rsc/buttonBack2.png"));
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
		
		Image image2 = null;
		try {
			image2 = ImageIO.read(new File("./rsc/buttonUploadSkin1.png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

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

			
			  @Override public void actionPerformed(ActionEvent e) { JFileChooser chooser =
			           new JFileChooser(); FileNameExtensionFilter filter = new
			           FileNameExtensionFilter("*.png", "png");
			            chooser.setFileFilter(filter);
			           int rueckgabeWert = chooser.showOpenDialog(null);
			  
			            if (rueckgabeWert == JFileChooser.APPROVE_OPTION) { 
			            System.out.println("Die zu öffnende Datei ist:" + chooser.getSelectedFile().getName()); 
			            try { Options.skin =ImageIO.read(chooser.getSelectedFile()); Options.image4.image = Options.skin; Options.image4.repaint(); } 
			            catch (IOException e1) { 
			             // TODO Auto-generated
			             e1.printStackTrace(); }
			            try {
							ImageIO.write(ImageIO.read(new File("./rsc/endocryneSkinTemplate.png")), "png",
									new File(Launcher.mainDirectoryPath + "skins" + "/endocryneSkinTemplate.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			 
			  
			  }
		
		}
		}

		);
		btnUploadSkin.setBounds(getWidth() / 2 - image2.getWidth(null) / 2,
				20 + 10 * 2 + image2.getHeight(null) * 2, image2.getWidth(null), image2.getHeight(null));
		btnUploadSkin.setBackground(null);
		btnUploadSkin.setForeground(Color.white);
		btnUploadSkin.setOpaque(true);
		btnUploadSkin.addActionListener(e -> {
		});
		contentPane.add(btnUploadSkin);
		repaint();

	}

}
