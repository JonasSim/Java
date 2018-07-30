/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.table.*;
import java.lang.String;
import java.text.SimpleDateFormat;

/**
 *
 * @author w1565407
 */
/*
About HCI 
On my main page I used a soft shade of blue because even after working long hours on the application, my eyes are still not tired of the blue. After loading the main page the user will see a tale full of information. A section of calculation, search and logs together with disconnect buttons.
I implemented 5 buttons:
-	‘Search’: To search for the values of what was typed in the search bar. The table is updated with entered value (like Monday) after the ‘search’ button is pressed. 
-	‘Logs’: Opens a new frame with user log activities registered while the user is interacting with the application. 
-	‘Disconnect’: The User can disconnect their profile from the main page (it will switch to ‘log in’ page).
-	‘Calculate Correlation’: Information will appear with calculated Driver Age and Number of Casualties columns.
-	‘Correlation Graph’: New frame will appear will calculations and a sketched graph. 
There are two text field areas on the main page:
A ‘Search’ text field where the user can type a value that they prefer to see in the table.
If the user types a non-existing value in search area the table will come up empty.
The ‘Correlation Result’ text field where the calculated result is generated.
On the top left corner of the application the user can see what username is used to log in. 
The User can look for the information inside the table by scrolling up and down or using the left and right scroll panels. 
Columns with names are expandable for more hidden information.


-=About Security=-
ONLY registered users can access the main page content and the user log activities!
*/
public class Main extends javax.swing.JFrame {

    //Search
    private TableRowSorter<TableModel> rowSorter;
    public static ArrayList<Double> driverAge;
    public static ArrayList<Double> numCas;  
    
    
String newline = System.getProperty("line.separator");

        //method for CSV source: http://stackoverflow.com/questions/22864095/reading-data-from-a-specific-csv-file-and-displaying-it-in-a-jtable
        // Method for reading CSV file
       class MyModel extends AbstractTableModel {
         
            private final String[] columnNames = { "Reference", "London Borough Name",
            "Severity", "Vehicle Reference", "Vehicle Type", "Vehicle Type Banded", "Vehicle Manoeuvres",
            "Vehicle Skidding", "Restricted Lane", "Junction Location", "Object In Carriage Way", "Vehicle Leaving Carriage Way",
            "Vehicle off Carriage Way", "Vehicle Impact", "Driver Sex", "Driver Age", "Driver Age Band",
            "Number Of Casualties", "Date", "Day", "Time", "Highway", "Road Class",
            "Road Number 1", "Road Type", "Speed Limit"};
            
        private ArrayList<String[]> Data = new ArrayList<String[]>();

        public void AddCSVData(ArrayList<String[]> DataIn) {
            this.Data = DataIn;
            this.fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;// length;
        }

        @Override
        public int getRowCount() {
            return Data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return Data.get(row)[col];
        }
    }
    public class CSVFile {
        private final ArrayList<String[]> Rs = new ArrayList<String[]>();
        private String[] OneRow;

        public ArrayList<String[]> ReadCSVfile(File DataFile) {
            try {
                BufferedReader brd = new BufferedReader(new FileReader(DataFile));
                while (brd.ready()) {
                    String st = brd.readLine();
                    OneRow = st.split(",");
                    Rs.add(OneRow);
                    System.out.println(Arrays.toString(OneRow));
                   
                } // end of while
            } // end of try
            catch (Exception e) {
                String errmsg = e.getMessage();
                System.out.println("File not found:" + errmsg);
            } // end of Catch
            return Rs;
        }// end of ReadFile method
    }// end of CSVFile class
    
        public Main() {
            
        initComponents();       
        try{ 
        CSVFile Rd = new CSVFile();
        MyModel NewModel = new MyModel();
        this.table.setModel(NewModel);
        File DataFile = new File("1717_Road_Collision_Vehicles_In_Camden.csv");
        ArrayList<String[]> Rs2 = Rd.ReadCSVfile(DataFile);
        Rs2.remove(0);
        NewModel.AddCSVData(Rs2);
        System.out.println("Rows: " + NewModel.getRowCount());
        System.out.println("Cols: " + NewModel.getColumnCount());
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        }catch(ArrayIndexOutOfBoundsException  err){
            System.out.println("Table is incorrect");
            System.out.println(err);
        }catch(IndexOutOfBoundsException e){
            System.out.println("File does not exists!");
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        searchPanel = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        mainSearchTf = new javax.swing.JTextField();
        mainDisconnect = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        logButton = new javax.swing.JButton();
        resultPanel = new javax.swing.JPanel();
        resultLogo = new javax.swing.JLabel();
        correlationResultLbl = new javax.swing.JLabel();
        correlationResultTf = new javax.swing.JTextField();
        correlationButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Application v1.0");
        setResizable(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Schooosdfdf", "Title asdfasdf", "Title 3asdfasd", "Title 4asdfasdf", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16"
            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(table);

        searchPanel.setBackground(new java.awt.Color(153, 204, 255));
        searchPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        mainSearchTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainSearchTfActionPerformed(evt);
            }
        });

