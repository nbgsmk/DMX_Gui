package com.example.library.forms;

import com.example.library.DC;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class AppFrame extends JFrame {
	private JPanel contentPane;
	
	private JButton button1;
	private JButton button2;
	
	public AppFrame() {
		setTitle("DMX Tester Gui");
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(new GridLayout(0,1));	// any number of rows, exactly 1 column
		
		JTextField etNazivFixture = new JTextField(20);
		JTextField etPocetniKanal = new JTextField(5);
		JLabel tvNaz = new JLabel("Naziv fixture");
		JLabel tvPocet = new JLabel("pocetni kanal");
		
		etNazivFixture.setFont(new Font("Monospaced", Font.BOLD, 20));
		etPocetniKanal.setFont(new Font("Monospaced", Font.BOLD, 20));
		
		etNazivFixture.setHorizontalAlignment(SwingConstants.CENTER);
		etPocetniKanal.setHorizontalAlignment(SwingConstants.CENTER);
		
		etNazivFixture.setText("...");
		etPocetniKanal.setText("1");

		JPanel gore = new JPanel();
		gore.add(tvNaz);
		gore.add(etNazivFixture);
		gore.add(tvPocet);
		gore.add(etPocetniKanal);
		
		contentPane.add(gore);
		
		for (int i = 1; i <= 16; i++) {
			contentPane.add(new DC(i));
		}
		
		
		// JPanel panel1 = createColoredPanel(Color.RED, "Panel 1");
		// JPanel panel2 = createColoredPanel(Color.GREEN, "Panel 2");
		// JPanel panel3 = createColoredPanel(Color.BLUE, "Panel 3");
		//
		// panel2.add(new JButton("jbg"));
		// DmxChan ch1 = new DmxChan();
		// ch1.setVisible(true);
		// panel3.add(ch1);
		//
		// contentPane.add(panel1);
		// contentPane.add(panel2);
		// contentPane.add(ch1);
		// contentPane.add(panel3);
		
		
		setContentPane(contentPane);
		pack();
	}
	
	
	
	private static JPanel createColoredPanel(Color color, String labelText) {
     JPanel panel = new JPanel();
     panel.setBackground(color);
     // Add a simple label to the panel so we can see which is which
     JLabel label = new JLabel(labelText);
     label.setForeground(Color.BLACK);
     panel.add(label);
     return panel;
 }
 
 
 
}
