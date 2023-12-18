

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controler.HoaDon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemHD")
public class XemHD extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemHD() {
        super();
    }
    HoaDon in = new HoaDon();
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
        
        out.println("<h2> Hoá Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchSoHoaDon'>Tìm Kiếm Số Hoá Đơn:</label>");
        out.println("  <input type='text' id='searchSoHoaDon' name='searchSoHoaDon'>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        out.println("<h2> Hoá Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoHoaDon'>Số Hoá Đơn:</label>");
        out.println("  <input type='text' id='SoHoaDon' name='SoHoaDon' required><br>");
       
        out.println("  <label for='SoXe'>So Xe:</label>");
        out.println("  <input type='text' id='SoXe' name='SoXe' required><br>");
        out.println("  <label for='NgayLapHoaDon'>NgayLapHoaDon:</label>");
        out.println("  <input type='text' id='NgayLapHoaDon' name='NgayLapHoaDon' required><br>");
        out.println("  <input type='submit' value='Thêm Hoá Đơn'>");
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
        String searchSoHoaDon = req.getParameter("searchSoHoaDon");
        
        if (searchSoHoaDon == null) {
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