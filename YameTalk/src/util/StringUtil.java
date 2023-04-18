package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	
	// 넘겨받은 숫자가 1자리수이면, 앞에 0붙이기
	public static String getNumString(int num) {
		String str = null;
		if(num<10) {	// 한자리수
			str= "0"+num;
		}else {	// 두자리수
			str = Integer.toString(num);
		}
		return str;
	}
	
	
	// 확장자 추출하여 반환받기
	public static String getExtends(String filename) {
		int lastIndex = filename.lastIndexOf(".");
		//System.out.println(lastIndex);
		return filename.substring(lastIndex+1, filename.length());
	}
	
		// 파일명 반환하기
	public static String createFilename(String url) {
		// 파일명 만들기
		long time = System.currentTimeMillis();
		
		// 확장자 구하기
		String ext = StringUtil.getExtends(url);
		
		return time+"."+ext;
	}
		
	// 비밀번호 암호화
	// 자바의 보안과 관련된 기능을 지원하는 api가 모여있는 패키지 : java.security
	public static String getConvertedPassword(String pass) {
		// 암호화 객체
		StringBuffer hexString = null;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));
			
			// String은 불변이다!!!	따라서 그 값이 변경될 수 없다
			// 따라서 String 객체는 반복문 회수가 클 때는 절대 누적식을 사용해선 안된다
			// 해결책 : 변경가능한 문자열 객체를 지원하는 StringBuffer, StringBuilder 등을 활용하기
			hexString = new StringBuffer();
			
			for(int i=0; i<hash.length; i++) {
				String hex = Integer.toHexString(0xff& hash[i]);
				//System.out.println(hex);
				if(hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
			//System.out.println(hexString.toString());
			//System.out.println(hexString.length());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hexString.toString();
	}
	
//	public static void main(String[] args) {
//		String result = getConvertedPassword("minzino");
//		System.out.println(result.length());
//	}
}
