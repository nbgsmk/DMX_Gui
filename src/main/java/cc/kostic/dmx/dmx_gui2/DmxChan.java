package cc.kostic.dmx.dmx_gui2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DmxChan extends JPanel {
	
	public JPanel panel;
	private JCheckBox cbEnable;
	private JButton bMinus;
	private JSlider slider;
	private JButton bPlus;
	private JTextArea tvChnum;
	private JTextField etOpis;
	private JTextField etVal;
	
	ChValueListener chValueListener;
	private final int chNum;
	
	private int tmp;
	private int chanValue;
	
	
	public DmxChan(ChValueListener listener, int chNum) {
		this.chValueListener = listener;
		this.chNum = chNum;
		this.tvChnum.setText("ch:" + chNum);

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

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cbEnable.setSelected(true);
				tmp = slider.getValue();
				setIt(tmp);
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
		
		
		etVal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// on press ENTER
				if ((tmp > 0) && (tmp < 255)) {
					String s = etVal.getText();
					try {
						tmp = Integer.parseInt(s);
						cbEnable.setSelected(true);
						slider.setValue(tmp);
						setIt(tmp);
					} catch (NumberFormatException exception) {
						// nista
					}
				}
			}
		});
		
		// etVal.addFocusListener(new java.awt.event.FocusAdapter() {
		// 	@Override
		// 	public void focusLost(java.awt.event.FocusEvent e) {
		// 		// on user leaving field
		// 		if ((tmp > 0) && (tmp < 255)) {
		// 			String s = etVal.getText();
		// 			try {
		// 				tmp = Integer.parseInt(s);
		// 				cbEnable.setSelected(true);
		// 				setIt(tmp);
		// 			} catch (NumberFormatException exception) {
		// 				// nista
		// 			}
		// 		}
		// 	}
		// });
		
	}
	
	
	private void setIt(int val){
		if (cbEnable.isSelected()) {
			chanValue = val;
		} else {
			chanValue = 0;
		}
		etVal.setText(String.valueOf(chanValue));
		if (chValueListener != null) {
			chValueListener.onChValChange(chNum, chanValue);
		}
		
		
	}
	

}