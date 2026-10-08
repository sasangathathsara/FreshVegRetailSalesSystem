/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.DashboardDAO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Achintha
 */
public class SalesChartPanel extends JPanel {
    private Map<String, Double> salesData;

    public SalesChartPanel() {
        DashboardDAO dao = new DashboardDAO();
        salesData = dao.getLast7DaysSales();

        System.out.println("Sales Data: " + salesData);

        setBackground(java.awt.Color.WHITE);
        setPreferredSize(new java.awt.Dimension(570, 230));
        setMinimumSize(new java.awt.Dimension(570, 230));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        
/*
        g.setColor(java.awt.Color.RED);
        g.fillRect(20, 20, 200, 100);   
        
        g.setColor(java.awt.Color.BLUE);
        g.fillRect(250, 20, 200, 100);
*/

        System.out.println("SalesChartPanel paintComponent called");

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        int left = 60;
        int right = 20;
        int top = 55;
        int bottom = 45;

        int chartWidth = width - left - right;
        int chartHeight = height - top - bottom;

        // Title
        g2.setFont(new java.awt.Font(
            "Segoe UI",
            java.awt.Font.BOLD,
            14
        ));

        g2.setColor(new java.awt.Color(20, 120, 70));

        g2.drawString(
            "Last 7 Days Sales",
            left,
            18
        );

        if (salesData.isEmpty()) {

            g2.setFont(new java.awt.Font(
                "Segoe UI",
                java.awt.Font.PLAIN,
                14
            ));

            g2.setColor(java.awt.Color.GRAY);

            g2.drawString(
                "No sales data available",
                left + 120,
                top + 100
            );

            g2.dispose();
            return;
        }

        double maxSales = 0;

        for (double value : salesData.values()) {

            if (value > maxSales) {
                maxSales = value;
            }
        }

        if (maxSales == 0) {
            maxSales = 1;
        }

        // Axes
        g2.setColor(new java.awt.Color(180, 180, 180));

        g2.drawLine(
            left,
            top,
            left,
            top + chartHeight
        );

        g2.drawLine(
            left,
            top + chartHeight,
            left + chartWidth,
            top + chartHeight
        );

        int count = salesData.size();

        int index = 0;

        for (Map.Entry<String, Double> entry : salesData.entrySet()) {

            double sales = entry.getValue();

            int x = left
                    + (index * chartWidth / Math.max(count - 1, 1));

            int y = top
                    + chartHeight
                    - (int) ((sales / maxSales) * chartHeight);

            // Bar
            g2.setColor(new java.awt.Color(34, 170, 90));

            int barWidth = 35;

            g2.fillRect(
                x - barWidth / 2,
                y,
                barWidth,
                top + chartHeight - y
            );

            // Amount
            g2.setColor(new java.awt.Color(80, 80, 80));

            g2.setFont(new java.awt.Font(
                "Segoe UI",
                java.awt.Font.PLAIN,
                10
            ));

            g2.drawString(
                String.format("%.0f", sales),
                x - 15,
                y - 5
            );

            // Date
            String date = entry.getKey();

            if (date.length() >= 10) {
                date = date.substring(5);
            }

            g2.drawString(
                date,
                x - 15,
                top + chartHeight + 20
            );

            index++;
        }

        g2.dispose();
    }  
}
