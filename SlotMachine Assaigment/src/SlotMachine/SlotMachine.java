/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotMachine;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author w1565407
 */
public class SlotMachine extends JFrame {
    
    //Buttons
    private JButton addCoinBtn = new JButton("Add Coin");
    private JButton betOneBtn = new JButton("Bet One");
    private JButton betMaxBtn = new JButton("Bet Max");
    private JButton resetBtn = new JButton("Reset");
    private JButton spinBtn = new JButton("Spin");
    private JLabel creditLabel = new JLabel("Credit: 10");
    private JLabel betLabel = new JLabel("Credit betting with: 0    ");
    private JPanel buttonTwoPanel = new JPanel(new FlowLayout());
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private JPanel buttonThreePanel = new JPanel(new FlowLayout());
    private JPanel rightPanel = new JPanel(new GridLayout(2, 0));
    private JPanel middlePanel = new JPanel(new FlowLayout());
    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JButton staticsBtn = new JButton("Statistics");
    private JPanel topPanel = new JPanel(new GridLayout(3, 0));
    private int credit = 10;
    private int currentCredit = 0;// Stores the current credit user is betting
    private Reel reelOne = new Reel();
    private Reel reelTwo = new Reel();
    private Reel reelThree = new Reel();
    private JButton reelOneBtn = new JButton("");
    private JButton reelTwoBtn = new JButton("");
    private JButton reelThreeBtn = new JButton("");
    private ImageIcon iconOne, iconTwo, iconThree;
    private int reelOneIndex, reelTwoIndex, reelThreeIndex;
    private int wins, looses, totalCredits, numberOfGames;
    private boolean checkStatus = false;
    
    SwingWorker<Void, Void> worker, workerTwo, workerThree;

    Random random = new Random();

