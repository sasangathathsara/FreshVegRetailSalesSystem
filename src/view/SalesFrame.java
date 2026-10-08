/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.ProductDAO;
import model.Product;
import dao.CustomerDAO;
import model.Customer;

import model.Sale;
import model.SaleDetail;
import model.CustomerPayment;
import service.SalesService;

import view.ReportViewer;

import java.util.List;


/**
 *
 * @author Achintha
 */
public class SalesFrame extends javax.swing.JFrame {
    
    private List<Product> productList;
    private List<Customer> customerList;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalesFrame.class.getName());

    /**
     * Creates new form SalesFrame
     */
    public SalesFrame() {
        initComponents();
        loadProducts();
        loadCustomers();
        
        cmbProduct.addActionListener(e -> productSelected());
        
        txtPaidAmount.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtPaidAmount.getText().equals("0.00")) {
                    txtPaidAmount.selectAll();
                }
            }
        });

        txtPaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculateOutstanding();
            }
        });
        
        txtOutstanding.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtOutstanding.getText().equals("0.00")) {
                    txtOutstanding.selectAll();
                }
            }
        });        
   
        txtBalance.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtBalance.getText().equals("0.00")) {
                    txtBalance.selectAll();
                }
            }
        });
        
        txtQtyKg.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtQtyKg.getText().equals("0")) {
                    txtQtyKg.selectAll();
                }
            }
        });


        txtQtyG.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtQtyG.getText().equals("0")) {
                    txtQtyG.selectAll();
                }
            }
        });


        txtQtyPcs.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtQtyPcs.getText().equals("0")) {
                    txtQtyPcs.selectAll();
                }
            }
        });
       
    }

    private void loadProducts() {

        try {

            ProductDAO productDAO = new ProductDAO();

            productList = productDAO.getAllActiveProducts();

            cmbProduct.removeAllItems();
            cmbProduct.addItem("Select Product");

            for (Product product : productList) {

                cmbProduct.addItem(product.getProductName());

            }

        } 
        catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to load products.\n" + e.getMessage(),
                    "Product Loading Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

        }
    }
    
    private void loadCustomers() {

        try {

            CustomerDAO customerDAO = new CustomerDAO();

            customerList = customerDAO.getAllActiveCustomers();

            cmbCustomer.removeAllItems();
            cmbCustomer.addItem("Select Customer");

            for (Customer customer : customerList) {

                cmbCustomer.addItem(customer.getCustomerName());

            }

        }
        catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to load customers.\n" + e.getMessage(),
                    "Customer Loading Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private int getSelectedCustomerId() {

        int selectedIndex = cmbCustomer.getSelectedIndex();

        if (selectedIndex <= 0) {
            return 0;
        }

        return customerList.get(selectedIndex - 1).getCustomerId();
    }
    
    private String generateInvoiceNumber() {

        try {

            dao.SaleDAO saleDAO = new dao.SaleDAO();

            int nextNumber = saleDAO.getNextInvoiceNumber();

            return String.format("INV-%06d", nextNumber);

        } 
        catch (Exception e) {

            throw new RuntimeException(
                    "Unable to generate invoice number.",
                    e
            );
        }
    }
    
    private java.util.List<SaleDetail> getSaleDetailsFromTable() {

        java.util.List<SaleDetail> details =
                new java.util.ArrayList<>();

        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) tblSaleItems.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {

            String productName = model.getValueAt(i, 1).toString();
            String unit = model.getValueAt(i, 2).toString();

            double quantity =
                    Double.parseDouble(model.getValueAt(i, 3).toString());

            double unitPrice =
                    Double.parseDouble(model.getValueAt(i, 4).toString());

            double lineTotal =
                    Double.parseDouble(model.getValueAt(i, 6).toString());

            int productId = 0;

            for (Product product : productList) {

                if (product.getProductName().equals(productName)) {

                    productId = product.getProductId();
                    break;
                }
            }

            SaleDetail detail = new SaleDetail();

            detail.setProductId(productId);
            detail.setUnit(unit);
            detail.setQuantity(quantity);
            detail.setUnitPrice(unitPrice);
            detail.setLineTotal(lineTotal);

            details.add(detail);
        }

        return details;
    }
    
    private void productSelected() {

        int index = cmbProduct.getSelectedIndex();

        if (index <= 0) {

            txtUnit.setText("-");
            txtUnitPrice.setText("0.00");

            txtQtyKg.setEnabled(false);
            txtQtyG.setEnabled(false);
            txtQtyPcs.setEnabled(false);

            return;
        }

        Product product = productList.get(index - 1);

        txtUnit.setText(product.getUnit());
        txtUnitPrice.setText(
                String.format("%.2f", product.getSellingPrice())
        );

        if ("KG".equals(product.getUnit())) {

            // Weight product
            txtQtyKg.setEnabled(true);
            txtQtyG.setEnabled(true);

            // Piece quantity disable
            txtQtyPcs.setEnabled(false);

            txtQtyPcs.setText("0");

        } 
        else if ("PCS".equals(product.getUnit())) {

            // Piece product
            txtQtyPcs.setEnabled(true);

            // Weight quantity disable
            txtQtyKg.setEnabled(false);
            txtQtyG.setEnabled(false);

            txtQtyKg.setText("0");
            txtQtyG.setText("0");
        }
    }
    
    private void calculateSubtotal() {

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblSaleItems.getModel();

        double subtotal = 0.00;

        for (int i = 0; i < model.getRowCount(); i++) {

            Object amountValue = model.getValueAt(i, 6);

            if (amountValue != null) {
                subtotal += Double.parseDouble(amountValue.toString());
            }
        }

        txtSubtotal.setText(String.format("%.2f", subtotal));

        // Get discount
        double discount = 0.00;

        try {
            discount = Double.parseDouble(txtDiscount.getText().trim());
        } 
        catch (NumberFormatException e) {
            discount = 0.00;
        }

        // Calculate net amount
        double netAmount = subtotal - discount;

        if (netAmount < 0) {
            netAmount = 0.00;
        }

        txtNetAmount.setText(String.format("%.2f", netAmount));
    }
    
    private void calculateOutstanding() {

        double netAmount = 0.00;
        double paidAmount = 0.00;

        try {
            netAmount = Double.parseDouble(txtNetAmount.getText().trim());
        } 
        catch (NumberFormatException e) {
            netAmount = 0.00;
        }

        try {
            paidAmount = Double.parseDouble(txtPaidAmount.getText().trim());
        } 
        catch (NumberFormatException e) {
            paidAmount = 0.00;
        }

        // Paid amount cannot be negative
        if (paidAmount < 0) {
            paidAmount = 0.00;
            txtPaidAmount.setText("0.00");
        }

        // Calculate outstanding
        double outstanding = netAmount - paidAmount;

        if (outstanding < 0) {
            outstanding = 0.00;
        }

        txtOutstanding.setText(
                String.format("%.2f", outstanding)
        );
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblCustomer = new javax.swing.JLabel();
        cmbCustomer = new javax.swing.JComboBox<>();
        lblInvoiceNo = new javax.swing.JLabel();
        txtInvoiceNo = new javax.swing.JTextField();
        lblSaleDate = new javax.swing.JLabel();
        txtSaleDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        pnlProductEntry = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbProduct = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtUnit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQtyKg = new javax.swing.JTextField();
        lblKg = new javax.swing.JLabel();
        txtQtyG = new javax.swing.JTextField();
        lblGram = new javax.swing.JLabel();
        lblUnitPrice = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        txtQtyPcs = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnAddItem = new javax.swing.JButton();
        pnlSaleItems = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSaleItems = new javax.swing.JTable();
        pnlSummary = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        lblDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        lblNetAmount = new javax.swing.JLabel();
        txtNetAmount = new javax.swing.JTextField();
        pnlPayment = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblPaidAmount = new javax.swing.JLabel();
        lblOutstanding = new javax.swing.JLabel();
        lblPaymentMethod = new javax.swing.JLabel();
        txtPaidAmount = new javax.swing.JTextField();
        txtOutstanding = new javax.swing.JTextField();
        cmbPaymentMethod = new javax.swing.JComboBox<>();
        lblBalance = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        pnlButtons = new javax.swing.JPanel();
        btnSaveSale = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fresh Fruit & Vegetable Sales");

        lblCustomer.setText("Customer");
        lblCustomer.setToolTipText("");

        cmbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Cusromer" }));

        lblInvoiceNo.setText("Invoice No");

        txtInvoiceNo.setEditable(false);
        txtInvoiceNo.setText("INV-000001");

        lblSaleDate.setText("Sale Date");

        txtSaleDate.setEditable(false);
        txtSaleDate.setText("2026-10-07");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Customer & Invoice Information");

        jButton1.setText("View Sales Report");
        jButton1.setToolTipText("");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblInvoiceNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSaleDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSaleDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(72, 72, 72))))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomer)
                    .addComponent(lblInvoiceNo)
                    .addComponent(lblSaleDate)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaleDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Product Entry");

        jLabel3.setText("Product");

        cmbProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Product" }));

        jLabel4.setText("Unit");

        txtUnit.setEditable(false);
        txtUnit.setBackground(new java.awt.Color(255, 255, 255));
        txtUnit.setText("-");

        jLabel5.setText("Quantity");

        txtQtyKg.setText("0");

        lblKg.setText("KG");

        txtQtyG.setText("0");

        lblGram.setText("G");

        lblUnitPrice.setText("Unit Price");

        txtUnitPrice.setText("0.00");

        txtQtyPcs.setText("0");

        jLabel7.setText("PCS");

        btnAddItem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddItem.setText("+ Add Item");
        btnAddItem.addActionListener(this::btnAddItemActionPerformed);

        javax.swing.GroupLayout pnlProductEntryLayout = new javax.swing.GroupLayout(pnlProductEntry);
        pnlProductEntry.setLayout(pnlProductEntryLayout);
        pnlProductEntryLayout.setHorizontalGroup(
            pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductEntryLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddItem)
                    .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtUnitPrice)
                        .addComponent(lblUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlProductEntryLayout.createSequentialGroup()
                        .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(pnlProductEntryLayout.createSequentialGroup()
                                .addComponent(txtQtyKg, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblKg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQtyG, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGram)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQtyPcs, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(pnlProductEntryLayout.createSequentialGroup()
                        .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbProduct, 0, 120, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        pnlProductEntryLayout.setVerticalGroup(
            pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductEntryLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProductEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQtyKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKg)
                    .addComponent(txtQtyG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGram)
                    .addComponent(txtQtyPcs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(lblUnitPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Sale Items");

        tblSaleItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Product", "Unit", "Quantity", "Unit Price", "Discount", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSaleItems);

        javax.swing.GroupLayout pnlSaleItemsLayout = new javax.swing.GroupLayout(pnlSaleItems);
        pnlSaleItems.setLayout(pnlSaleItemsLayout);
        pnlSaleItemsLayout.setHorizontalGroup(
            pnlSaleItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaleItemsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSaleItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlSaleItemsLayout.setVerticalGroup(
            pnlSaleItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaleItemsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Summary");

        lblSubtotal.setText("Subtotal  ");

        txtSubtotal.setEditable(false);
        txtSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubtotal.setText("0.00");

        lblDiscount.setText("Discount");

        txtDiscount.setEditable(false);
        txtDiscount.setBackground(new java.awt.Color(255, 255, 255));
        txtDiscount.setText("0.00");

        lblNetAmount.setText("Net Amount  ");

        txtNetAmount.setEditable(false);
        txtNetAmount.setBackground(new java.awt.Color(255, 255, 255));
        txtNetAmount.setText("0.00");
        txtNetAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNetAmountKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlSummaryLayout = new javax.swing.GroupLayout(pnlSummary);
        pnlSummary.setLayout(pnlSummaryLayout);
        pnlSummaryLayout.setHorizontalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSummaryLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlSummaryLayout.createSequentialGroup()
                        .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNetAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNetAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtDiscount)
                            .addComponent(txtSubtotal))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlSummaryLayout.setVerticalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotal)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiscount)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNetAmount)
                    .addComponent(txtNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Payment");

        lblPaidAmount.setText("Paid Amount");

        lblOutstanding.setText("Outstanding");

        lblPaymentMethod.setText("Payment Method");

        txtPaidAmount.setText("0.00");
        txtPaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidAmountKeyReleased(evt);
            }
        });

        txtOutstanding.setEditable(false);
        txtOutstanding.setBackground(new java.awt.Color(255, 255, 255));
        txtOutstanding.setText("0.00");

        cmbPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CASH", "CARD", "ONLINE BANKING", "CREDIT" }));

        lblBalance.setText("Balance");

        txtBalance.setEditable(false);
        txtBalance.setText("0.00");

        javax.swing.GroupLayout pnlPaymentLayout = new javax.swing.GroupLayout(pnlPayment);
        pnlPayment.setLayout(pnlPaymentLayout);
        pnlPaymentLayout.setHorizontalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addComponent(lblPaidAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblOutstanding, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPaymentMethod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPaidAmount)
                    .addComponent(txtOutstanding)
                    .addComponent(cmbPaymentMethod, 0, 160, Short.MAX_VALUE)
                    .addComponent(txtBalance))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlPaymentLayout.setVerticalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaidAmount)
                    .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOutstanding)
                    .addComponent(txtOutstanding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBalance)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentMethod)
                    .addComponent(cmbPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnSaveSale.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSaveSale.setText("Save Sale");
        btnSaveSale.addActionListener(this::btnSaveSaleActionPerformed);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlButtonsLayout.createSequentialGroup()
                        .addComponent(btnClear)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel))
                    .addComponent(btnSaveSale, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnSaveSale, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlSummary, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlProductEntry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlSaleItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlProductEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSaleItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:
        int productIndex = cmbProduct.getSelectedIndex();

        // Product validation
        if (productIndex <= 0) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a product.",
                    "Validation",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Product product = productList.get(productIndex - 1);

        double quantity = 0;

        try {

            if ("KG".equals(product.getUnit())) {

                double kg = Double.parseDouble(txtQtyKg.getText().trim());
                double grams = Double.parseDouble(txtQtyG.getText().trim());

                if (kg < 0 || grams < 0 || grams >= 1000) {

                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid KG and grams quantity.",
                            "Invalid Quantity",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                quantity = kg + (grams / 1000.0);

            } 
            else if ("PCS".equals(product.getUnit())) {

                quantity = Double.parseDouble(txtQtyPcs.getText().trim());

                if (quantity <= 0) {

                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid PCS quantity.",
                            "Invalid Quantity",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

        } 
        catch (NumberFormatException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (quantity <= 0) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than zero.",
                    "Invalid Quantity",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double unitPrice;

        try {

            unitPrice = Double.parseDouble(txtUnitPrice.getText().trim());

        } 
        catch (NumberFormatException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Invalid unit price.",
                    "Invalid Price",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double discount = 0.00;
        double amount = (quantity * unitPrice) - discount;

        // Add row to table
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) tblSaleItems.getModel();

        int rowNumber = model.getRowCount() + 1;

        model.addRow(new Object[]{
            rowNumber,
            product.getProductName(),
            product.getUnit(),
            quantity,
            unitPrice,
            discount,
            amount
        });
        
        calculateSubtotal();
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnSaveSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSaleActionPerformed
        // TODO add your handling code here:
        try {

            // 1. Validate customer
            int customerId = getSelectedCustomerId();

            if (customerId <= 0) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Please select a customer.",
                        "Customer Required",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                cmbCustomer.requestFocus();
                return;
            }

            // 2. Validate sale items
            if (tblSaleItems.getRowCount() == 0) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Please add at least one item to the sale.",
                        "Sale Items Required",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // 3. Read amounts
            double grossAmount =
                    Double.parseDouble(txtSubtotal.getText().trim());

            double discountAmount =
                    Double.parseDouble(txtDiscount.getText().trim());

            double netAmount =
                    Double.parseDouble(txtNetAmount.getText().trim());

            double paidAmount =
                    Double.parseDouble(txtPaidAmount.getText().trim());

            // 4. Validate paid amount
            if (paidAmount < 0) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Paid amount cannot be negative.",
                        "Invalid Paid Amount",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

                txtPaidAmount.requestFocus();
                return;
            }

            // 5. Create Sale object
            Sale sale = new Sale();

            String invoiceNo = generateInvoiceNumber();
            txtInvoiceNo.setText(invoiceNo);
            sale.setInvoiceNo(invoiceNo);
            
            sale.setCustomerId(customerId);

            sale.setSaleDate(
                    java.time.LocalDate.now()
            );

            sale.setGrossAmount(grossAmount);
            sale.setDiscountAmount(discountAmount);
            sale.setNetAmount(netAmount);
            sale.setPaidAmount(paidAmount);

            sale.setNotes("");
            sale.setCreatedBy(1);

            // 6. Get sale details from table
            java.util.List<SaleDetail> details =
                    getSaleDetailsFromTable();

            // 7. Create customer payment
            CustomerPayment payment =
                    new CustomerPayment();

            payment.setPaymentDate(
                    java.time.LocalDate.now()
            );

            payment.setAmount(paidAmount);

            payment.setPaymentMethod(
                    cmbPaymentMethod.getSelectedItem().toString()
            );

            payment.setReferenceNo("");
            payment.setNotes("");
            payment.setCreatedBy(1);

            // 8. Save complete transaction
            SalesService salesService =
                    new SalesService();

            salesService.saveSale(
                    sale,
                    details,
                    payment
            );

            // 9. Success message
            double outstanding = 0.00;
            double balance = 0.00;

            if (paidAmount < netAmount) {

                outstanding = netAmount - paidAmount;

            } 
            else if (paidAmount > netAmount) {

                balance = paidAmount - netAmount;
            }

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Sale saved successfully.\n\n"
                    + "Invoice No: " + sale.getInvoiceNo()
                    + "\nNet Amount: "
                    + String.format("%.2f", netAmount)
                    + "\nPaid Amount: "
                    + String.format("%.2f", paidAmount)
                    + "\nOutstanding: "
                    + String.format("%.2f", outstanding)
                    + "\nBalance: "
                    + String.format("%.2f", balance),
                    "Sale Saved",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            System.out.println("SALE SAVED SUCCESSFULLY");
            System.out.println("Sale ID: " + sale.getSaleId());
            System.out.println("Invoice: " + sale.getInvoiceNo());

        }
        catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to save sale.\n\n"
                    + e.getMessage(),
                    "Sale Saving Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveSaleActionPerformed

    private void txtNetAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNetAmountKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetAmountKeyReleased

    private void txtPaidAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidAmountKeyReleased
        // TODO add your handling code here:
        try {

            double netAmount = Double.parseDouble(
                    txtNetAmount.getText().trim()
            );

            double paidAmount = Double.parseDouble(
                    txtPaidAmount.getText().trim()
            );

            double outstanding = 0.00;
            double balance = 0.00;

            if (paidAmount < netAmount) {

                outstanding = netAmount - paidAmount;

            } 
            else if (paidAmount > netAmount) {

                balance = paidAmount - netAmount;
            }

            txtOutstanding.setText(
                    String.format("%.2f", outstanding)
            );

            txtBalance.setText(
                    String.format("%.2f", balance)
            );

        } 
        catch (NumberFormatException e) {

            txtOutstanding.setText("0.00");
            txtBalance.setText("0.00");
        }
    }//GEN-LAST:event_txtPaidAmountKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        cmbCustomer.setSelectedIndex(0);

       cmbProduct.setSelectedIndex(0);

       txtUnit.setText("-");

       txtQtyKg.setText("0");
       txtQtyG.setText("0");
       txtQtyPcs.setText("0");

       txtQtyKg.setEnabled(false);
       txtQtyG.setEnabled(false);
       txtQtyPcs.setEnabled(false);

       txtUnitPrice.setText("0.00");

       txtDiscount.setText("0.00");

       txtPaidAmount.setText("0.00");

       txtOutstanding.setText("0.00");
       txtBalance.setText("0.00");

       txtSubtotal.setText("0.00");
       txtNetAmount.setText("0.00");

       txtInvoiceNo.setText(generateInvoiceNumber());

       txtSaleDate.setText(
               java.time.LocalDate.now().toString()
       );

       javax.swing.table.DefaultTableModel model =
               (javax.swing.table.DefaultTableModel) tblSaleItems.getModel();

       model.setRowCount(0);

       cmbCustomer.requestFocus();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ReportViewer.showSalesReport();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SalesFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSaveSale;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JComboBox<String> cmbPaymentMethod;
    private javax.swing.JComboBox<String> cmbProduct;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblGram;
    private javax.swing.JLabel lblInvoiceNo;
    private javax.swing.JLabel lblKg;
    private javax.swing.JLabel lblNetAmount;
    private javax.swing.JLabel lblOutstanding;
    private javax.swing.JLabel lblPaidAmount;
    private javax.swing.JLabel lblPaymentMethod;
    private javax.swing.JLabel lblSaleDate;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblUnitPrice;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlPayment;
    private javax.swing.JPanel pnlProductEntry;
    private javax.swing.JPanel pnlSaleItems;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JTable tblSaleItems;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtInvoiceNo;
    private javax.swing.JTextField txtNetAmount;
    private javax.swing.JTextField txtOutstanding;
    private javax.swing.JTextField txtPaidAmount;
    private javax.swing.JTextField txtQtyG;
    private javax.swing.JTextField txtQtyKg;
    private javax.swing.JTextField txtQtyPcs;
    private javax.swing.JTextField txtSaleDate;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtUnit;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}