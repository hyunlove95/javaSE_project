package network.multi.katalk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMemberDAO22 {
	DBManager dbManager = DBManager.getInstance();
	
	public ChatMember22 select(ChatMember22 chatMember22) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		ChatMember22 chatMember = null;
		
		con = dbManager.getConnection();
		String sql = "select * from chatMember where id = ? and pass = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, chatMember22.getId());
			pstmt.setString(2, chatMember22.getPass());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chatMember = new ChatMember22();
				chatMember.setChatMember_idx(rs.getInt("chatMember_idx"));
				chatMember.setId(rs.getString("ID"));
				chatMember.setPass(rs.getString("PASS"));
				chatMember.setName(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		
		return chatMember;
	}
	
	public int insert(ChatMember22 chatMember22) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		con = dbManager.getConnection();
		String sql = "insert into chatMember(chatMember_idx, id, pass, name)";
		sql += " values(seq_chatMember.nextval, ?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, chatMember22.getId());
			pstmt.setString(2, chatMember22.getPass());
			pstmt.setString(3, chatMember22.getName());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
}
