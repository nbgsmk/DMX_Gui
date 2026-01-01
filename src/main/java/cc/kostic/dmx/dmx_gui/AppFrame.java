package cc.kostic.dmx.dmx_gui;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

@Component
public class AppFrame extends JFrame implements ChValueListener {
	private JPanel appFrame;

	private final Preferences prefs = Preferences.userNodeForPackage(AppFrame.class);
	private final String wposx = "window-position-x";
	private final String wposy = "window-position-y";

	private JButton bSendBreak;
	private JButton bCloseCom;
	private JComboBox<String> cbxComPort;
	JLabel chanValPublished = new JLabel();
	
	JTextField etPocetniKanal;
	
	private COMWriter comWriter = null;

	
	public AppFrame() {
		setTitle("DMX Tester Gui");
		int xpos = prefs.getInt(wposx, 0);
		int ypos = prefs.getInt(wposy, 0);
		if (isLocationOnScreen(xpos, ypos)) {
			setLocation(xpos, ypos);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		appFrame.setLayout(new GridLayout(0,1));	// any number of rows, exactly 1 column
		
		JTextField etNazivFixture = new JTextField(30);
		etPocetniKanal = new JTextField(5);
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
		
		for (int i = 1; i <= 20; i++) {
			DmxChan dc = new DmxChan(this, i);
			appFrame.add(dc.panel);
		}
		
		chanValPublished.setFont(new Font("Monospaced", Font.BOLD, 20));
		chanValPublished.setHorizontalAlignment(SwingConstants.CENTER);
		appFrame.add(chanValPublished);
		
		SerialPort[] sviPortovi = SerialPort.getCommPorts();
		if ((sviPortovi == null) || (sviPortovi.length == 0)) {
			String s = "No serial ports found.";
			System.out.println(s);
			cbxComPort.addItem(s);
			cbxComPort.setEnabled(false);
			cbxComPort.setBackground(Color.RED);
		} else {
			cbxComPort.setEnabled(true);
			cbxComPort.setBackground(Color.YELLOW);
			for (int i = 0; i < sviPortovi.length; i++) {
				cbxComPort.addItem(sviPortovi[i].getDescriptivePortName() + " " + sviPortovi[i].getPortDescription());
			}
			cbxComPort.addItem(" ... izaberi ... ");
			cbxComPort.setSelectedIndex(sviPortovi.length);
		}
		
		cbxComPort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SerialPort[] sviPortovi = SerialPort.getCommPorts();
				int i = cbxComPort.getSelectedIndex();
				comWriter = new COMWriter(sviPortovi[i]);
				boolean stat = comWriter.open();
				if (stat) {
					cbxComPort.setEnabled(true);
					cbxComPort.setBackground(Color.GREEN);
				} else {
					comWriter.close();
					comWriter = new COMWriter(sviPortovi[i]);
					cbxComPort.setBackground(Color.RED);
				}
			}
		});
		
		
		bCloseCom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comWriter.isOpen()) {
					comWriter.close();
				} else {
					comWriter.open();
				}
				if (comWriter.isOpen()) {
					cbxComPort.setBackground(Color.GREEN);
				} else {
					cbxComPort.setBackground(Color.RED);
				}
			}
		});
		
		bSendBreak.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comWriter.sendBreak();
			}
		});

		setContentPane(appFrame);
		pack();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeCom();
				saveWindowPos();
			}
		});
	}


	
	@Override
	public void onChValChange(int ch, int val) {
		String p = etPocetniKanal.getText();
		int ofs = Integer.parseInt(p) - 1;	// FREEZE!! minus 1
		chanValPublished.setText("ch:" + ch + " = " + val + "     (abs.ch:" + String.valueOf(ch+ofs) + ")" );
		// chanValPublished.setText("ch:" + ch + " (abs.ch:" + String.valueOf(ch+ofs) + ") = " + val );
		System.out.println("ch:" + ch + " = " + val);
		if (comWriter != null) {
			comWriter.write(ch+ofs, val);
		}
	}


	/// //////////////////
	/// //////////////////
	// cleanup
	/// /////////////////
	/// /////////////////


	public boolean isLocationOnScreen(int x, int y) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		for (GraphicsDevice screen : screens) {
			// bounds includes the screen's x,y offset in a multi-monitor setup
			Rectangle bounds = screen.getDefaultConfiguration().getBounds();
			if (bounds.contains(x, y)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * close com port if any is open
	 */
	private void closeCom(){
		if (comWriter != null) {
			comWriter.close();
		}
	}

	/**
	 * save app window position on exit
	 */
	private void saveWindowPos(){
		prefs.putInt(wposx, getX());
		prefs.putInt(wposy, getY());
	}


}