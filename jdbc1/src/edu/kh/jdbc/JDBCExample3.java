package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		// -> 이클립스 콘솔 출력
		
		// [실행화면]
		// 최소 급여 : 1000000
		// 최대 급여 : 4000000
		
		// 사번 / 이름 / 급여
		
		
		// 1. JDBC 객체 참조변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		try {
			
			// 2. DriverManager 객체 이용하여 Connection 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String host = "localhost";
			String port = ":1521";
			String dbName = ":XE";
			
			String userName = "kh"; // 사용자 계정명
			String password = "kh1234"; // 사용자 비밀번호
			
			conn = DriverManager.getConnection(type+host+port+dbName, userName, password);
			
			// 3. SQL 작성
			System.out.println(conn);
			sc = new Scanner(System.in);
			
			System.out.print("최소 급여 : ");
			int input1 = sc.nextInt();

			System.out.print("최대 급여 : ");
			int input2 = sc.nextInt();
			
			
			/*
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY BETWEEN  " + input1
					+ " AND " + input2 + " ORDER BY SALARY DESC";
			*/
			
			// Java 13부터 지원하는 Text Block(""") 문법
			// 자동으로 개행 포함 + 문자열 연결이 처리됨
			// 기존처럼 + 연산자로 문자열을 연결할 필요가 없음
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN 
					""" + input1 + " AND " + input2 + " ORDER BY SALARY DESC";
			
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			
			// 6. 1행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d \n", empId, empName, salary );
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			
			// 7. close 해주기
				try {

					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(conn != null) conn.close();
					if (sc != null) sc.close();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
		
	}

}
