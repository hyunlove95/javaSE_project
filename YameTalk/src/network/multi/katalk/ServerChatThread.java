package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerChatThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatServer22 chatServer22;
	boolean flag = true;			// listen을 정지시켜 쓰레드를 끝낼 논리값
	
	public ServerChatThread(ChatServer22 chatServer22, Socket socket) {
		this.chatServer22 = chatServer22;
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		String msg = null;
		
		try {
			msg = buffr.readLine();
			
			chatServer22.area.append(msg+"\n");
			
			//broadCasting : 다수의 사용자에 동시에 메세지를 보낸 것
			for(int i=0;i<chatServer22.vec.size();i++) {
					ServerChatThread sct = chatServer22.vec.get(i);	// 0번째부터 보내기
					sct.sendMsg(msg);
			}
			
		} catch (IOException e) {
			//e.printStackTrace();
			flag = false;
			chatServer22.vec.remove(this);	// 쓰레드를 끝내고 제거하기
			chatServer22.area.append("현재 참여 중인 사람 : "+chatServer22.vec.size()+"\n");
		}
	}
	
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(flag) {
			listen();
		}
	}
}
