import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.PhuTung;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/XemPT")
public class XemPT extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PhuTung in = new PhuTung();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");

        out.println("<h2>Thông Tin Phụ Tùng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaPhuTung'>Tìm Kiếm Mã Phụ Tùng:</label>");
        out.println("  <input type='text' id='searchMaPhuTung' name='searchMaPhuTung'>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Thêm Phụ Tùng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaPhuTung'>Mã Phụ Tùng:</label>");
        out.println("  <input type='text' id='MaPhuTung' name='MaPhuTung' required><br>");
        out.println("  <label for='TenPhuTung'>Tên Phụ Tùng:</label>");
        out.println("  <input type='text' id='TenPhuTung' name='TenPhuTung' required><br>");
        out.println("  <label for='NuocSX'>Nước Sản Xuất:</label>");
        out.println("  <input type='text' id='NuocSX' name='NuocSX' required><br>");
        out.println("  <label for='ThoiGianBaoHanh'>Thời Gian Bảo Hành:</label>");
        out.println("  <input type='number' id='ThoiGianBaoHanh' name='ThoiGianBaoHanh' required><br>");
        out.println("  <label for='DonGia'>Đơn Giá:</label>");
        out.println("  <input type='text' id='DonGia' name='DonGia' required><br>");
        out.println("  <input type='submit' value='Thêm Phụ Tùng'>");
        out.println("</form>");

        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");

        String searchMaPhuTung = req.getParameter("searchMaPhuTung");

        if (searchMaPhuTung == null) {
            in.displayData(out);
        } else {
            in.TK(req, res, out);
        }

        in.xoa(req, res, out);
        in.nhap(req, res);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        in.nhap(request, response);
    }

}
