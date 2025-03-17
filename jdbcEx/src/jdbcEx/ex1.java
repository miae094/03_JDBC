package jdbcEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ex1 {
	
	// 1. 입력 최소 하나
	// 2. PreparedStatement 사용하기
	// 3. Commit 과 Rollback 
	
	// 이름을 입력받고
	// 해당 사원이 속해있는 부서원을 조회한다.
	// 단, 본인 제외
	// 사번 이름 전화번호 고용일 부서명
	
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		
		
		try {
			
			// 1. 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
			String user = "kh";
			String userpw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, userpw);
			
			//System.out.println(conn);
			// -> 연결 완료
			
			
			// 2. SQL문 작성
			
			String sql ="""
					SELECT EMP_ID, EMP_NAME, PHONE, HIRE_DATE, DEPT_TITLE
					FROM EMPLOYEE
					LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID=EMPLOYEE.DEPT_CODE)
					WHERE DEPT_CODE = ( SELECT DEPT_CODE
										FROM EMPLOYEE
										WHERE EMP_NAME = ?)
					AND EMP_NAME <> ?
					""";
			
			
			pstmt = conn.prepareStatement(sql);
			
			sc  = new Scanner(System.in);
			
			System.out.print("이름 입력 : ");
			String userName = sc.nextLine();
			
			pstmt.setString(1, userName);
			pstmt.setString(2, userName);
			
			rs = pstmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("실패");
				return;
			}
			
			while(rs.next()) {
				
				
				System.out.printf("%s, %s, %s, %s, %s \n", rs.getString(1), 
						rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5));
			}
			
			
			
		
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
				if (sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
