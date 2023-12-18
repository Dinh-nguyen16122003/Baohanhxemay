import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.Khach;
import controler.Menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemKhach")
public class XemKhach extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemKhach() {
        super();
    }
    Khach in = new Khach();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
     Menu menu = new Menu();
        menu.htmenu(res, out);
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        
        out.println("<h2>Tìm Kiếm Khách Hàng</h2>");
        out.println("<form  method='get'>");
        out.println("  <label for='searchMaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='searchMaKhach' name='searchMaKhach' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        out.println("<h2> Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");
     
        out.println("  <label for='TenKhach'>Tên Khách:</label>");
        out.println("  <input type='text' id='TenKhach' name='TenKhach' required><br>");
        out.println("  <label for='DiaChi'>DiaChi:</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");
        out.println("  <label for='SoDienThoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");
        out.println("  <input type='submit' value='Submit'>");
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
        
        String searchMaKhach = req.getParameter("searchMaKhach");
        if (searchMaKhach == null) {
            in.displayData(out);
        }
        else{
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