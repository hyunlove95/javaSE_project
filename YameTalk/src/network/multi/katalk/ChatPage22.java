package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.json.simple.JSONObject;

public class ChatPage22 extends Page22 {
	JTextPane content;
	JScrollPane scroll_content;
	JLabel la_emoti; // 이모티콘 창을 열 라벨
	JLabel la_file; // 파일찾기 라벨
	JPanel p_south;
	JTextField t_input;
	JButton bt_send;
	JButton bt_exit;
	JPanel p_selectList;		// 어떤 아이콘을 사용할지 선택하는 곳
	JLabel[] la_icon = new JLabel[4];
	boolean flag;				// 아이콘 창을 보이게 할지 결정하는 논리값
	
	JPanel iconContainer;
	EmotiWindow[] detail = new EmotiWindow[4]; // 아이콘 창
	public static final int EMOTINEWSPAPER = 0;
	public static final int EMOTISTARTGIF= 1;
	public static final int EMOTITKZ = 2;
	public static final int EMOTIYYH= 3;
	//JTextPane detail; // 아이콘 창

	ClientChatThread cct;
	JFileChooser chooser; // 파일 탐색기
	Document doc_content; // 대화창 스타일
	Document[] doc_detail = new StyledDocument[4];			// 아이콘 창 스타일
	File file; // 파일 탐색기에서 선택한 파일
	List<CustomIcon> iconList = new ArrayList<CustomIcon>();
	String imgName = null; // 아이콘들의 경로명

