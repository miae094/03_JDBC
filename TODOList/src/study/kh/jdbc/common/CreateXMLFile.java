package study.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {
		// XML(eXtensible Markup Language) : 단순화된 데이터 기술 형식
		// Markup Language : html 처럼 <> 를 사용하는 언어 

		// XML에 저장되는 데이터 형식 -> Key : Vaule(Map)
		// -> Key, Value 모두 String(문자열) 형식
		
		// XML 파일을 읽고, 쓰기 위한 IO 관련된 클래스 필요

		// * Propeties 컬렉션 객체 *
		// - Map의 후손 클래스
		// - Key, Value 모두 String(문자열 형식)
		// - XML 파일을 읽고, 쓰는데 특화된 메서드 제공
		
		
		try {
			Scanner sc = new Scanner(System.in);
			
			// Properties 객체 생성
			Properties prop = new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			// FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			
			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName + ".xml file!!!");
			
			System.out.println(".xml 파일 생성 완료");
					
			
		
		
		
		
		
		} catch (Exception e) {
			System.out.println("xml 파일 생성 중 예외 발생");
			e.printStackTrace();
		
		}
		
		
	}

}
