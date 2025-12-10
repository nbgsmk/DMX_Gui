package com.example.library.forms;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class DmxChan extends JPanel {
	public JPanel contentPane;
	private JCheckBox onOffCheckBox;
	private JButton bMinus;
	private JSlider slider;
	private JButton bPlus;
	private JTextField etVal;
	
	private int chanValue = 0;
	
	PropertyChangeListener pcl = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
		
		}
	};
	
	
	public DmxChan() {
		bMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chanValue--;
			}
		});

		
		bPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chanValue++;
			}
		});
		
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					chanValue = source.getValue();
				}
			}
		});
		
		
		etVal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == etVal) {
					chanValue = Integer.parseInt(etVal.getText());
				}
			}
		});
		setVisible(true);
	
		setBackground(Color.MAGENTA);
		bMinus.setText("jebem vam mater");
		contentPane.add(bMinus);
		bMinus.setVisible(true);
		contentPane.setVisible(true);

		
	}
	
	
}
