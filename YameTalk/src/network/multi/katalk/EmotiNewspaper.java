package network.multi.katalk;

import java.awt.Color;

import javax.swing.text.BadLocationException;

public class EmotiNewspaper extends EmotiWindow{

	public EmotiNewspaper(ChatPage22 chatPage22) {
		super(chatPage22);
	}
	
	// 컨테이너에 붙이기
	public void openPage() {
//		if(chatPage22.iconList != null) {
//			// 다른 페이지의 아이콘이 들어있으면 비우기
//			chatPage22.iconList.removeAll(chatPage22.iconList);
//		}
		for (int i = 1; i < 13; i++) {
			try {
				CustomIcon customIcon = new CustomIcon(chatPage22, "res/image/newspaper/" + i + ".png");

				chatPage22.iconList.add(customIcon);
				chatPage22.detail[chatPage22.EMOTINEWSPAPER].insertComponent(customIcon);

				// 컴포넌트들은 텍스트와 다르게 wrapping이 안되있기에 중간에 공백을 넣어 위치를 조절한다
				chatPage22.doc_detail[chatPage22.EMOTINEWSPAPER].insertString(chatPage22.doc_detail[chatPage22.EMOTINEWSPAPER].getLength(), " ", null);
				chatPage22.detail[chatPage22.EMOTINEWSPAPER].setCaretPosition(chatPage22.doc_detail[chatPage22.EMOTINEWSPAPER].getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
