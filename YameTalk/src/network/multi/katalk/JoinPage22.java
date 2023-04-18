package network.multi.katalk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class JoinPage22 extends Page22 {
	JLabel la_title;
	JLabel la_id;
	JTextField t_id;
	JLabel la_pass;
	JPasswordField p_pass;
	JCheckBox box_pass;
	JLabel la_match;
	JPasswordField p_match;
	JCheckBox box_match;
	JLabel la_name;
	JTextField t_name;
	JButton bt_create;
	JButton bt_login;		// 로그인 창으로 가는 버튼
	
	ChatMemberDAO22 chatMemberDAO22;
	
	public JoinPage22(ClientMain22 clientMain22) {
		super(clientMain22);
		chatMemberDAO22 = new ChatMemberDAO22();
		
		la_title = new JLabel("회원 가입");
		la_id = new JLabel("ID");
		la_pass = new JLabel("비밀번호");
		la_name = new JLabel("NAME");
		t_name = new JTextField();
		t_id = new JTextField();
		p_pass = new JPasswordField();
		p_pass.setEchoChar('*');
		box_pass = new JCheckBox();
		la_match = new JLabel("비밀번호확인");
		p_match = new JPasswordField();
		p_match.setEchoChar('*');
		box_match = new JCheckBox();
		bt_create = new JButton("계정 생성");
		bt_login = new JButton("로그인 창으로");
		
		la_title.setPreferredSize(new Dimension(500,250));
		la_title.setFont(new Font("dotum", Font.BOLD, 60));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		la_id.setPreferredSize(new Dimension(76,25));
		la_pass.setPreferredSize(new Dimension(102,25));
		la_match.setPreferredSize(new Dimension(102,25));
		la_name.setPreferredSize(new Dimension(76,25));
		la_id.setFont(new Font("Verdana", Font.BOLD, 15));
		la_pass.setFont(new Font("dotum", Font.BOLD, 15));
		la_match.setFont(new Font("dotum", Font.BOLD, 15));
		la_name.setFont(new Font("dotum", Font.BOLD, 15));
		la_id.setHorizontalAlignment(SwingConstants.CENTER);
		la_pass.setHorizontalAlignment(SwingConstants.CENTER);
		la_name.setHorizontalAlignment(SwingConstants.CENTER);
		la_match.setHorizontalAlignment(SwingConstants.CENTER);
		
		Dimension d = new Dimension(350,25);
		t_name.setPreferredSize(d);
		t_id.setPreferredSize(d);
		p_pass.setPreferredSize(d);
		p_match.setPreferredSize(d);
		
		add(la_title);
		add(la_id);
		add(t_id);
		add(la_pass);
		add(p_pass);
		add(box_pass);		
		add(la_match);
		add(p_match);
		add(box_match);
		add(la_name);
		add(t_name);
		add(bt_create);
		add(bt_login);
		
		setBackground(Color.GREEN);
		
		bt_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createID();
			}
		});
		
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientMain22.showHide(clientMain22.LOGINPAGE);
			}
		});
		
		box_pass.addItemListener(new ItemListener() {		// 비밀번호 전환
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					p_pass.setEchoChar((char)0);
				}
				else {
					p_pass.setEchoChar('*');
				}
			}
		});
		
		box_match.addItemListener(new ItemListener() {		// 비밀번호 전환
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					p_match.setEchoChar((char)0);
				}
				else {
					p_match.setEchoChar('*');
				}
			}
		});
	}
	
	public void createID() {
		ChatMember22 chatMember22 = new ChatMember22();
		chatMember22.setName(t_name.getText());
		chatMember22.setId(t_id.getText());
		
		String pass = "";
		char[] ch = p_pass.getPassword();
		for(int i=0; i<ch.length;i++) {
			pass += Character.toString(ch[i]);		// char[]을 String형으로 전환
			System.out.println(pass);
		}
		chatMember22.setPass(pass);
		
		String passConfirm = "";
		char[] ch2 = p_match.getPassword();
		for(int i=0; i<ch2.length;i++) {
			passConfirm += Character.toString(ch2[i]);		// char[]을 String형으로 전환
			System.out.println(passConfirm);
		}
		
		boolean confirm = false;			// -1, 0, 1 중에 값이 들어가기에 초기화는 다른 값으로
		chatMember22.setPass(pass);
		
		if(t_id.getText().equals("")) {
			JOptionPane.showMessageDialog(clientMain22, "ID를 입력하십시오");
		}
		else if(pass.equals("")) {
			JOptionPane.showMessageDialog(clientMain22, "패스워드를 입력하십시오");
		}
		else if(passConfirm.equals("")) {
			JOptionPane.showMessageDialog(clientMain22, "패스워드 확인을 입력하십시오");
		}
		else if(t_name.getText().equals("")) {
			JOptionPane.showMessageDialog(clientMain22, "이름을 입력하십시오");
		}
		else if(pass.equals(passConfirm)) {		//  비밀번호확인이 됐다면
			int result = chatMemberDAO22.insert(chatMember22);		// insert
			
			if(result>0) {									//  insert가 성공했다면
				JOptionPane.showMessageDialog(clientMain22, "회원가입 성공");
			}
		}
		else {
			JOptionPane.showMessageDialog(clientMain22, "빈칸 혹은 비밀번호가 맞는지 확인하십시오");
		}
	}
}
