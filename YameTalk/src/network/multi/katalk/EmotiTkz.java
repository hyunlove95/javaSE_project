package network.multi.katalk;

import javax.swing.text.BadLocationException;

public class EmotiTkz extends EmotiWindow{

	public EmotiTkz(ChatPage22 chatPage22) {
		super(chatPage22);
	}
	
	public void openPage() {
//		if(chatPage22.iconList != null) {
//			// 다른 페이지의 아이콘이 들어있으면 비우기
//			chatPage22.iconList.removeAll(chatPage22.iconList);	
//		}
		for (int i = 1; i < 10; i++) {
			try {
				CustomIcon customIcon = new CustomIcon(chatPage22, "res/image/tkz/" + i + ".png");

				chatPage22.iconList.add(customIcon);
				chatPage22.detail[chatPage22.EMOTITKZ].insertComponent(customIcon);

				// 컴포넌트들은 텍스트와 다르게 wrapping이 안되있기에 중간에 공백을 넣어 위치를 조절한다
				chatPage22.doc_detail[chatPage22.EMOTITKZ].insertString(chatPage22.doc_detail[chatPage22.EMOTITKZ].getLength(), " ", null);
				chatPage22.detail[chatPage22.EMOTITKZ].setCaretPosition(chatPage22.doc_detail[chatPage22.EMOTITKZ].getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
