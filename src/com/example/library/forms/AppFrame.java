package com.example.library.forms;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


public class AppFrame extends JFrame implements ChValueListener {
	private JPanel appFrame;
	
	private JButton button1;
	private JButton bConnectCom;
	private JComboBox<String> cbxComPort;
	private ChValueListener publisher;
	JLabel chanValPublished = new JLabel();
	
	SerialPort[] comPortovi;
	SerialPort izabran = null;
	OutputStream os = null;
	BufferedOutputStream bos = null;

	
	public AppFrame() {
		setTitle("DMX Tester Gui");
		setLocation(-900, 10);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					os.flush();
					os.close();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				} finally {
					izabran.closePort();
				}
				dispose();
				System.exit(0);
			}
		});
		
		appFrame.setLayout(new GridLayout(0,1));	// any number of rows, exactly 1 column
		
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
		
		appFrame.add(gore);
		
		for (int i = 1; i <= 16; i++) {
			DmxChan dc = new DmxChan(this, i);
			appFrame.add(dc.panel);
		}
		
		chanValPublished.setFont(new Font("Monospaced", Font.BOLD, 20));
		chanValPublished.setHorizontalAlignment(SwingConstants.CENTER);
		appFrame.add(chanValPublished);
		
		comPortovi = SerialPort.getCommPorts();
		if (comPortovi.length == 0) {
			System.out.println("No serial ports found.");
			return;
		} else {
			for (int i = 0; i < comPortovi.length; i++) {
				cbxComPort.addItem(comPortovi[i].getDescriptivePortName() + " " + comPortovi[i].getPortDescription());
			}
		}
		
		
		setContentPane(appFrame);
		pack();
		
		cbxComPort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = cbxComPort.getSelectedIndex();
				String s= (String) cbxComPort.getSelectedItem();
				izabran = comPortovi[i];
				if (izabran.openPort()) {
					System.out.println(izabran.getSystemPortName() + ": opened");
				} else {
					System.out.println(izabran.getSystemPortName() + ": error opening!");
				}
				os = izabran.getOutputStream();
				bos = new BufferedOutputStream(os);
			}
		});
	}
	
	
	
	
	@Override
	public void onChValChange(int ch, int val) {
		chanValPublished.setText("ch:" + ch + " = " + val);
		System.out.println("ch:" + ch + " = " + val);
		if (izabran != null) {
			byte[] data = new byte[4];
			try {
				data[0] = 0;
				data[1] = (byte) ch;
				data[2] = (byte) 0;
				data[3] = (byte) val;
				bos.write(data);
				bos.flush();
				System.out.println("sent \"" + Arrays.toString(data) + "\"");
			} catch (IOException e) {
				System.out.println(izabran.getSystemPortName() + ": error sending char \"." + Arrays.toString(data) + "\"" + " " + e.getMessage());
			}
			
		}
		
		
	}
}
