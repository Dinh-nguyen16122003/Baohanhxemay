

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Sua")
public class SuaKhach extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String maKhach = request.getParameter("MaKhach");
        out.println("<h2>Form Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form action='Sua' method='post'>");
       
        out.println("  <input type='hidden' name='MaKhach' value='" + maKhach + "'/>");
        

        out.println("  <label for='tenKhach'>Tên Khách:</label>");
        out.println("  <input type='text' id='TenKhach' name='TenKhach' required><br>");

        out.println("  <label for='DiaChi'>DiaChi:</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");

        out.println("  <label for='SoDienThoai'>SDT:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");

        out.println("  <input type='submit' value='Sua'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
                       
    
    }
    
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Lấy dữ liệu từ form
        String maKhach = request.getParameter("MaKhach");
        String tenKhach = request.getParameter("TenKhach");
        String diaChi = request.getParameter("DiaChi");
        String soDienThoai = request.getParameter("SoDienThoai");
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi tải driver MySQL: " + e.getMessage());
            return;
        }

        // Kết nối đến cơ sở dữ liệu
        String jdbcUrl = "jdbc:mysql://localhost:3306/BHXM";
        String username = "root";
        String password = "16122003";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Câu lệnh SQL để cập nhật dữ liệu trong bảng
            String sql = "UPDATE Khach SET TenKhach=?, DiaChi=?, SoDienThoai=? WHERE MaKhach=?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                
                
                statement.setString(1, tenKhach);
                statement.setString(2, diaChi);
                statement.setString(3, soDienThoai);
                statement.setString(4, maKhach);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    PrintWriter out = response.getWriter();
                    out.println("Khong the cap nhat du lieu! Hay chac chan rang Ma Khach ton tai.");
                }
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    
}
