/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activity;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JFrame;

public class SplashScreen2 {
    JFrame frame;
    public SplashScreen2()
    {
frame = new JFrame();
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.setLocationRelativeTo(null);
        frame.add(new SceneKonami());
        frame.setSize(1200,500);
       frame.setResizable(false);
     // jf.getContentPane().setLayout(null);
      // frame.setUndecorated(true);
       frame.setVisible(true);  
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SplashScreen2();
    }
    class SceneKonami extends javax.swing.JPanel {

    private int index = 0;
    private javax.swing.Timer timer;
    private javax.swing.JButton jButton1,jButton2, jButton3;
    private javax.swing.JLabel texter1,texter2,texter3,texter4;
      javax.swing.JPanel p1=new javax.swing.JPanel(new java.awt.GridLayout(4,1));
      javax.swing.JPanel p2=new javax.swing.JPanel(new java.awt.GridLayout(2,2));
      JFrame jf;
    public SceneKonami() {
        initComponents();
    }

    private void initComponents() {

        jButton1 = new javax.swing.JButton("EXIT");
        jButton2 = new javax.swing.JButton("ADMIN LOGIN");
         jButton3 = new javax.swing.JButton("CUSTOMER LOGIN");
        texter1 = new javax.swing.JLabel();
        texter2 = new javax.swing.JLabel();
        texter3 = new javax.swing.JLabel();
        texter4 = new javax.swing.JLabel();
       texter1.setFont(new Font("BOLD", BOLD, 35));
       texter2.setFont(new Font("BOLD", BOLD, 35));
       texter3.setFont(new Font("BOLD", BOLD, 35));
       texter4.setFont(new Font("BOLD", BOLD, 35));
       
       jButton1.setFont(new Font("BOLD", BOLD, 35));
       jButton2.setFont(new Font("BOLD", BOLD, 35));
        jButton3.setFont(new Font("BOLD", BOLD, 35));
       jButton1.setBackground(Color.ORANGE);
       jButton2.setBackground(Color.ORANGE);
        jButton3.setBackground(Color.ORANGE);
        setLayout(new BorderLayout(5,5));
        p1.add(texter1);
         p1.add(texter2);
          p1.add(texter3);
           p1.add(texter4);
           p2.add(jButton1); p2.add(jButton2);p2.add(jButton3);
       jButton3.setVisible(false); 
       jButton2.setVisible(false);
        
       
        //add(jButton1, BorderLayout.SOUTH);
        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1000,500));
        
       String message = "Welcome to: ";
        String message2="Stock Management System";
        String message3="...........................";
        String message4="Thanks";        
        slowPrint(message);
        jButton2.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
				 try {
                                       frame.setVisible(false);   
					 LoginActivity la = new LoginActivity();
		                            la.setVisible(true);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}    
                
			}
		});
           jButton3.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
				 try {
                                       frame.setVisible(false);   
					
                                        LoginActivity2 la = new LoginActivity2();
		                                            la.setVisible(true);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}    
                
			}
		});
    
    

    jButton1.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
				 try {
                                      System.exit(0);
				} catch (Exception ex) {
					ex.printStackTrace();
				}    
                
			}
		});
    }
    
    
    public void slowPrint(String message) {
 String message2="Stock Management System";
        if(timer != null && timer.isRunning())
            return;
        index   = 0;
        texter1.setText("");

        timer = new javax.swing.Timer(100,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                texter1.setText(texter1.getText() + String.valueOf(message.charAt(index)));
                index++;
              //  timer.setDelay(50);
                if (index >= message.length()) {
                    timer.stop();
                    slowPrint2(message2);
                }
            }
        });
        timer.start();
   
    }
    
    public void slowPrint2(String message) {
 String message3=".....................................";
        if(timer != null && timer.isRunning())
            return;
        index   = 0;
        texter2.setText("");

        timer = new javax.swing.Timer(100,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                texter2.setText(texter2.getText() + String.valueOf(message.charAt(index)));
                index++;
                if (index >= message.length()) {
                    timer.stop();
                    slowPrint3(message3);
                }
            }
        });
        timer.start();
   
    }
    
    public void slowPrint3(String message) {
 String message4="................................";   
        if(timer != null && timer.isRunning())
            return;
        index   = 0;
        texter3.setText("");

        timer = new javax.swing.Timer(100,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                texter3.setText(texter3.getText() + String.valueOf(message.charAt(index)));
                index++;
                if (index >= message.length()) {
                    timer.stop();
                     slowPrint4(message4);
                }
            }
        });
        timer.start();
   
    }
    
    public void slowPrint4(String message) {

        if(timer != null && timer.isRunning())
            return;
        index   = 0;
        texter4.setText("");

        timer = new javax.swing.Timer(100,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                texter4.setText(texter4.getText() + String.valueOf(message.charAt(index)));
                index++;
                if (index >= message.length()) {
                    timer.stop();
                     jButton3.setVisible(true);  
                     jButton2.setVisible(true);
                }
            }
        });
        timer.start();
   
    }
    } 
}
