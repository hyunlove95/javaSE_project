package network.multi.katalk;

import java.awt.Dimension;

import javax.swing.JTextPane;

public class EmotiWindow extends JTextPane{
	ChatPage22 chatPage22;
	
	public EmotiWindow(ChatPage22 chatPage22) {
		this.chatPage22 = chatPage22;
		
		this.setPreferredSize(new Dimension(500, 180));
	}
}
