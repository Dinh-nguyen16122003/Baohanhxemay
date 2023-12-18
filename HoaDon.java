package controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HoaDon {
    Connection con;
    Statement stmt;

    public HoaDon() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BHXM?useUnicode=true&characterEncoding=UTF-8", "root", "16122003");
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from HoaDon";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Hoá Đơn</th><th>Số Xe</th><th>Ngày Lập Hoá Đơn</th></tr>");

            while (rs.next()) {
                String soHoaDon = rs.getString("SoHoaDon");
                String soXe = rs.getString("SoXe");
                String ngayNhap = rs.getString("NgayLapHoaDon");

                out.println("<tr><td>" + soHoaDon + "</td><td>" + soXe + "</td><td>" + ngayNhap + "</td>"
                        + "<td><a href='XemHD?soHoaDon=" + soHoaDon + "'>Xóa</a></td>"
                        + "<td><a href='SuaHD?SoHoaDon=" + soHoaDon + "'>Sửa</a></td> </tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String soHoaDon = request.getParameter("SoHoaDon");
        String soXe = request.getParameter("SoXe");
        String ngayNhapStr = request.getParameter("NgayLapHoaDon");

        try {
            openConnection();
            String sql = "INSERT INTO HoaDon (SoHoaDon, SoXe, NgayLapHoaDon) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, soHoaDon);
                statement.setString(2, soXe);

                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date ngayNhap = dateFormat.parse(ngayNhapStr);
                statement.setDate(3, new java.sql.Date(ngayNhap.getTime()));

                // Thực hiện chèn dữ liệu
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Không thể chèn dữ liệu!");
                }
            }
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Other methods...
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
            String soHoaDon = request.getParameter("soHoaDon");
            if (soHoaDon != null && !soHoaDon.isEmpty()) {
                

                // Start a transaction
                con.setAutoCommit(false);

                try 
                {
                    xoatubang("ChiTietHoaDon", "SoHoaDon", soHoaDon);
                    xoatubang("HoaDon", "SoHoaDon", soHoaDon);
                    
                    
                    
                    con.commit();

                    out.println("Xóa thành công!");
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
            } 
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {

            out.close();
        }
        }
public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchSoHoaDon = req.getParameter("searchSoHoaDon");
        try {
            openConnection();
            String sql = "SELECT * FROM HoaDon WHERE SoHoaDon =?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSoHoaDon);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Số Hoá Đơn</th><th>So Xe</th><th>Ngay Lap Hoa Don</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoHoaDon") + "</td><td>" + resultSet.getString("SoXe") + "</td><td>" + resultSet.getString("NgayLapHoaDon") + "</td>"+"<td><a href='Xoa?soHoaDon=" + resultSet.getString("SoHoaDon") + "'>Xóa</a></td>"+"<td><a href='Sua?SoHoaDon=" + resultSet.getString("SoHoaDon") + "'>Sua</a></td> </tr>");
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
