/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package freshvegretailsalessystem;

/**
 *
 * @author Achintha
 */
public class FreshVegRetailSalesSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        javax.swing.SwingUtilities.invokeLater(() -> {

            view.DashboardFrame dashboard =
                    new view.DashboardFrame();

            dashboard.setLocationRelativeTo(null);
            dashboard.setVisible(true);
        });
    }
    
}
