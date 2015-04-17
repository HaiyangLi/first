import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
  
/**
 * 测试数据库连接
 * @author lihaiyang
 *
 */
public class TestConnection {  
    public static Connection getConnection(){  
        String driver="com.mysql.jdbc.Driver"; 
        String url="jdbc:mysql://localhost:3306/test"; 
        String name="root";
        String pwd="star1022"; 
        try{  
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(url,name,pwd);
            return conn;  
        }catch(ClassNotFoundException e){  
            e.printStackTrace();  
            return null;  
        }catch(SQLException e){  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    public static void main(String[] args) throws SQLException  
    {  
          
        Connection cc=TestConnection.getConnection();  
          
        if(!cc.isClosed())  
  
        System.out.println("ok");  
        Statement statement = cc.createStatement();  
        String sql = "select * from users";  
        ResultSet rs = statement.executeQuery(sql);  
        while(rs.next()) {  
            System.out.println(rs.getString("userid")+"");  
        }  
  
  
    }  
}  