/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotMachine;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author w1565407
 */
public class Statistics extends JFrame {

    private JTextArea statArea = new JTextArea(80, 00);
    private JButton saveBtn = new JButton("Save");
    private JScrollPane jScrollPane = new JScrollPane(statArea);
    private JPanel middlePanel = new JPanel();

    public Statistics(int win, int loose, double averageCreditsPerGame) {

        setTitle("Stats");
        middlePanel.add(saveBtn);
        statArea.append("Wins: " + win + "\n");
        statArea.append("Losses: " + loose + "\n");
        statArea.append("Average number of credits netted per game: " + averageCreditsPerGame);
        statArea.append("\n");
        statArea.append("\n");
        this.getContentPane().add(jScrollPane, BorderLayout.CENTER);
        this.getContentPane().add(middlePanel, BorderLayout.SOUTH);
        this.setSize(390, 200);

        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {            
             
        try {
            //open
            FileWriter fw = new FileWriter("SlotMachine results", true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            //write
            fw.write(statArea.getText());
            bw.flush();
            System.out.println("File was created!");
            
            //close
            bw.close();
            fw.close();
        } catch (IOException e1) {
            System.out.println("Error");
        }

        }

            });
                }
}