    public SlotMachine() {

        
        // button size and position
        buttonTwoPanel.add(spinBtn);
        spinBtn.setPreferredSize(new Dimension(120, 40));
        
        //Other menu
        buttonPanel.add(addCoinBtn);
        buttonPanel.add(betOneBtn);
        buttonPanel.add(betMaxBtn);
        buttonPanel.add(resetBtn);

        rightPanel.add(creditLabel);
        rightPanel.add(betLabel);

        topPanel.add(new JPanel());
        topPanel.add(buttonTwoPanel);
        topPanel.add(buttonPanel);
        
        iconOne = new ImageIcon(reelOne.reel()[0].getImage());
        iconOne.setImage(iconOne.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        
        iconTwo = new ImageIcon(reelTwo.reel()[0].getImage());
        iconTwo.setImage(iconTwo.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        iconThree = new ImageIcon(reelThree.reel()[0].getImage());
        iconThree.setImage(iconThree.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        
        reelOneBtn.setIcon(iconOne);
        reelTwoBtn.setIcon(iconTwo);
        reelThreeBtn.setIcon(iconThree);

        middlePanel.add(reelOneBtn);
        middlePanel.add(reelTwoBtn);
        middlePanel.add(reelThreeBtn);

        bottomPanel.add(rightPanel);
        bottomPanel.add(staticsBtn);

        this.getContentPane().add(topPanel, BorderLayout.SOUTH);
        this.getContentPane().add(middlePanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.CENTER);
        
        
         //Frame properties
        this.setTitle("Slot Machine");
        this.setSize(650, 580);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Handlers
        MyListener handler = new MyListener();
        betOneBtn.addActionListener(handler);
        resetBtn.addActionListener(handler);
        betMaxBtn.addActionListener(handler);
        addCoinBtn.addActionListener(handler);
        spinBtn.addActionListener(handler);
        staticsBtn.addActionListener(handler);
        reelOneBtn.addActionListener(handler);
        reelTwoBtn.addActionListener(handler);
        reelThreeBtn.addActionListener(handler);
        
    }
         private class MyListener implements ActionListener {

            public void actionPerformed(ActionEvent evt){
                String btnLabel = evt.getActionCommand();
                if (btnLabel.equals("Bet One")){
                    if (credit <= 0) {
                    JOptionPane.showMessageDialog(null, "Not enough credit to bet");
                } else {
                    credit--;
                    currentCredit++;
                    creditLabel.setText("Credit: " + credit);
                    betLabel.setText("Credit betting with: " + currentCredit + "    ");
                }
                
            } else if (btnLabel.equals("Reset")){
               credit += currentCredit;// If user resets the credit add it all
                // back to the inital credit
                currentCredit = 0;
                creditLabel.setText("Credit: " + credit);
                betLabel.setText("Credit betting with: " + currentCredit + "    ");
                betMaxBtn.setEnabled(true); 
            } else if (btnLabel.equals("Bet Max")){
                if (credit <= 2) {
                    JOptionPane.showMessageDialog(null, "Not enough credit to place a maximum bet");
                } else {
                    credit -= 3;
                    currentCredit += 3;
                    creditLabel.setText("Credit: " + credit);
                    betLabel.setText("Credit betting with: " + currentCredit + "    ");
                    betMaxBtn.setEnabled(false);

                }
            } else if (btnLabel.equals("Add Coin")){
                credit++;// Increase credit
                creditLabel.setText("Credit: " + credit);
                betLabel.setText("Credit betting with: " + currentCredit + "    ");

            } else if (btnLabel.equals("Spin")){
                if (credit < 0 || currentCredit <= 0) {
                    JOptionPane.showMessageDialog(null, "You need to insert credit to spin");
                } else {
                    
                    spinBtn.setEnabled(false);
                    betOneBtn.setEnabled(false);
                    betMaxBtn.setEnabled(false);
                    checkStatus = true;
                    startReelOne();// Start and run the three spinner threads
                    startReelTwo();
                    startReelThree();
                    totalCredits += currentCredit;
                    numberOfGames++;
                    
                            
                }
                
            } else if (btnLabel.equals("Statistics")){
                if (numberOfGames == 0) {
                    new Statistics(wins, looses, 0).setVisible(true);
                } else {
                    new Statistics(wins, looses, new Double(totalCredits / numberOfGames)).setVisible(true);
                }
                
            }
        }



    }

    public void startReelOne() {
        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    reelOneIndex = random.nextInt(6);
                    iconOne = new ImageIcon(reelOne.reel()[reelOneIndex].getImage());
                    iconOne.setImage(iconOne.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
                    reelOneBtn.setIcon(iconOne);
                    Thread.sleep(200);
                }
            }
        };
        worker.execute();
        reelOneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel(true);
                workerTwo.cancel(true);
                workerThree.cancel(true);
                checkWin();
            }
        });

    }

    public void startReelTwo() {
        workerTwo = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                while (true) {
                    reelTwoIndex = random.nextInt(6);
                    iconTwo = new ImageIcon(reelTwo.reel()[reelTwoIndex].getImage());
                    iconTwo.setImage(iconTwo.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
                    reelTwoBtn.setIcon(iconTwo);
                    Thread.sleep(200);
                }

            }

        };
        workerTwo.execute();
        reelTwoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel(true);
                workerTwo.cancel(true);
                workerThree.cancel(true);
                checkWin();
            }
        });
    }

    public void startReelThree() {
        workerThree = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                while (true) {
                    reelThreeIndex = random.nextInt(6);
                    iconThree = new ImageIcon(reelThree.reel()[reelThreeIndex].getImage());
                    iconThree.setImage(iconThree.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
                    reelThreeBtn.setIcon(iconThree);
                    Thread.sleep(200);
                }

            }

        };
        workerThree.execute();
        reelThreeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worker.cancel(true);
                workerTwo.cancel(true);
                workerThree.cancel(true);
                checkWin();
            }
        });

    }

    public void checkWin() {
        if (checkStatus) {
            // Check if have won
   if ((reelOneIndex == 0) && (reelThreeIndex == 0)
                    && (reelTwoIndex == 0)) {
                JOptionPane.showMessageDialog(null, "You have won 6 coins");
                credit = credit + currentCredit + 6;
                wins++;
            } else if ((reelOneIndex == 1) && (reelThreeIndex == 1)
                    && (reelTwoIndex == 1)) {
                JOptionPane.showMessageDialog(null, "You have won 2 coins");
                credit = credit + currentCredit + 2;
                wins++;
            } else if ((reelOneIndex == 2) && (reelThreeIndex == 2)
                    && (reelTwoIndex == 2)) {
                JOptionPane.showMessageDialog(null, "You have won 3 coins");
                credit = credit + currentCredit + 3;
                wins++;
            } else if ((reelOneIndex == 3) && (reelThreeIndex == 3)
                    && (reelTwoIndex == 3)) {
                JOptionPane.showMessageDialog(null, "You have won 4 coins");
                credit = credit + currentCredit + 4;
                wins++;
            } else if ((reelOneIndex == 4) && (reelThreeIndex == 4)
                    && (reelTwoIndex == 4)) {
                JOptionPane.showMessageDialog(null, "You have won 7 coins");
                credit = credit + currentCredit + 7;
                wins++;
            } else if ((reelOneIndex == 5) && (reelThreeIndex == 5)
                    && (reelTwoIndex == 5)) {
                JOptionPane.showMessageDialog(null, "You have won 5 coins");
                credit = credit + currentCredit + 5;
                wins++;
            } else if ((reelOneIndex == 0) && (reelThreeIndex == 0)) {
                JOptionPane.showMessageDialog(null, "You have won 6 coins");
                credit = credit + currentCredit + 6;
                wins++;
            } else if ((reelOneIndex == 0) && (reelTwoIndex == 0)) {
                JOptionPane.showMessageDialog(null, "You have won 6 coins");
                credit = credit + currentCredit + 6;
                wins++;
            } else if ((reelOneIndex == 1) && (reelThreeIndex == 1)) {
                JOptionPane.showMessageDialog(null, "You have won 2 coins");
                credit = credit + currentCredit + 2;
                wins++;
            } else if ((reelTwoIndex == 1) && (reelThreeIndex == 1)) {
                JOptionPane.showMessageDialog(null, "You have won 2 coins");
                credit = credit + currentCredit + 2;
                wins++;
            } else if ((reelOneIndex == 2) && (reelThreeIndex == 2)) {
                JOptionPane.showMessageDialog(null, "You have won 3 coins");
                credit = credit + currentCredit + 3;
                wins++;
            } else if ((reelTwoIndex == 2) && (reelThreeIndex == 2)) {
                JOptionPane.showMessageDialog(null, "You have won 3 coins");
                credit = credit + currentCredit + 3;
                wins++;
            } else if ((reelOneIndex == 3) && (reelThreeIndex == 3)) {
                JOptionPane.showMessageDialog(null, "You have won 4 coins");
                credit = credit + currentCredit + 4;
                wins++;
            } else if ((reelTwoIndex == 3) && (reelThreeIndex == 3)) {
                JOptionPane.showMessageDialog(null, "You have won 4 coins");
                credit = credit + currentCredit + 4;
                wins++;
            } else if ((reelOneIndex == 4) && (reelThreeIndex == 4)) {
                JOptionPane.showMessageDialog(null, "You have won 7 coins");
                credit = credit + currentCredit + 7;
                wins++;
            } else if ((reelTwoIndex == 4) && (reelThreeIndex == 4)) {
                JOptionPane.showMessageDialog(null, "You have won 7 coins");
                credit = credit + currentCredit + 7;
                wins++;
            } else if ((reelOneIndex == 5) && (reelThreeIndex == 5)) {
                JOptionPane.showMessageDialog(null, "You have won 5 coins");
                credit = credit + currentCredit + 5;
                wins++;
            } else if ((reelTwoIndex == 6) && (reelThreeIndex == 5)) {
                JOptionPane.showMessageDialog(null, "You have won 5 coins");
                credit = credit + currentCredit + 5;
                wins++;
            } else {//otherwise have lost
                JOptionPane.showMessageDialog(null, "You have lost");
                looses++;
            }
            currentCredit = 0;
            creditLabel.setText("Credit: " + credit);
            betLabel.setText("Credit betting with: " + currentCredit + "    ");

            betMaxBtn.setEnabled(true);
            checkStatus = false;
            spinBtn.setEnabled(true);
            betOneBtn.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new SlotMachine();
    }

}



