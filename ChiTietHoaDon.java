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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChiTietHoaDon {
    Connection con;
    Statement stmt;

    public ChiTietHoaDon() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BHXM?useUnicode=true&characterEncoding=UTF-8", "root", "16122003");
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from ChiTietHoaDon";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Hoá Đơn</th><th>Mã Phụ Tùng</th><th>Số Lượng</th><th>Đơn Giá</th></tr>");
           
            while (rs.next()) {
                String soHoaDon = rs.getString("SoHoaDon");
                String maPhuTung = rs.getString("MaPhuTung");
                int soLuong = rs.getInt("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                out.println("<tr><td>" + soHoaDon + "</td><td>" + maPhuTung + "</td><td>" + soLuong + "</td><td>" + donGia + "</td>"
                        + "<td><a href='XemCTHD?soHoaDon=" + soHoaDon + "'>Xóa</a></td>"
                        + "<td><a href='SuaCTHD?SoHoaDon=" + soHoaDon + "'>Sửa</a></td> </tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String soHoaDon = request.getParameter("SoHoaDon");
        String maPhuTung = request.getParameter("MaPhuTung");
        int soLuong = Integer.parseInt(request.getParameter("SoLuong"));
        BigDecimal donGia = new BigDecimal(request.getParameter("DonGia"));

        try {
            openConnection();
            String sql = "INSERT INTO ChiTietHoaDon (SoHoaDon, MaPhuTung, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, soHoaDon);
                statement.setString(2, maPhuTung);
                statement.setInt(3, soLuong);
                statement.setBigDecimal(4, donGia);

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

          
// Lấy tham số maKhach từ request
            String soHoaDon = request.getParameter("soHoaDon");
            if (soHoaDon != null && !soHoaDon.isEmpty()) {
                

                // bắt đầu giao dịch 
                con.setAutoCommit(false);

                try {
                    xoatubang("ChiTietHoaDon", "SoHoaDon", soHoaDon);
                    
                    
                    
                    con.commit();

                    out.println("Xóa thành công!");
                } catch (Exception e) {
                    // Khôi phục giao dịch nếu xảy ra lỗi
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    
                  // Đặt lại cam kết tự động thành đúng
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
            String sql = "SELECT * FROM ChiTietHoaDon WHERE SoHoaDon =?";
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
                    out.println("<tr><th>Số Hoá Đơn</th><th>Mã Phụ Tùng</th><th>Số Lượng</th><th>Đơn Giá</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoHoaDon") + "</td><td>" + resultSet.getString("MaPhuTung") + "</td><td>" + resultSet.getInt("SoLuong") + "</td><td>"+ resultSet.getBigDecimal("DonGia") + "</td>"+"<td><a href='Xoa?soHoaDon=" + resultSet.getString("SoHoaDon") + "'>Xóa</a></td>"+"<td><a href='Sua?SoHoaDon=" + resultSet.getString("SoHoaDon") + "'>Sua</a></td> </tr>");
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

