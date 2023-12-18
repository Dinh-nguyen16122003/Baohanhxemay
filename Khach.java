package controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Khach {
        Connection con ;
        Statement stmt ;
    public Khach() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BHXM?useUnicode=true&characterEncoding=UTF-8", "root", "16122003");
        }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from Khach";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Ma Khach</th><th>Ten Khach</th><th>DiaChi</th><th>SDT</th></tr>");
            while (rs.next()) {
                String maKhach = rs.getString("Makhach");
                String tenKhach = rs.getString("TenKhach");
                String diachi = rs.getString("DiaChi");
                String sdt = rs.getString("SoDienThoai");
                out.println("<tr><td>" + maKhach + "</td><td>" + tenKhach + "</td><td>" + diachi + "</td><td>"+ sdt + "</td>"+"<td><a href='XemKhach?maKhachXoa=" + maKhach + "'>Xóa</a></td>"+"<td><a href='Sua?MaKhach=" + maKhach + "'>Sua</a></td> </tr>");
            }
            out.println("</table>");
            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
        }
        public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException
        {
        String maKhach = request.getParameter("MaKhach");
        String tenKhach = request.getParameter("TenKhach");
        String diachi = request.getParameter("DiaChi");
        String soDienThoai = request.getParameter("SoDienThoai");
       
        
        try {
           openConnection();
           String sql = "INSERT INTO Khach (Makhach,TenKhach,DiaChi, SoDienThoai) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setString(1, maKhach);
                statement.setString(2, tenKhach);
                statement.setString(3, diachi);
                statement.setString(4, soDienThoai);

                // Thực hiện chèn dữ liệu
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
         
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } 
                else 
                {
                    PrintWriter out = response.getWriter();
                  
                }
            }
            } catch (Exception e) 
            {
                   e.printStackTrace();
                    
            }
        }   
        public void xoatubang(String tableName, String columnName, String value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setString(1, value);
            statement.executeUpdate();
            }
       }
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
           try {
            openConnection();

            // Retrieve the maKhach parameter from the request
            String maKhach = request.getParameter("maKhachXoa");
            if (maKhach != null && !maKhach.isEmpty()) {
              

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("Xe", "MaKhach", maKhach);
                    xoatubang("Khach", "MaKhach", maKhach);
                    xoatubang("HoaDon","SoXe",maKhach);
                    xoatubang("Xe","SoXe",maKhach);
                    con.commit();

                    out.println("Xóa khách hàng thành công!");
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally 
                {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
               
            } 
            else{
            
            };
            
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {

            out.close();
        }
        }
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchMaKhach = req.getParameter("searchMaKhach");
        try {
            openConnection();
            String sql = "SELECT * FROM Khach WHERE Makhach=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaKhach);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách</th><th>Tên Khách</th><th>DiaChi</th><th>SDT</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("Makhach") + "</td><td>" + resultSet.getString("TenKhach") + "</td><td>" + resultSet.getString("DiaChi") + "</td><td>"+ resultSet.getString("SoDienThoai") + "</td>"+"<td><a href='Xoa?maKhachXoa=" + resultSet.getString("Makhach") + "'>Xóa</a></td>"+"<td><a href='Sua?MaKhach=" + resultSet.getString("Makhach") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        
        
        }
}

