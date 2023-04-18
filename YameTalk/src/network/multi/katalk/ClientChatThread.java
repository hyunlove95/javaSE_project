package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientChatThread extends Thread{
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatPage22 chatPage22;
	Socket socket;
	boolean flag = true;		// thread를 끝낼지 정할 논리값
	
	public ClientChatThread(ChatPage22 chatPage22, Socket socket) {
		this.chatPage22 = chatPage22;
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 듣기
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine();	// 문자열인 json
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(msg);		// 파싱
			String data = (String)jsonObject.get("chat");
			String icon = (String) jsonObject.get("icon");
			if(data.length()>0) {
				chatPage22.addString(jsonObject);				
			}
			else if(!icon.equals("null")) {
				chatPage22.addIcon(jsonObject);	
			}
			else {
				chatPage22.addString(jsonObject);
			}
			
		} catch (IOException e) {
			flag = false;
			//e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// 쓰기
	public void sendChat(String chat) {
		try {
			buffw.write(chat+"\n");
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
