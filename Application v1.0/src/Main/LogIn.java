/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static Main.Main.userLabel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author USER
 */

/*
-=HCI=-
My login background page has a very light shade of yellow. 
I used a larger text font for the header and made it bold so it is easier for the user to identify what page they are on.
The user is asked to type in their username and password in order to proceed to the next page. 
If the user does not have a registered username and password they can easily sign up by clicking ‘Register’ that can be found below the password text field. 
I implemented thee buttons:
-	‘Clear’ button: Removes all text from the username and password text fields.
-	‘Exit’ button: Exits the application if the user want to stop it.
-	‘Log In’ button: If the user has an existing username and password and their typed ones match the ones in the app’s database, a new Frame will appear with the main page content.
I created a pop up message in case a user enters incorrect username or password. (For security reasons the app does not specify which part is incorrect)
If the username and password is accepted, user log activity will register time and date when they logged in. 
Each button has a pop up message

-=About Security=-

The Username and Password is compared with an encrypted file.
If the username and password match with the information on the encrypted file, the user gets granted access to the main page.
Only registered users can access the main page.
User’s details are loaded from the encryption.
To check if the user details exist in the database, the encrypted file is decrypted in background and compared with the username and password typed in by the user.
 */
public class LogIn extends javax.swing.JFrame {

