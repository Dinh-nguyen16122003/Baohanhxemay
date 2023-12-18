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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Xe {
    Connection con;
    Statement stmt;

    public Xe() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BHXM?useUnicode=true&characterEncoding=UTF-8", "root", "16122003");
    }
    public void displayData(PrintWriter out) {
    try {
        openConnection();
        stmt = con.createStatement();
        String sql = "select * from Xe"; 
        ResultSet rs = stmt.executeQuery(sql);
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Số Xe</th><th>Mã Khách</th><th>Hiệu Xe</th><th>Số Sườn</th><th>Số Máy</th><th>Ngày Mua</th><th>Giá Mua</th></tr>");

        while (rs.next()) {
            String soXe = rs.getString("SoXe");
            String maKhach = rs.getString("MaKhach");
            String hieuXe = rs.getString("HieuXe");
            String soSuon = rs.getString("SoSuon");
            String soMay = rs.getString("SoMay");
            Date ngayMua = rs.getDate("NgayMua");
            BigDecimal giaMua = rs.getBigDecimal("GiaMua");

            out.println("<tr><td>" + soXe + "</td><td>" + maKhach + "</td><td>" + hieuXe + "</td><td>" + soSuon + "</td><td>" + soMay + "</td><td>" + ngayMua + "</td><td>" + giaMua + "</td>"
                    + "<td><a href='XemXe?soXe=" + soXe + "'>Xóa</a></td>"
                    + "<td><a href='SuaXe?soXe=" + soXe + "'>Sửa</a></td> </tr>");
        }
        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
}
public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String soXe = request.getParameter("SoXe");
    String maKhach = request.getParameter("MaKhach");
    String hieuXe = request.getParameter("HieuXe");
    String soSuon = request.getParameter("SoSuon");
    String soMay = request.getParameter("SoMay");

    String ngayMua = request.getParameter("NgayMua");
    

    BigDecimal giaMua = new BigDecimal(request.getParameter("GiaMua"));

    try {
        

        openConnection();
        String sql = "INSERT INTO Xe (SoXe, MaKhach, HieuXe, SoSuon, SoMay, NgayMua, GiaMua) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, soXe);
            statement.setString(2, maKhach);
            statement.setString(3, hieuXe);
            statement.setString(4, soSuon);
            statement.setString(5, soMay);
            statement.setString(6, ngayMua);
            statement.setBigDecimal(7, giaMua);

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
    } finally {
        // Close the database connection (if open)
        closeConnection();
    }
}

    public void xoatubang(String tableName, String columnName, String value) throws Exception {
    String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
    try (PreparedStatement statement = con.prepareStatement(sql)) {
        statement.setString(1, value);
        statement.executeUpdate();
    }
}

public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the maPhuTung parameter from the request
        String soXe = request.getParameter("soXe");
        if (soXe != null && !soXe.isEmpty()) {

            // Start a transaction
            con.setAutoCommit(false);

            try {
                
                xoatubang("Xe", "SoXe", soXe);
                
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
    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchSoXe = req.getParameter("searchSoXe");
    try {
        openConnection();
        String sql = "SELECT * FROM Xe WHERE SoXe = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchSoXe);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>Số Xe</th><th>Mã Khách</th><th>Hiệu Xe</th><th>Số Sườn</th><th>Số Máy</th><th>Ngày Mua</th><th>Giá Mua</th></tr>");
                out.println("<tr><td>" + resultSet.getString("SoXe") + "</td><td>" + resultSet.getString("MaKhach") + "</td><td>"
                        + resultSet.getString("HieuXe") + "</td><td>" + resultSet.getString("SoSuon") + "</td><td>"
                        + resultSet.getString("SoMay") + "</td><td>" + resultSet.getDate("NgayMua") + "</td><td>"
                        + resultSet.getBigDecimal("GiaMua") + "</td></tr>");
                out.println("</table>");
            } else {
                // Handle case where no record is found
                out.println("Không tìm thấy dữ liệu cho Số Xe: " + searchSoXe);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

    private void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    
