package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {

    // Thông tin kết nối
	// Với SQLEXPRESS, bỏ port, driver tự dò port
	//private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QLRCP;encrypt=false;trustServerCertificate=true;";
	private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=QLRCP;encrypt=false;";
	private static final String USER = "sa";
	private static final String PASSWORD = "123";



    // Singleton instance
    private static ConnectDB instance = new ConnectDB();
    private static Connection connection = null;

    // Biến cờ để kiểm soát log chỉ in 1 lần
    private static boolean isLogged = false;

    // Lấy instance
    public static ConnectDB getInstance() {
        return instance;
    }

    /**
     * Lấy kết nối tới cơ sở dữ liệu (chỉ tạo 1 connection duy nhất)
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                isLogged = false; // reset cờ log khi tạo kết nối mới
            }

            // Chỉ log 1 lần duy nhất sau khi kết nối được tạo thành công
            if (!isLogged && connection != null && !connection.isClosed()) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.INFO, "✅ Database connection established successfully");
                isLogged = true;
            }

        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE,
                    "❌ SQL Server Driver not found. Please check if mssql-jdbc driver is in classpath.", e);
            System.err.println("Lỗi: Không tìm thấy driver SQL Server. Vui lòng kiểm tra thư viện mssql-jdbc.");
        } catch (SQLException e) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE,
                    "❌ Connection failed. Please check database server, URL, username and password.", e);
            System.err.println("Lỗi kết nối database: " + e.getMessage());
            System.err.println("Vui lòng kiểm tra:");
            System.err.println("1. SQL Server đang chạy");
            System.err.println("2. Database QLRCP đã được tạo");
            System.err.println("3. Username và Password đúng");
        }
        return connection;
    }

    /**
     * Đóng tất cả kết nối, PreparedStatement, ResultSet
     */
    public static void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null && !con.isClosed()) con.close();
            isLogged = false; // reset để lần sau log lại khi mở kết nối mới
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Đóng tất cả kết nối, Statement, ResultSet
     */
    public static void closeAll(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null && !con.isClosed()) con.close();
            isLogged = false;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        Connection con = ConnectDB.getConnection();
        if (con != null) {
            System.out.println("✅ Kết nối SQL Server thành công!");
        } else {
            System.out.println("❌ Kết nối SQL Server thất bại!");
        }
    }

}
