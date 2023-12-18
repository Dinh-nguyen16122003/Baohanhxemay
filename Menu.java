package controler;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class Menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='XemKhach' style='text-decoration: none; color: #333;'>Khách Hàng</a>");
        out.println("<a href='XemPT' style='text-decoration: none; color: #333;'>Phu Tung</a>");
        out.println("<a href='XemXe' style='text-decoration: none; color: #333;'>Xe</a>");
        out.println("<a href='XemHD' style='text-decoration: none; color: #333;'>Hoa Don</a>");
        out.println("<a href='XemCTHD' style='text-decoration: none; color: #333;'>Chi Tiet Hoa Don </a>");
        
        out.println("<a href='index.jsp' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");

    }
}