    private static SecretKey key64 = new SecretKeySpec(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07}, "Blowfish");
    //Creates Users ArrayList
    protected static ArrayList<Users> usersArray = new ArrayList();
    static String hello;

    /**
     * Creates new form LogReg
     */
    public LogIn() {
        //Reads the users.txt file

        File file = new File("users.txt");
        if (file.exists() && file.canRead()) {
            try {
                //decryption
                decrypt();
                               
                //Error handling 
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | java.security.InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | ClassNotFoundException ex) {
                //Print the error
                System.out.println(ex);
            }
        }
        initComponents();
    }

    //Decryption system
    public static void decrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, java.security.InvalidKeyException, IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key64);
        CipherInputStream cis = new CipherInputStream(new FileInputStream("users.txt"), cipher);
        ObjectInputStream ois = new ObjectInputStream(cis);
        SealedObject sealedObject = (SealedObject) ois.readObject();
        usersArray = (ArrayList<Users>) sealedObject.getObject(cipher);
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLog = new javax.swing.JPanel();
        logUsernameTF = new javax.swing.JTextField();
        logUsernameLabel = new javax.swing.JLabel();
        logPassLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        logLogButton = new javax.swing.JButton();
        logClearButton = new javax.swing.JButton();
        logExitButton = new javax.swing.JButton();
        logRegLabel = new javax.swing.JLabel();
        logPassTF = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Application v0.1");
        setResizable(false);

        panelLog.setBackground(new java.awt.Color(255, 255, 204));
        panelLog.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLogMouseClicked(evt);
            }
        });

        logUsernameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                logUsernameTFFocusGained(evt);
            }
        });
        logUsernameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logUsernameTFActionPerformed(evt);
            }
        });

        logUsernameLabel.setText("Username:");

        logPassLabel.setText("Password:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Log In");

        logLogButton.setText("Log In");
        logLogButton.setToolTipText("Click to Log In");
        logLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logLogButtonActionPerformed(evt);
            }
        });

        logClearButton.setText("Clear");
        logClearButton.setToolTipText("Click to clear the text");
        logClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logClearButtonActionPerformed(evt);
            }
        });

        logExitButton.setText("EXIT");
        logExitButton.setToolTipText("Click to Exit the program!");
        logExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logExitButtonActionPerformed(evt);
            }
        });

        logRegLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        logRegLabel.setText("Register");
        logRegLabel.setToolTipText("Click to register!");
        logRegLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logRegLabelMouseClicked(evt);
            }
        });

        jLabel4.setText("Not a member?");

        javax.swing.GroupLayout panelLogLayout = new javax.swing.GroupLayout(panelLog);
        panelLog.setLayout(panelLogLayout);
        panelLogLayout.setHorizontalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLogLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(143, 143, 143))
            .addGroup(panelLogLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLogLayout.createSequentialGroup()
                        .addComponent(logLogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logRegLabel)
                    .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLogLayout.createSequentialGroup()
                            .addComponent(logUsernameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(logUsernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelLogLayout.createSequentialGroup()
                            .addComponent(logPassLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(logPassTF, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(111, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logExitButton)
                .addContainerGap())
        );
        panelLogLayout.setVerticalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logUsernameLabel)
                    .addComponent(logUsernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logPassLabel)
                    .addComponent(logPassTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logRegLabel)
                .addGap(18, 18, 18)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logLogButton)
                    .addComponent(logClearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(logExitButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logRegLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logRegLabelMouseClicked
        //Goes to sign up page after pressing the button
        SignUp second = new SignUp();
        second.setVisible(true);
        dispose();
    }//GEN-LAST:event_logRegLabelMouseClicked

    private void logExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logExitButtonActionPerformed
        //Exits the problem
        System.exit(0);
    }//GEN-LAST:event_logExitButtonActionPerformed

    private void logClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logClearButtonActionPerformed
        //Clears the textfiels
        logUsernameTF.setText("");
        logPassTF.setText("");
        System.out.println("");
        
        
        try(BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
    StringBuilder sb = new StringBuilder();
    String line = br.readLine();

    while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
    }
    String everything = sb.toString();
            System.out.println(everything);
            System.out.println(usersArray);
}       catch (IOException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logClearButtonActionPerformed

    private void logLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logLogButtonActionPerformed
        //Stores value
        String username = logUsernameTF.getText();
        String password = logPassTF.getText();

        //Initialising the boolean for file checking
        boolean found = false;

        //foor loop for userArray
        for (int i = 0; i < usersArray.size(); i++) {

            //checks if username exists proced to next operation
            if (username.equals(usersArray.get(i).getUsername()) && password.equals(usersArray.get(i).getPassword())) {
                //If username and password is correct then it will go to main page
                Main mainpage = null;
                mainpage = new Main();
                mainpage.setVisible(true);
                //Exits from login page to main page
                this.dispose();
                //sets text in main page user part
                userLabel.setText(username);
                found = true;
                break;
            }

        }
        if (!found) {
            logUsernameTF.setText("");
            logPassTF.setText("");
            JOptionPane.showMessageDialog(null, "Username or Password is Incorrect!");

        }
        logFile(found);
    }
    //Stores data of logins whether succeed or fail to the file 

    public void logFile(boolean found) {

        String username = logUsernameTF.getText();
        FileWriter output = null;
        try {
            output = new FileWriter("userslog.txt", true);
        } catch (IOException ex) {
            System.out.println("Error encountered " + ex.toString());
        }

        try {

            Date dNow = new Date();
            //Creates Date Object
            SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

            //IF the file is found it writes inside user activities.
            if (found) {
                output.write("-----------------------------------------------------------------------------------------------------" + "\r\n");
                output.write(ft.format(dNow) + " " + "User:  " + " [ " + username + " ] " + " + " + "successfully logged in!" + "\r\n");
            }
            output.close();
            //Catch Exception 
        } catch (Exception e) {
            System.out.println("Error found: " + e.toString());

        }
    }//GEN-LAST:event_logLogButtonActionPerformed

    private void logUsernameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logUsernameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logUsernameTFActionPerformed

    private void panelLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogMouseClicked

    }//GEN-LAST:event_panelLogMouseClicked

    private void logUsernameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_logUsernameTFFocusGained

    }//GEN-LAST:event_logUsernameTFFocusGained

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
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton logClearButton;
    private javax.swing.JButton logExitButton;
    private javax.swing.JButton logLogButton;
    private javax.swing.JLabel logPassLabel;
    private javax.swing.JPasswordField logPassTF;
    private javax.swing.JLabel logRegLabel;
    private javax.swing.JLabel logUsernameLabel;
    public static javax.swing.JTextField logUsernameTF;
    private javax.swing.JPanel panelLog;
    // End of variables declaration//GEN-END:variables
}
