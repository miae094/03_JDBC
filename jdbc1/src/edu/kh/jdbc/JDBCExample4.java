package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	
	public static void main(String[] args) {
	
		// 부서명을 입력받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		
		// [실행화면]
		// 부서명 입력 : 총무부 
		// 200 / 선동일 / 총무부 / 대표
		// 202 / 노옹철 / 총무부 / 부사장
		// 201 / 송종기 / 총무부 / 부사장
		
		// 부서명 입력 : 개발팀
		// 일치하는 부서가 없습니다!
		
		// hint : SQL 에서 문자열은 양쪽 '' (홑따옴표) 필요
		// ex) 총무부 입력 => '총무부'
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
	
		try {
			
			// DB연결
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String host = "localhost";
			String port = ":1521";
			String dbName = ":XE";
			
			String userName = "kh"; // 사용자 계정명
			String password = "kh1234"; // 사용자 비밀번호
			
			conn = DriverManager.getConnection(type+host+port+dbName, userName, password);
			
			// SQL 작성
			
			sc = new Scanner(System.in);
			System.out.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			String sql = """ 
					SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME
					FROM EMPLOYEE E
					JOIN JOB J ON(E.JOB_CODE = J.JOB_CODE)
					LEFT JOIN DEPARTMENT D ON (E.DEPT_CODE = D.DEPT_ID)
					WHERE DEPT_TITLE = '""" + input + "'"
					+ " ORDER BY E.JOB_CODE";
			
			String sql2 = """
					SELECT DEPT_TITLE
					FROM DEPARTMENT
					WHERE DEPT_TITLE = '""" + input + "'";
			
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			
			/*
			 * flag 이용법
			 * 
			boolean flag = true;
			// 조회 결과가 있다면 false, 없다면 true;
			
			
			while(rs.next()) {
				flag = false;
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%s / %s / %s / %s \n", empId, empName, deptTitle, jobName);
			}
			
			if(flag) {
				System.out.println("일치하는 부서가 없습니다!");
			}
			*/
			
			// return 이용법
			if(!rs.next()) {	// rs.next() 커서를 다음행으로 이동
				System.out.println("일치하는 부서가 없습니다!");
				return; // 다른 코드들은 수행 안되지만 finally 는 수행하고 메서드 종료됨.
			}
			

			do {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%s / %s / %s / %s \n", empId, empName, deptTitle, jobName);
			} while (rs.next());
			
		} catch (Exception e) {
			e.printStackTrace();
		
		
		} finally {
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
