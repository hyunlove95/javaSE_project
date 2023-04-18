package network.multi.katalk;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMain22 extends JFrame{
	JPanel container;
	Page22[] pages = new Page22[4];
	public static final int LOGINPAGE = 0;
	public static final int JOINPAGE = 1;
	public static final int CHATPAGE = 2;
	public static final int CHATROOMPAGE = 3;
	
	// Page에 ClientMain을 매개변수로 넣었기에 모든 Page에서 참조 가능함
	ChatMember22 chatMember22;
	
	public ClientMain22() {
		container = new JPanel();
		pages[0] = new LoginPage22(this);
		pages[1] = new JoinPage22(this);
		pages[2] = new ChatPage22(this);
		pages[3] = new ChatRoomPage(this);
		
		add(container);
		
		for(int i=0; i<pages.length;i++) {
			container.add(pages[i]);
		}
		
		showHide(LOGINPAGE);
		
		setSize(520,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void showHide(int n) {
		for(int i=0; i<pages.length;i++) {
			if(i==n) {
				pages[i].setVisible(true);	
			}
			else {
				pages[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ClientMain22();
	}
}
