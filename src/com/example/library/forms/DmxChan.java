package com.example.library.forms;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;


public class DmxChan extends JPanel {
	
	public JPanel chPanel;
	private JCheckBox cbEnable;
	private JButton bMinus;
	private JSlider slider;
	private JButton bPlus;
	private JLabel tvVal;
	private JTextField etOpis;
	private JTextField textField1;
	
	ChValueListener chValueListener;
	private final int chNum;
	
	private int tmp;
	private int chanValue;
	
	
	public DmxChan(ChValueListener listener, int chNum) {
		this.chValueListener = listener;
		this.chNum = chNum;

		tmp = slider.getValue();
		setIt(tmp);
		
		cbEnable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setIt(tmp);
			}
		});
		
		bMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tmp > 0) {
					cbEnable.setSelected(true);
					tmp--;
					setIt(tmp);
				}
			}
		});

		
		bPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tmp < 255) {
					cbEnable.setSelected(true);
					tmp++;
					setIt(tmp);
				}
			}
		});
		
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cbEnable.setSelected(true);
				tmp = slider.getValue();
				setIt(tmp);
			}
		});
		
	}
	
	
	private void setIt(int val){
		if (cbEnable.isSelected()) {
			chanValue = val;
		} else {
			chanValue = 0;
		}
		tvVal.setText("ch: " + String.format("% " + 2 + "d", chNum) + " = " + String.format("% " + 4 + "d", chanValue));
		if (chValueListener != null) {
			chValueListener.onChValChange(chNum, chanValue);
		}
		
		
	}
	

}
