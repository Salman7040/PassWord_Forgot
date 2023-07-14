
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/MyPassWordServlet"})
public class MyPassWordServlet extends HttpServlet {

    Connection c1;
    Statement st;
    PrintWriter prt;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?zeroDateTimeBehavior="
                    + "CONVERT_TO_NULL", "root", "root");
            st = c1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (Exception e) {
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        prt = res.getWriter();
        String str1, str2, F_S;

        str1 = req.getParameter("t1");
        str2 = req.getParameter("t2");
        F_S = req.getParameter("pass");

        if (F_S.equals("save") == true) {
            try {
                ResultSet r1 = st.executeQuery("select * from pass_table");
//                r1.last();
//                int c1 = r1.getRow();                
//                r1.beforeFirst();
                String s1 = "";
                while (r1.next()) {
                    s1 = r1.getString(1);
                }

                if (s1.equals(str1) == true) {
                    prt.print("<style> body{"
                            + "                   padding:230px 0 0 0;"
                            + "                   text-align: center;"
                            + "                   color: rgb(50, 151, 50);"
                            + " font-size: 20px;"
                            + " text-shadow: 3px 4px 5px gray;}</style>"
                            + "<h1>Your Is Password  Right......</h1>");
                } else {
                    res.sendRedirect("wrong.html");
                }

            }//big if 
            catch (Exception e) {
                prt.print(e);
            }
        } else if (F_S.equals("forgot") == true) {
            try {
                ResultSet r2 = st.executeQuery("select * from pass_table");
                r2.last();
                int c2 = 0;
                c2 = r2.getRow();
                r2.beforeFirst();

                String s2 = "";
                while (r2.next()) {
                    s2 = r2.getString(1);
                }

                if (s2.equals(str2)) {
                    prt.print(" <style> body{"
                            + "         padding:230px 0 0 0;"
                            + "         text-align: center;"
                            + "         color: red;"
                            + " font-size: 20px;"
                            + " text-shadow: 3px 4px 5px gray;}</style>"
                            + "<h1>Password Already Exist......</h1>");
                } else {

                    if (c2 >= 0) {
                        if (c2 >= 1) {
                            boolean b2 = st.execute("delete from pass_table where 1=1");
                            if (b2 == false) {
                                prt.print(" <style> body{"
                                        + "         padding:230px 0 0 0;"
                                        + "         text-align: center;"
                                        + " font-size: 20px;"
                                        + "         color: rgb(50, 151, 50);"
                                        + " text-shadow: 3px 4px 5px gray;}</style>"
                                        + "      <h1>Password  Update SuccessFully.....</h1>");
                            }
                        }

                        String My_Data = "insert into pass_table values('" + str2 + "')";
                        st.execute(My_Data);
                    }
                }

            } catch (Exception e) {
                prt.print(e);
            }
        }

    }

    @Override
    public void destroy() {
        try {
            c1.close();
            st.close();

        } catch (Exception e) {
        }

    }
}
