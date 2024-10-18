package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

	// 입력받은 최소 급여 이상
	// 입력받은 최대 급여 이하를 받는
	// 사원의 사번, 이름, 급여를 급여내림차순으로 조회
	// -> 이클립스 콘솔 출력
	// [실행화면]
	// 최소급여 : 1000000
	// 최대급여 : 3000000
	
	// 사번 / 이름 / 급여

public class JDBCExample3 {
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
			String userName = "kh_kkm";
			
			String password = "kh1234"; 
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("최소 급여 : ");
			int min = sc.nextInt();

			System.out.print("최대 급여 : ");
			int max = sc.nextInt();
			
			String sql = """ 
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE 
					WHERE SALARY BETWEEN """ + min + " AND " + max +
					" ORDER BY SALARY DESC";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
		
		int count = 0;
		
		while(rs.next()) {
			
			count++;
			
			String empId = rs.getString("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			int salary = rs.getInt("SALARY");
			
			System.out.printf("사번 : %s / 이름 : %s / 급여 : %d  \n"
					, empId, empName,  salary );
		}
		
		System.out.println("총원 : " + count + "명");
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			try {
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			}
		 catch (Exception e2) {
			e2.printStackTrace();
		}}
		
		
	}
}
