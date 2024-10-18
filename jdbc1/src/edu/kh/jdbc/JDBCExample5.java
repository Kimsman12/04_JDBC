package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		// 아이디, 비밀번호, 이름을 입력받아
		// TB_USER 테이블에 삽입(INSERT) 하기
		
		// 1. JDBC 객체 참조변수 선언
		Connection conn = null;
		
		
		/*
		 * java.sql.PreparedStatement
		 * - SQL 중간에 ? (위치홀더, placeholder)를 작성하여 
		 *   ? 사이에 java 값을 대입할 준비가 되어있는 Statement
		 *   
		 * 장점 1 : SQL 작성이 간단해짐
		 * 장점 2 : ?에 값 대입 시 자료형에 맞는 형태의 리터럴 표기법으로 대입됨
		 * 장점 3 : 성능, 속도에서 우위를 가지고 있음
		 * 
		 * ** PreparedStatement 는 Statement의 자식 **
		 * 
		 * */
		
		PreparedStatement pstmt = null;
		// SELECT 가 아니기 때문에 ResultSet 필요 없음
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_kkm";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("이름 입력 : ");
			String name = sc.nextLine();
			
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			/*
			 * AutoCommit 끄기
			 * 
			 * 개발자가 트랜잭션을 마음대로 제어하기 위해서 끈다
			 * 
			 * */
			conn.setAutoCommit(false);
			
			
			
			// 4. PreparedStatement 객체 생성
			// -> 객체 생성과 동시에 SQL이 담겨지게 됨
			// -> 미리 ? 에 값을 받을 준비를 해야되기 때문에
			pstmt = conn.prepareStatement(sql);
			
			// 5. ? 위치 홀더에 알맞은 값 대입
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			// -> 여기까지 실행하면 SQL이 작성 완료된 상태
			
			// 6. SQL(INSERT) 수행 후 결과(int) 반환 받기
			
			int result = pstmt.executeUpdate();
			
			// 7. result 값에 따른 결과 처리 + 트랜잭션 제어 처리
			
			if(result > 0) {
				System.out.println(name + "님이 추가 되었습니다");
				conn.commit();
				
			} else {
				System.out.println("추가 실패");
				conn.rollback();
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		
		
		

	}

}
