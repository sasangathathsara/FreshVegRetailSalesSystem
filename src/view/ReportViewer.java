/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.DBConnection;

/**
 *
 * @author Achintha
 */
public class ReportViewer {
    public static void showSalesReport() {
        try {

            Connection con = DBConnection.getConnection();

            InputStream reportStream =
                    ReportViewer.class.getResourceAsStream(
                            "/reports/SalesReport.jasper"
                    );

            if (reportStream == null) {

                throw new Exception(
                        "SalesReport.jasper not found in reports folder."
                );
            }

            JasperPrint print =
                    JasperFillManager.fillReport(
                            reportStream,
                            new HashMap<>(),
                            con
                    );

            JasperViewer.viewReport(print, false);

        } catch (Exception e) {

            e.printStackTrace();

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Unable to generate Sales Report:\n"
                    + e.getMessage(),
                    "Report Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
