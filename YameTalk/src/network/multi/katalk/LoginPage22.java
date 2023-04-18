package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPage22 extends Page22{
	JLabel la_title;
	JLabel la_id;
	JTextField t_id;
	JLabel la_pass;
	JPasswordField p_pass;
	JButton bt_login;
	JButton bt_join;
	JButton bt_pass;
	
	ChatMemberDAO22 chatMemberDAO22;
	
	public LoginPage22(ClientMain22 clientMain22) {
		super(clientMain22);
		chatMemberDAO22 = new ChatMemberDAO22();
		
		la_title = new JLabel("YameTalk");
		la_id = new JLabel("ID");
		t_id = new JTextField();
		la_pass = new JLabel("PASSWORD");
		p_pass = new JPasswordField();
		
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");
		bt_pass = new JButton("비밀번호 ");
		
		la_title.setPreferredSize(new Dimension(520,300));
		la_title.setFont(new Font("Verdana", Font.BOLD, 45));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		la_id.setPreferredSize(new Dimension(108,25));
		la_pass.setPreferredSize(new Dimension(108,25));
		la_id.setFont(new Font("Verdana", Font.BOLD, 15));
		la_pass.setFont(new Font("Verdana", Font.BOLD, 15));
		la_id.setHorizontalAlignment(SwingConstants.CENTER);
		la_pass.setHorizontalAlignment(SwingConstants.CENTER);
		
		Dimension d = new Dimension(350,25);
		t_id.setPreferredSize(d);
		p_pass.setPreferredSize(d);
		
		
		add(la_title);
		add(la_id);
		add(t_id);
		add(la_pass);
		add(p_pass);
		
		add(bt_login);
		add(bt_join);
		
		setBackground(Color.YELLOW);
		
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
		
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientMain22.showHide(clientMain22.JOINPAGE);
			}
		});
	}
	
	public void loginCheck() {
		ChatMember22 chatMember22 = new ChatMember22();	// empty dto
		chatMember22.setId(t_id.getText());
		String str = new String(p_pass.getPassword());	// char[]을 String형으로 전환
		chatMember22.setPass(str);
		
		chatMember22 = chatMemberDAO22.select(chatMember22);
		if(chatMember22 == null) {
			JOptionPane.showMessageDialog(clientMain22, "로그인 정보가 올바르지 않습니다");
		}
		else {
			JOptionPane.showMessageDialog(clientMain22, "로그인 성공");
			// clientMain22에 사용자 정보 보관해두기
			clientMain22.chatMember22 = chatMember22;
			clientMain22.showHide(clientMain22.CHATROOMPAGE);
			ChatPage22 chatPage22 = (ChatPage22)clientMain22.pages[clientMain22.CHATPAGE];
			chatPage22.connect();
		}
	}
}
