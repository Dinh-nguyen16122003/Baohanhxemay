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
@WebServlet("/SuaCTHD")
public class SuaCTHD extends HttpServlet {
    private static final long serialVersionUID = 1L;
Connection con;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Hoa Đon</title>");
        out.println("</head>");
        out.println("<body>");

        String soHoaDon = request.getParameter("SoHoaDon");
        out.println("<h2> Nhập Du Lieu Hoa Đon</h2>");
        out.println("<form action='SuaCTHD' method='post'>");
        out.println("  <input type='hidden' name='SoHoaDon' value='" + soHoaDon + "'/>");

      
        
        out.println("  <label for='MaPhuTung'>Ma Phu Tung:</label>");
        out.println("  <input type='text' id='MaPhuTung' name='MaPhuTung' required><br>");

        out.println("  <label for='SoLuong'>So Luong:</label>");
        out.println("  <input type='number' id='SoLuong' name='SoLuong' required><br>");

        out.println("  <label for='DonGia'>Don Gia:</label>");
        out.println("  <input type='text' id='DonGia' name='DonGia' required><br>");

        out.println("  <input type='submit' value='Sua'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String soHoaDon = request.getParameter("SoHoaDon");
        String maPhuTung = request.getParameter("MaPhuTung");
        int soLuong = Integer.parseInt(request.getParameter("SoLuong"));
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
            String sql = "UPDATE ChiTietHoaDon SET  MaPhuTung=?, SoLuong=?, DonGia=? WHERE SoHoaDon=?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                
                statement.setString(1, maPhuTung);
                
                statement.setInt(2, soLuong);
                statement.setBigDecimal(3, donGia);
                statement.setString(4, soHoaDon);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Số Hóa Đơn tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
