package jdbcEx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ex2 {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		FileWriter fw = null; 		// 문자 기반 파일 출력 스트림
		BufferedWriter bw = null; 	// 문자 출력 보조 스트림
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			String user = "workbook";
			String pw = "workbook";
			
			
			conn = DriverManager.getConnection(url, user, pw);
			// 연결 완료
			
			String sql = """
					SELECT STUDENT_NO, STUDENT_NAME
					FROM TB_STUDENT
					JOIN TB_GRADE USING(STUDENT_NO)
					JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
					WHERE DEPARTMENT_NAME = '국어국문학과'
					GROUP BY STUDENT_NO, STUDENT_NAME
					HAVING AVG(POINT) = (SELECT MAX(AVG(POINT))
										FROM TB_STUDENT
										JOIN TB_GRADE USING(STUDENT_NO)
										JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
										WHERE DEPARTMENT_NAME = '국어국문학과'	
										GROUP BY STUDENT_NO
										)
					""";
			
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		StringBuilder  sb = new StringBuilder();
		while(rs.next()) {
			//System.out.printf("%s, %s", rs.getString(1), rs.getString(2));
			sb.append(rs.getString(1));
			sb.append(rs.getString(2));
		}
			
		String str = sb.toString();
		
		// 파일과 출력스트림 연결(파일이 없으면 생성해줌)
		fw = new FileWriter("/io_test/20250305/0317_output.txt");
		
		bw = new BufferedWriter(fw);
	
		bw.write(str); // str 에 저장된 내용을 연결된 파일에 출력
		
		System.out.println("출력 완료");
		
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(bw != null) bw.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
