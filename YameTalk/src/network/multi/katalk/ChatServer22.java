package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatServer22 extends JFrame{
	JPanel p_north;
	JTextField t_port;
	JButton bt_start;
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server;		// 접속자를 감지하기 위한 서버소켓
	Thread serverThread;
	
	//List<Vector> vecList = new ArrayList<Vector>();
	Vector<ServerChatThread> vec = new Vector<ServerChatThread>();		// 여러 명의 사용자 vector
	
	public ChatServer22() {
		p_north = new JPanel();
		t_port = new JTextField("9999", 8);
		bt_start = new JButton("Start");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt_start);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		setSize(400, 500);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
			}
		});
		
		serverThread = new Thread() {
			public void run() {
				serverStart();
			}
		};
	}
	
	public void serverStart() {
		int port = Integer.parseInt(t_port.getText());
		try {
			server = new ServerSocket(port);
			area.append("서버 시작\n");
			
			while(true) {
				Socket socket = server.accept();
				area.append("접속자 감지\n");
				ServerChatThread sct = new ServerChatThread(this, socket);
				sct.start();
				
				vec.add(sct);
				//vecList.add(vec);
				area.append("현재 참여 중인 사람 수 : "+vec.size()+"\n");
				//area.append("현재 채팅방의 수 : "+vecList.size()+"\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer22();
	}
}
