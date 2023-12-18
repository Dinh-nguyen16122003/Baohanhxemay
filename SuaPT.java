import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SuaPT")
public class SuaPT extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Phụ Tùng</title>");
        out.println("</head>");
        out.println("<body>");

        String maPhuTung = request.getParameter("maPhuTung");
        out.println("<h2> Sua Du Lieu Phu Tung</h2>");
        out.println("<form action='SuaPT' method='post'>");
        out.println("  <input type='hidden' name='MaPhuTung' value='" + maPhuTung + "'/>");

        out.println("  <label for='TenPhuTung'>Ten Phu Tung:</label>");
        out.println("  <input type='text' id='TenPhuTung' name='TenPhuTung' required><br>");

        out.println("  <label for='NuocSX'>Nuoc San Xuat:</label>");
        out.println("  <input type='text' id='NuocSX' name='NuocSX' required><br>");

        out.println("  <label for='ThoiGianBaoHanh'>Thoi Gian Bao Hanh:</label>");
        out.println("  <input type='number' id='ThoiGianBaoHanh' name='ThoiGianBaoHanh' required><br>");

        out.println("  <label for='DonGia'>Don Gia:</label>");
        out.println("  <input type='text' id='DonGia' name='DonGia' required><br>");

        out.println("  <input type='submit' value='Sửa'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String maPhuTung = request.getParameter("MaPhuTung");
        String tenPhuTung = request.getParameter("TenPhuTung");
        String nuocSanXuat = request.getParameter("NuocSX");
        int thoiGianBaoHanh = Integer.parseInt(request.getParameter("ThoiGianBaoHanh"));
        BigDecimal donGia = new BigDecimal(request.getParameter("DonGia"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi tải driver MySQL: " + e.getMessage());
            return;
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/BHXM";
        String username = "root";
        String password = "16122003";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "UPDATE PhuTung SET  TenPhuTung=?, NuocSX=?, ThoiGianBaoHanh=?, DonGia=? WHERE MaPhuTung=?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, tenPhuTung);
                statement.setString(2, nuocSanXuat);
                statement.setInt(3, thoiGianBaoHanh);
                statement.setBigDecimal(4, donGia);
                statement.setString(5, maPhuTung);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Du lieu đa đuoc cap nhat thanh cong!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Khong the cap nhat du lieu! Hay chac chan rang Ma Phu Tung ton tai.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
