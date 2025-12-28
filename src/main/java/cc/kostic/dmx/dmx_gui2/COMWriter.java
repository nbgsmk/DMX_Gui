package cc.kostic.dmx.dmx_gui2;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class COMWriter {
	SerialPort port;
	OutputStream os  = null;
	BufferedOutputStream bos = null;
	byte[] data = null;
	
	public COMWriter(SerialPort port) {
		this.port = port;
	}
	
	public boolean open(){
		boolean rezult = port.openPort(500);
		if (rezult) {
			os = port.getOutputStream();
			bos = new BufferedOutputStream(os);
			System.out.println(port.getSystemPortName() + ": opened");
		} else {
			System.out.println(port.getSystemPortName() + ": error opening!");
		}
		return rezult;
	}
	
	public void write(int chan, int val){
		if (port != null) {
			try {
				data = new byte[4];
				data[0] = (byte) ((chan > 255) ? (chan>>8) : (0) );
				data[1] = (byte) chan;
				data[2] = (byte) 0;
				data[3] = (byte) val;
				bos.write(data);
				bos.flush();
				System.out.println("sent \"" + Arrays.toString(data) + "\"");
			} catch (IOException e) {
				System.out.println(port.getSystemPortName() + ": error sending char \"." + Arrays.toString(data) + "\"" + " " + e.getMessage());
			}
		}
		
	}
	
	
	public void sendBreak(){
		if (port != null) {
			try {
				byte[] brejk = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff };
				bos.write(brejk);
				bos.flush();
				System.out.println("sent \"0xff 0xff 0xff 0xff\"");
			} catch (IOException e) {
				System.out.println(port.getSystemPortName() + ": error sending char \"." + Arrays.toString(data) + "\"" + " " + e.getMessage());
			}
			
		}
	}
	
	public boolean close(){
		boolean rezultat = true;
		try {
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		} catch (IOException ex) {
			// throw new RuntimeException(ex);
		} finally {
			rezultat = port.closePort();
			System.out.println(port.getSystemPortName() + ": try/catch/{finally -> should be closed now}");
		}
		return rezultat;
	}
	
	public boolean isOpen(){
		return port.isOpen();
	}
	
}
