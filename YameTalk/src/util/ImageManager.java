package util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// 이미지에 대한 경로를 전달하면, 이미지 객체를 반환하는 메서드 정의
public class ImageManager {
	public Image[] createImages(String[] imgName) {
		Class myClass = this.getClass(); // 현재 클래스에 대한 정보를 가진 클래스를 얻는다
		Image[] images = new Image[imgName.length];

		// 자원의 위치를 의미, 패키지경로도 처리 가능
		for (int i = 0; i < imgName.length; i++) {
			URL url = myClass.getClassLoader().getResource(imgName[i]);
			try {
				// 생성된 이미지를 배열에 담기
				images[i] = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}

	// 라벨메뉴에 아이콘 붙이기
	// path : 클래스path 내의 이미지 경로
	// width, height : 크기를 조정하고 싶은 값
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass = this.getClass();
		URL url = myClass.getClassLoader().getResource(path);
		// ClassLoader loader = myClass.getClassLoader();
		// URL url = loader.getResource(path);
		Image scaledImg = null;
		try {
			Image img = ImageIO.read(url);
			scaledImg = img.getScaledInstance(width, height, img.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(scaledImg);

		return icon;
	}
	
	// 이미지 얻어오기
	public Image getImage(String path, int width, int height) {
		Image image = null;
		URL url = this.getClass().getClassLoader().getResource(path);
		try {
			image = ImageIO.read(url);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	// 이미지 삭제
	public static boolean deleteFile(String path) {
		File file = new File(path);
		boolean result = file.delete();
		
		return result;
	}
		
}
