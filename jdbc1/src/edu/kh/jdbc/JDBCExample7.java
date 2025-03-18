package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {
	public static void main(String[] args) {

		// EMPLOYEE 테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것

		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순

		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2

		// 사번 | 이름 | 성별 | 급여 | 직급명 | 부서명
		// --------------------------------------------------------
		// 218 | 이오리 | F | 3890000 | 사원 | 없음
		// 203 | 송은희 | F | 3800000 | 차장 | 해외영업2부
		// 212 | 장쯔위 | F | 3550000 | 대리 | 기술지원부
		// 222 | 이태림 | F | 3436240 | 대리 | 기술지원부
		// 207 | 하이유 | F | 3200000 | 과장 | 해외영업1부
		// 210 | 윤은해 | F | 3000000 | 사원 | 해외영업1부

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";

			String userName = "kh"; // 사용자 계정명
			String password = "kh1234"; // 사용자 비밀번호

			conn = DriverManager.getConnection(url, userName, password);

			conn.setAutoCommit(false);

			sc = new Scanner(System.in);

			System.out.print("조회할 성별(M/F) : ");
			String gender = sc.next().toUpperCase();

			System.out.print("급여 범위(최소, 최대 순서로 작성) : ");
			int minSal = sc.nextInt();
			int maxSal = sc.nextInt();

			System.out.print("급여 정렬(1.ASC, 2.DESC) : ");
			int inputOrderby = sc.nextInt();

			String orderBy = "";

			if (inputOrderby == 1) {
				orderBy = "ASC";
			} else if (inputOrderby == 2) {
				orderBy = "DESC";
			}

			String sql = """
					SELECT EMP_ID, EMP_NAME, DECODE(SUBSTR(EMP_NO, 8,1), '1', 'M', '2', 'F') GENDER,
					SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
					FROM EMPLOYEE
					JOIN JOB USING(JOB_CODE)
					LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
					WHERE DECODE(SUBSTR(EMP_NO, 8,1), '1', 'M', '2', 'F') = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY SALARY""" + " " + orderBy;	// String block 은 문자형은 자동으로 '' 가 붙기 때문에 따로 써줘야함
			
			// 급여의 오름차순인지 내림차순인지 조건에 따라 sql 보완하기
			// 선생님 방법!
//			if(inputOrderby == 1) sql += " ASC";
//			else sql+= " Desc";
			

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gender);
			pstmt.setInt(2, minSal);
			pstmt.setInt(3, maxSal);

			rs = pstmt.executeQuery();

			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("===================================================");
			
			boolean flag = true; // true : 조회결과 없음, false : 조회결과 존재함
			
			
			while (rs.next()) {
				flag = false;	// while 문이 1회 이상 반복함 == 조회결과가 1행이라도 있다

											// 컬럼명, 별칭, 조회된 컬럼 순서 가능
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gender2 = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
						empId, empName, gender2, salary, jobName, deptTitle);
				 
				 //System.out.printf("%s | %s | %s | %d    | %s  | %s \n",
				 //		 empId, empName, gender2, salary, jobName, deptTitle);
				

//				System.out.printf("%s | %s | %s | %d    | %s | %s \n", rs.getString(1),
//						 rs.getString(2), rs.getString(3), rs.getInt(4),
//						 rs.getString(5), rs.getString(6) );
				
			}
			
			
			
			if(flag) {	// flag == true 인 경우 -> while 문 안쪽 수행 X -> 조회 결과가 1행도 없음
				System.out.println("조회 결과 없음");
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				if (rs != null) rs.close();
				if (sc != null) sc.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