	public ChatPage22(ClientMain22 clientMain22) {
		super(clientMain22);

		content = new JTextPane();
		content.setEditable(false); // 대화창에는 간섭X
		scroll_content = new JScrollPane(content);
		doc_content = content.getStyledDocument();

		la_emoti = new JLabel(iconScale("res/image/defaultIcon.png"));
		la_file = new JLabel(iconScale("res/image/defaultFile.png"));
		p_south = new JPanel();
		t_input = new JTextField();
		bt_send = new JButton("전송");
		bt_exit = new JButton("나가기");
		
		p_selectList = new JPanel();
		la_icon[0] = new JLabel(iconScale("res/image/newspaper/1.png"));
		la_icon[1] = new JLabel(iconScale("res/image/startGif/1.gif"));
		la_icon[2] = new JLabel(iconScale("res/image/tkz/1.png"));
		la_icon[3] = new JLabel(iconScale("res/image/yyh/1.png"));
		
		iconContainer = new JPanel();
		detail[0] = new EmotiNewspaper(this);
		detail[1] = new EmotiStartGif(this);
		detail[2] = new EmotiTkz(this);
		detail[3] = new EmotiYyh(this);

		content.setPreferredSize(new Dimension(500, 300));
		scroll_content.setPreferredSize(new Dimension(480,250));
		t_input.setPreferredSize(new Dimension(290, 35));
		p_selectList.setPreferredSize(new Dimension(500, 30));
		p_selectList.setLayout(new FlowLayout(FlowLayout.CENTER));		// 패널에 붙을 라벨들을 중앙정렬

		add(scroll_content);
		add(p_south, BorderLayout.SOUTH);
		p_south.add(la_file);
		p_south.add(t_input);
		p_south.add(la_emoti);
		p_south.add(bt_send);
		p_south.add(bt_exit);
		
		add(p_selectList);

		add(iconContainer);
		for(int i=0;i<detail.length;i++) {
			doc_detail[i] = detail[i].getStyledDocument();
			
			detail[i].setEditable(false); // 아이콘 창에는 간섭X
			detail[i].getScrollableTracksViewportWidth();
			detail[i].getScrollableTracksViewportHeight();
			iconContainer.add(detail[i]);
		}
		
		setBackground(Color.YELLOW);
		
		((EmotiNewspaper) detail[EMOTINEWSPAPER]).openPage();
		((EmotiStartGif) detail[EMOTISTARTGIF]).openPage();
		((EmotiTkz) detail[EMOTITKZ]).openPage();
		((EmotiYyh) detail[EMOTIYYH]).openPage();
		
		showHideIcon(4);	// 처음에는 이모티콘 창이 안 보이게
		
		bt_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
			}
		});

		la_emoti.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				openIcon();
			}
		});

		la_file.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectFile();
			}
		});
		
		bt_exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int announce = JOptionPane.showConfirmDialog(clientMain22, "나가시겠습니까?");
				if(announce == JOptionPane.OK_OPTION) {
					clientMain22.showHide(clientMain22.CHATROOMPAGE);
				}
			}
		});
	}
	
	// 아이콘 창 열기
	public void openIcon() {
		flag = !flag;
		System.out.println(flag);
		if(flag) {
			for (int i = 0; i < la_icon.length; i++) {
				p_selectList.add(la_icon[i]);
				updateUI();
				setIndex(i);
			}			
		}else {
			showHideIcon(4);
			p_selectList.removeAll();
		}
	}

	// 아이콘 창에 있는 라벨들에게 마우스리스너 연결
	public void setIndex(int n) {
		la_icon[n].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showHideIcon(n);
			}
		});
	}
	
	public void showHideIcon(int index) {
		for(int i=0;i<la_icon.length;i++) {
			if(i==index) {
				detail[i].setVisible(true);
			}
			else {
				detail[i].setVisible(false);
			}
		}
	}

	// 라벨에 넣을 이미지 크기 조정
	public ImageIcon iconScale(String path) {
		ImageIcon icon=null;
		try {
			URL url = this.getClass().getClassLoader().getResource(path);
			Image img = ImageIO.read(url);
			img = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return icon;
	}

	// 메세지 보내기
	public void send() {
		StringBuilder sb = new StringBuilder();
		int chatMember_idx = clientMain22.chatMember22.getChatMember_idx(); // 자신과 타인을 구분지을 인스턴스
		String id = clientMain22.chatMember22.getId();
		String name = clientMain22.chatMember22.getName();
		String chat = t_input.getText();
		String icon = imgName;
		// String file = chooser.getSelectedFile().toString();

		sb.append("{");
		sb.append("\"member\": {");
		sb.append("\"ChatMember_idx\": \"" + chatMember_idx + "\",");
		sb.append("\"id\": \"" + id + "\",");
		sb.append("\"name\": \"" + name + "\"");
		sb.append("},");
		sb.append("\"chat\": \"" + chat + "\",");
		sb.append("\"icon\":\"" + icon + "\"");
		// sb.append("\"file\":\"" + file + "\"");
		sb.append("}");

		cct.sendChat(sb.toString());
		t_input.setText(""); // 메세지 전송 후 입력칸 초기화
	}

	// 메세지 출력
	public void addString(JSONObject jsonObject) {
		JSONObject member = (JSONObject) jsonObject.get("member");
		String idx = (String) member.get("ChatMember_idx");
		String name = (String) member.get("name");
		String chat = (String) jsonObject.get("chat");

		try {
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);

			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLACK);

			if (clientMain22.chatMember22.getChatMember_idx() == Integer.parseInt(idx)) {
				((StyledDocument) doc_content).setParagraphAttributes(doc_content.getLength(), 1, right, false);
				doc_content.insertString(doc_content.getLength(), "\n" + chat + "\n", right);
				content.setCaretPosition(doc_content.getLength());
			} else {
				((StyledDocument) doc_content).setParagraphAttributes(doc_content.getLength(), 1, left, false);
				doc_content.insertString(doc_content.getLength(), "\n" + name + " : " + chat + "\n", left);
				content.setCaretPosition(doc_content.getLength());
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	// 이미지 출력
	public void addIcon(JSONObject jsonObject) {
		JSONObject member = (JSONObject) jsonObject.get("member");
		String idx = (String) member.get("ChatMember_idx");
		String name = (String) member.get("name");
		String icon = (String) jsonObject.get("icon");

		URL url = this.getClass().getClassLoader().getResource(icon);

		try {
			Image image = ImageIO.read(url);
			image = image.getScaledInstance(80, 80, image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(image);
			JLabel label = new JLabel(imageIcon);

			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);

			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLACK);

			if (clientMain22.chatMember22.getChatMember_idx() == Integer.parseInt(idx)) {
				((StyledDocument) doc_content).setParagraphAttributes(doc_content.getLength(), 1, right, false);
				doc_content.insertString(doc_content.getLength(), "\n", right);
				content.setCaretPosition(doc_content.getLength());
				content.insertComponent(label);
				doc_content.insertString(doc_content.getLength(), "\n", right);
				content.setCaretPosition(doc_content.getLength());
			} else {
				((StyledDocument) doc_content).setParagraphAttributes(doc_content.getLength(), 1, left, false);
				doc_content.insertString(doc_content.getLength(), "\n" + name + " : ", left);
				content.setCaretPosition(doc_content.getLength());
				content.insertComponent(label);
				doc_content.insertString(doc_content.getLength(), "\n", left);
				content.setCaretPosition(doc_content.getLength());
			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	// 서버에 연결
	public void connect() {
		String ip = "192.168.56.1"; // 수시로 바뀜
		int port = 9999;

		try {
			Socket socket = new Socket(ip, port);
			
			cct = new ClientChatThread(this, socket);
			cct.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 아이콘 테두기 효과 주기 : 매개변수에 리스트의 몇번째 아이콘을 보더 적용할지 인덱스를 넘겨준다
	// 이미지명도 필요하군요, 그럼 객체로 넘겨받으면 되겠네요
	public void setBorderIcon(CustomIcon icon) {
		int index = iconList.indexOf(icon); // 커스텀 아이콘이 몇번째 들어있는지 조사한다

		for (int i = 0; i < iconList.size(); i++) {
			CustomIcon obj = iconList.get(i);

			if (i == index) {
				imgName = icon.path;
				obj.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // 클릭하면 테두리가 빨강으로
			} else {
				obj.setBorder(BorderFactory.createLineBorder(Color.black, 1));	// 클릭안한 아이콘들은 테두리가 검정으로
			}
		}
	}

	// 파일 탐색기 열기
	public void selectFile() {
		chooser = new JFileChooser("C:/java_workspace2/YameTalk/src/res/image");

		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		//String path = file.getAbsolutePath();

		try {
			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLACK);

			Image img = ImageIO.read(file);
			img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			
			((StyledDocument) doc_content).setParagraphAttributes(doc_content.getLength(), 1, right, false);
			doc_content.insertString(doc_content.getLength(), "\n", right);
			content.setCaretPosition(doc_content.getLength());
			content.insertComponent(new JLabel(new ImageIcon(img)));
			doc_content.insertString(doc_content.getLength(), "\n", right);
			content.setCaretPosition(doc_content.getLength());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
