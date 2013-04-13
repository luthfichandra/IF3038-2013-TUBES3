/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import ConnectDB.ConnectDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LCF
 */
public class Task extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("aksi").equals("lihat_list_task")){
            try {
                showTaskList(request.getParameter("idKategori"), request.getParameter("uid"), response);
            } catch (SQLException ex) {
                Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void showTaskList(String idKategori, String userID, HttpServletResponse response) throws IOException, ServletException, SQLException {
        PrintWriter out = response.getWriter();
        out.println("<div id='task_header'>");
        out.println("Tasks");
        out.println("</div>");

        out.println("<div class='aksi_task' onclick=\"location.href='newtask.html'\">");
        out.println("<p>Tambah Task...</p>");
        out.println("</div>");

        String query = "SELECT idtugas, nama, deadline, status_selesai FROM tugas, accounts_has_tugas WHERE kategori_idkategori = " + idKategori + " AND tugas_idtugas = idtugas AND accounts_idaccounts = " + userID;
        String[][] hasil = ConnectDB.getHasilQuery(query);

        for (int i = 0; i < hasil.length; i++) {
            out.println("<div class='task_block'>");
            String query2 = "SELECT * FROM accounts_has_tugas WHERE accounts_idaccounts = " + userID + " AND pembuat = 1";
            if (ConnectDB.getHasilQuery(query2).length > 0) {
                out.println("<div class='tombol_hapus_task'>");
                out.println("X");
                out.println("</div>");
            }
            
            out.println("<div class='task_judul'>");
            out.println("<a href='lihattask.jsp?id="+hasil[i][0]+"'>");
            out.println(hasil[i][1]);
            out.println("</a>");
            out.println("</div>");
            
            out.println("<div class='task_deadline'>");
            out.println(hasil[i][2]);
            out.println("</div>");
            
            String query_tag = "SELECT DISTINCT nama FROM tag, tugas_has_tag WHERE tugas_idtugas = "+hasil[i][0];
            String[][] result_tag = ConnectDB.getHasilQuery(query_tag);
            out.println("<div class='task_tag'>");
            out.println("Tags: ");
            for(int j = 0; j < result_tag.length; j++){
                out.println(result_tag[j][0]+"; ");
            }
            out.println("</div>");
            
            //status selesai
            out.println("<div class='task_status'>");
            if(hasil[i][3].equals("0")){
                out.println("Belum Selesai");
            }
            else{
                out.println("Selesai");
            }
            out.println("</div>");
            
            out.println("</div>");
        }
        
        if(hasil.length > 0){
            out.println("<div class='aksi_task'>");
            out.println("<p>Hapus Task...</p>");
            out.println("</div>");
        }
    }
}
