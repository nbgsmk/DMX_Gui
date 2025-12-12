package com.example.library;

import com.example.library.forms.ChPublisher;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DC extends JPanel {
	
	private ChPublisher publisher;
	private final int chNum;
	private int tmp;
	private int chanValue;
	
	JCheckBox cbEnable = new JCheckBox();
	JButton bm = new JButton("-");
	JButton bp = new JButton("+");
	JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 50);
	JLabel tv_Val = new JLabel();
	JTextField etNaziv = new JTextField(30);
	
	public DC(ChPublisher publisher, int chNum) {
		this.publisher = publisher;
		this.chNum = chNum;
		tmp = slider.getValue();
		setIt(tmp);
		
		cbEnable.setSelected(false);
		add(cbEnable);
		add(bm);
		add(slider);
		add(bp);
		
		tv_Val.setFont(new Font("Monospaced", Font.BOLD, 14));
		add(tv_Val);
		
		etNaziv.setFont(new Font("Monospace", Font.BOLD, 14));
		add(etNaziv);
	
		cbEnable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					setIt(tmp);
			}
		});
		bm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tmp > 0) {
					cbEnable.setSelected(true);
					tmp--;
					setIt(tmp);
				}
			}
		});
		bp.addActionListener(new ActionListener() {
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
		tv_Val.setText("ch: " + String.format("% " + 2 + "d", chNum) + " = " + String.format("% " + 4 + "d", chanValue));
		if (publisher != null) {
			publisher.onPub(chNum, chanValue);
		}
		
		
	}
	

}