        mainDisconnect.setText("Disconnect");
        mainDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainDisconnectActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Logged as:");

        userLabel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        userLabel.setText("user");

        logButton.setText("Logs");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanelLayout.createSequentialGroup()
                        .addComponent(mainSearchTf, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainDisconnect))
                    .addGroup(searchPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainSearchTf, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton)
                    .addComponent(mainDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logButton))
                .addContainerGap())
        );

        resultPanel.setBackground(new java.awt.Color(153, 204, 255));
        resultPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        resultLogo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resultLogo.setForeground(new java.awt.Color(102, 102, 102));
        resultLogo.setText("Result");

        correlationResultLbl.setText("Correlation Result");

        correlationResultTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correlationResultTfActionPerformed(evt);
            }
        });

        correlationButton.setText("Calculate Correlation");
        correlationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correlationButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Correlation Graph");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addGroup(resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resultPanelLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(correlationResultLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(correlationResultTf, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(correlationButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(resultPanelLayout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(resultLogo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultLogo)
                .addGap(28, 28, 28)
                .addGroup(resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(correlationResultLbl)
                    .addComponent(correlationResultTf, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(correlationButton)
                    .addComponent(jButton1))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
         
        String text = mainSearchTf.getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
                BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String data = mainSearchTf.getText();
                        String user = userLabel.getText();
                                             
			File file = new File("userslog.txt");
                        
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                        bw.write("User:  " + " [ " + user + " ] " + "  Searched for:  " + " [ " +data + " ] " + "  At  " + ft.format(dNow) + "\r\n");

                        
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

    }//GEN-LAST:event_searchButtonActionPerformed

    private void mainDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainDisconnectActionPerformed
            LogIn logIn = new LogIn();
            logIn.setVisible(true);
            dispose();
    }//GEN-LAST:event_mainDisconnectActionPerformed

    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        UserLogs logs = new UserLogs();
        logs.setVisible(true);
        File inputFile = new File("userslog.txt");
        String answer = "";
        try {
            Scanner input = new Scanner(inputFile);
            while (input.hasNextLine()) {
                answer += input.nextLine() + "\n";

            }
            logs.logText.setText(answer);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist!");
        }
    }//GEN-LAST:event_logButtonActionPerformed

    private void mainSearchTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainSearchTfActionPerformed
        
    }//GEN-LAST:event_mainSearchTfActionPerformed

    private void correlationResultTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correlationResultTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correlationResultTfActionPerformed

    private void correlationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correlationButtonActionPerformed
       
        driverAge = new ArrayList<Double>();
        numCas = new ArrayList<Double>();
        try{
        for (int count = 0; count < table.getRowCount(); count++) {
            driverAge.add(Double.valueOf((String) table.getValueAt(count, 15)));
            numCas.add(Double.valueOf((String) table.getValueAt(count, 17)));
            correlationResultTf.setText(correlation(driverAge, numCas) + "");
        }}catch(NumberFormatException e){
            System.out.println("Wrong data!");
           }
    }//GEN-LAST:event_correlationButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Graph graph = new Graph();
        graph.setVisible(true);
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new Main().setVisible(true);   // Try to fix this part!
            }
        });
    }
    
    
     public static double correlation(ArrayList<Double> driverAge, ArrayList<Double> numCas) {

        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = driverAge.size();

        for (int i = 0; i < n; ++i) {
            double x = driverAge.get(i);
            double y = numCas.get(i);

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmax = (sxx / n - sx * sx / n / n);
        // standard error of y
        double sigmay = (syy / n - sy * sy / n / n);

        // correlation is just a normalized covariation
        return cov / Math.sqrt(sigmax * sigmay);

    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton correlationButton;
    private javax.swing.JLabel correlationResultLbl;
    private javax.swing.JTextField correlationResultTf;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logButton;
    private javax.swing.JButton mainDisconnect;
    private javax.swing.JTextField mainSearchTf;
    private javax.swing.JLabel resultLogo;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTable table;
    public static javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
