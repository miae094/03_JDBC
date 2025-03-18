package edu.kh.jdbc.service;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

// (Model 중 하나) Service : 비즈니스 로직을 처리하는 계층
// 비즈니스 로직 -> 데이터를 가지고 가공하고 원하는 모양새로 만들고
//					트랜잭션 관리(commit, rollback)
public class UserService {
	
	// 필드
	private UserDAO dao = new UserDAO();

	
	
	
	// 메서드
	// User -> 참조형, null 이 기본값
	/** 전달받은 아이디와 일치하는 User 정보 반환 서비스
	 * @param input (입력된 아이디)
	 * @return 아이디가 일치하는 회원 정보가 담긴 User 객체,
	 * 			없으면 null 반환
	 */
	public User selectId(String input) {
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공(비즈니스 로직) -> 할게 없으면 넘어감
		
		// 3. DAO 메서드 호출 결과 반환
		User user = dao.selectId(conn, input);
		
		// 4. DML(commit / rollback) (단순 select 면 넘어가도됨)
		
		// 5. 다 쓴 커넥션 자원 반환
		JDBCTemplate.close(conn);
		
		
		// 6. 결과를 view에게 리턴
		return user;
	}




	public boolean insertUser(String userId, String userPw, String userName) {
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공(비즈니스 로직) -> 할게 없으면 넘어감
		
		// 3. DAO 메서드 호출 결과 반환
		int result = dao.insertUser(conn, userId, userPw, userName);
		boolean flag = false;
		
		// 4. DML(commit / rollback) (단순 select 면 넘어가도됨)
		if(result > 0) {	// insert 성공시
			JDBCTemplate.commit(conn); // DB에 INSERT 영구 반영
			flag = true;
		} else {	// insert 실패
			
			JDBCTemplate.rollback(conn);	// 실패 시 ROLLBACK
		}
		
		
		// 5. 다 쓴 커넥션 자원 반환
		JDBCTemplate.close(conn);
		
		
		// 6. 결과를 view에게 리턴
		
		return flag;
	}




	public User selectUser() {
		
		
		
		return null;
	}
	

}
