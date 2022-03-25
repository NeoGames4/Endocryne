package Main;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits extends JFrame {
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();
    Clip clip;

    public Credits() {
        try {
    		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    		
    		addWindowListener(new WindowAdapter() {
    			@Override
    			public void windowClosing(WindowEvent e) {
    				if(clip != null) clip.stop();
    				dispose();
    				TitleScreen titleScreen = new TitleScreen();
    				titleScreen.setVisible(true);
    			}
    		});
    		
            contentPane = (JPanel) getContentPane();
            contentPane.setLayout(new BorderLayout());
            setSize(new Dimension(768, 432));
            Image ii = new ImageIcon(
                    "./rsc/credits.gif").getImage().getScaledInstance(768, 432, Image.SCALE_DEFAULT);
            imageLabel.setIcon(new ImageIcon(ii));
            contentPane.add(imageLabel, java.awt.BorderLayout.CENTER);
            try {
    			File soundFile = new File("./rsc/creditssong.wav");
    			clip = SoundManager.play(soundFile, Clip.LOOP_CONTINUOUSLY);
    		} catch(Exception e) { e.printStackTrace(); }
            
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
}

