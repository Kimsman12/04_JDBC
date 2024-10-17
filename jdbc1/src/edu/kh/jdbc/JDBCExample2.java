package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {

	public static void main(String[] args) {
		// 입력받은 급여보다 초과해서 받는 사원의
		// 사번, 이름, 급여 조회
		
		// 1. JDBC 객체 참조용 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 2. DriverManager 객체를 이용해서 Connection 객체 생성
		// 2-1) oracle JDBC Driver 객체 메모리 로드
		// 2-2) DB 연결정보 작성
		// 2-3) DB 연결정보와 DriverManager를 이용해서 Connection 객체 생성
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; 
			
			String host = "localhost"; 
			
			String port = ":1521"; 
			
			String dbName = ":XE"; 
			
			String userName = "kh_kkm";
			
			String password = "kh1234"; 
			
			conn = DriverManager.getConnection(type+host+port+dbName, userName, password);
			
			System.out.println(conn);
			
			// 3. SQL 작성
			// 입력받은 급여 -> Scanner 필요
			// int input 여기에 급여 담기
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE";
			
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("급여 : ");
			int input = sc.nextInt();
			
			// 5. Statement 객체를 이용하여 SQL 수행 후 결과 반환 받기
			while (rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				// 6. 조회 결과가 담겨있는 ResultSet 을
				// 커서 이용해 1행씩 접근해
				// 각 행에 작성된 컬럼값 얻어오기
				// -> while 안에서 꺼낸 데이터 출력	
				if (input < salary) {
					System.out.printf("사번 : %s / 이름 : %s / 급여 : %d  \n"
							, empId, empName,  salary );
				}
				
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				// 7. 사용 완료된 JDBC 객체 자원 반환
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
