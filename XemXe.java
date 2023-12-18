import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.Xe;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/XemXe")
public class XemXe extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Xe in = new Xe();

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

        out.println("<h2>Thông Tin Xe</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchSoXe'>Tìm Kiếm Số Xe:</label>");
        out.println("  <input type='text' id='searchSoXe' name='searchSoXe'>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Thêm Xe</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoXe'>Số Xe:</label>");
        out.println("  <input type='text' id='SoXe' name='SoXe' required><br>");
        
        out.println("  <label for='MaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");
        
        out.println("  <label for='HieuXe'>Hiệu Xe:</label>");
        out.println("  <input type='text' id='HieuXe' name='HieuXe' required><br>");
        
        out.println("  <label for='SoSuon'>Số Sườn:</label>");
        out.println("  <input type='text' id='SoSuon' name='SoSuon' required><br>");
        
        out.println("  <label for='SoMay'>Số Máy:</label>");
        out.println("  <input type='text' id='SoMay' name='SoMay' required><br>");
        
        out.println("  <label for='NgayMua'>Ngày Mua:</label>");
        out.println("  <input type='text' id='NgayMua' name='NgayMua' required><br>");
        
        out.println("  <label for='GiaMua'>Giá Mua:</label>");
        out.println("  <input type='text' id='GiaMua' name='GiaMua' required><br>");
        out.println("  <input type='submit' value='Thêm Xe'>");
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

        String searchSoXe = req.getParameter("searchSoXe");
        
        if (searchSoXe == null) {
            in.displayData(out);
        } else {
            in.TK(req, res, out);
        }

        in.xoa(req, res, out);
        
        in.nhap(req, res);
         
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            
        } 
    }

