import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SuaXe")
public class SuaXe extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Sửa Dữ Liệu Xe</title>");
        out.println("</head>");
        out.println("<body>");

        String soXe = request.getParameter("soXe");
        out.println("<h2>Sửa Dữ Liệu Xe</h2>");
        out.println("<form action='SuaXe' method='post'>");
        out.println("  <input type='hidden' name='SoXe' value='" + soXe + "'/>");

        out.println("  <label for='MaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");

        out.println("  <label for='HieuXe'>Hiệu Xe:</label>");
        out.println("  <input type='text' id='HieuXe' name='HieuXe' required><br>");

        out.println("  <label for='SoSuon'>Số Sườn:</label>");
        out.println("  <input type='text' id='SoSuon' name='SoSuon' required><br>");

        out.println("  <label for='SoMay'>Số Máy:</label>");
        out.println("  <input type='text' id='SoMay' name='SoMay' required><br>");

        out.println("  <label for='NgayMua'>Ngày Mua:</label>");
        out.println("  <input type='date' id='NgayMua' name='NgayMua' required><br>");

        out.println("  <label for='GiaMua'>Giá Mua:</label>");
        out.println("  <input type='text' id='GiaMua' name='GiaMua' required><br>");

        out.println("  <input type='submit' value='Sửa'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String soXe = request.getParameter("SoXe");
        String maKhach = request.getParameter("MaKhach");
        String hieuXe = request.getParameter("HieuXe");
        String soSuon = request.getParameter("SoSuon");
        String soMay = request.getParameter("SoMay");
        String ngayMuaString = request.getParameter("NgayMua");
        String giaMuaString = request.getParameter("GiaMua");

        try {
            Date ngayMua = new SimpleDateFormat("yyyy-MM-dd").parse(ngayMuaString);
            BigDecimal giaMua = new BigDecimal(giaMuaString);

            updateData(soXe, maKhach, hieuXe, soSuon, soMay, ngayMua, giaMua);

            out.println("Dữ liệu đã được cập nhật thành công!");
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateData(String soXe, String maKhach, String hieuXe, String soSuon, String soMay, Date ngayMua,
            BigDecimal giaMua) throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/BHXM";
        String username = "root";
        String password = "16122003";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "UPDATE Xe SET MaKhach=?, HieuXe=?, SoSuon=?, SoMay=?, NgayMua=?, GiaMua=? WHERE SoXe=?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maKhach);
                statement.setString(2, hieuXe);
                statement.setString(3, soSuon);
                statement.setString(4, soMay);
                statement.setDate(5, new java.sql.Date(ngayMua.getTime()));
                statement.setBigDecimal(6, giaMua);
                statement.setString(7, soXe);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    // Update successful
                } else {
                    // Update failed, handle accordingly
                }
            }
        }
    }
}
