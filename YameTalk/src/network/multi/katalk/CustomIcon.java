package network.multi.katalk;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CustomIcon extends JLabel{
	String path;	// 이미지 경로명
	ChatPage22 chatPage22;
	
	public CustomIcon(ChatPage22 chatPage22, String path) {
		this.chatPage22 = chatPage22;
		this.path = path;
		URL url = this.getClass().getClassLoader().getResource(path);
		try {
			Image img = ImageIO.read(url);
			img = img.getScaledInstance(48, 50, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(img);
			this.setIcon(icon);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chatPage22.setBorderIcon(CustomIcon.this);
			}
		});
	}
}
