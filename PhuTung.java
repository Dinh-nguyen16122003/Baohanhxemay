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

public class PhuTung {
    Connection con;
    Statement stmt;

    public PhuTung() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BHXM?useUnicode=true&characterEncoding=UTF-8", "root", "16122003");
    }
   public void displayData(PrintWriter out) {
    try {
        openConnection();
        stmt = con.createStatement();
        String sql = "select * from PhuTung"; // Update table name
        ResultSet rs = stmt.executeQuery(sql);
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Mã phụ tùng</th><th>Tên Phụ Tùng</th><th>Nước Sản Xuất</th><th>Thời Gian Bảo hành</th><th>Đơn Giá</th></tr>");

        while (rs.next()) {
            String maPhuTung = rs.getString("MaPhuTung");
            String tenPhuTung = rs.getString("TenPhuTung");
            String nuocSanXuat = rs.getString("NuocSX");
            int thoiGianBaoHanh = rs.getInt("ThoiGianBaoHanh");
            BigDecimal donGia = rs.getBigDecimal("DonGia");

            out.println("<tr><td>" + maPhuTung + "</td><td>" + tenPhuTung + "</td><td>" + nuocSanXuat + "</td><td>" + thoiGianBaoHanh + "</td><td>" + donGia + "</td>"
                    + "<td><a href='XemPT?maPhuTung=" + maPhuTung + "'>Xóa</a></td>"
                    + "<td><a href='SuaPT?maPhuTung=" + maPhuTung + "'>Sửa</a></td> </tr>");
        }
        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
}
    public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String maPhuTung = request.getParameter("MaPhuTung");
    String tenPhuTung = request.getParameter("TenPhuTung");
    String nuocSanXuat = request.getParameter("NuocSX");
    int thoiGianBaoHanh = Integer.parseInt(request.getParameter("ThoiGianBaoHanh"));
    BigDecimal donGia = new BigDecimal(request.getParameter("DonGia"));

    try {
        openConnection();
        String sql = "INSERT INTO PhuTung (MaPhuTung, TenPhuTung, NuocSX, ThoiGianBaoHanh, DonGia) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, maPhuTung);
            statement.setString(2, tenPhuTung);
            statement.setString(3, nuocSanXuat);
            statement.setInt(4, thoiGianBaoHanh);
            statement.setBigDecimal(5, donGia);

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
        String maPhuTung = request.getParameter("maPhuTung");
        if (maPhuTung != null && !maPhuTung.isEmpty()) {

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("ChiTietHoaDon", "MaPhuTung", maPhuTung);
                xoatubang("PhuTung", "MaPhuTung", maPhuTung);

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
    String searchMaPhuTung = req.getParameter("searchMaPhuTung");
    try {
        openConnection();
        String sql = "SELECT * FROM PhuTung WHERE MaPhuTung =?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaPhuTung);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>Mã Phụ Tùng</th><th>Tên Phụ Tùng</th><th>Nước Sản Xuất</th><th>Thời Gian Bảo Hành</th><th>Đơn Giá</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaPhuTung") + "</td><td>" + resultSet.getString("TenPhuTung") + "</td><td>"
                        + resultSet.getString("NuocSX") + "</td><td>" + resultSet.getInt("ThoiGianBaoHanh") + "</td><td>"
                        + resultSet.getBigDecimal("DonGia") + "</td></tr>");
                out.println("</table>");
            } else {
                // Handle case where no record is found
                out.println("Không tìm thấy dữ liệu cho Mã Phụ Tùng: " + searchMaPhuTung);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
}