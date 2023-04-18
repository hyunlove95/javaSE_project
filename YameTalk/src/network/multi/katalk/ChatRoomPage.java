package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ChatRoomPage extends Page22{
	List<JPanel> roomList = new ArrayList<JPanel>();
	JLabel la_createRoom;
	JScrollPane scroll;
	
	public ChatRoomPage(ClientMain22 clientMain22) {
		super(clientMain22);
	
		scroll = new JScrollPane(this);
		try {
			URL url = this.getClass().getClassLoader().getResource("res/image/defaultRoom.png");
			Image img = ImageIO.read(url);
			img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(img);
			la_createRoom = new JLabel(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		la_createRoom.setBounds(450, 500, 50, 50);
		add(la_createRoom);
		
		setLayout(null);
		
		la_createRoom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				createRoom();
				updateUI();
			}
		});
	}
	
	public void createRoom() {
		JPanel room = new JPanel();
		JLabel title = new JLabel("채팅방 "+(roomList.size()+1));		// size()가 처음에는 0이므로
		
		room.setLayout(null);
		room.setBounds(0, roomList.size()*90, 500, 80);
		room.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		room.setBackground(Color.YELLOW);
		title.setFont(new Font("dotum", Font.BOLD, 40));
		title.setBounds(80, 20, 200, 50);
		
		room.add(title);
		roomList.add(room);
		add(room);
		
		room.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				enterRoom();
			}
		});
	}

	public void enterRoom() {
		clientMain22.showHide(clientMain22.CHATPAGE);
	}
}
