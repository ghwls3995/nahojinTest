package miniTest;


import java.sql.DriverManager; //public class DriverManager
import java.sql.Connection; //public interface Connection  extends Wrapper, AutoCloseable {
import java.sql.PreparedStatement; // public interface PreparedStatement extends Statement
import java.sql.SQLException; // public class SQLException extends java.lang.Exception implements Iterable<Throwable> {

public class SignUpToDatabase {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver"; // 10행 ~ 14행 데이터베이스
		// 접속을 위한 4가지 정보를 String 변수에 저장.
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "scott";
		String passwd = "tiger";
		Connection con = null;
		PreparedStatement pstmt = null;

		String name = "";
		String id = "";
		String password = "";
		try {
			Class.forName(driver);
			// 명시된 드라이버 클래스를 메모리에 로딩한다.
			con = DriverManager.getConnection(url, userid, passwd);
			// DriverManager 클래스의 getConnection() 메소드를 이용해서
//		 Connection 객체를 얻는다. 
			String sql = "INSERT INTO dept(deptno,dname,loc)" + "VALUES(?,?,?)";
			// 요청할 SQL 문을 String 변수에 저장한다. 이때 저장할 데이터 대신에 '?'
			// 기호를 사용한다. 나중에 PreparedStatement 객체의 set()메소드를
			// 사용하여 값을 동적으로 설정한다.
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, password);
			// SQL 문 전송에 필요한 PreparedStatement 객체를
			// Connection 객체의 preparedStatement(sql)메소드를 이용해서 얻고 '?' 대신에
			// 설정할 값을 인덱스(1부터 시작) 를 사용하여 set() 메소드로 설정한다.
			int n = pstmt.executeUpdate();
			// INSERT 문을 요청하기 때문에 executeUpdate() 메소드를
			// 사용하며 결과는 int 형으로 저장한다.
			System.out.println(n + "개의 레코드가 저장");
			// 반환된 정수값을 출력하면 SQL 문을 실행한 뒤에 데이터베이스에 적용된
			// 레코드의 개수를 알수 있다.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				// 사용한 자원을 finally 문을 이용해서 반납한다.
				// 자원 반납은 사용했던 객체의 역순으로 하며 모두 공통적으로
				// close() 메소드를 사용한다.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}