package app.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SetupPass extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTextField t;
	static JPasswordField pass;
	static JFrame f;
	static JButton b;
	static JLabel l;
	static String passEncrypted;

	public SetupPass() {
	}

	
		public static void GetPassw() {
			f = new JFrame("Enter password");
			l = new JLabel("");
			b = new JButton("Submit");
			SetupPass te = new SetupPass();
			b.addActionListener(te);
			pass = new JPasswordField(16);
			JPanel p = new JPanel();

			p.add(pass);
			p.add(b);
			p.add(l);

			f.add(p);

			f.setSize(300, 300);

			// In order to use the Enter key to click the Submit button
			f.getRootPane().setDefaultButton(b);

			try {
			 f.show();
			 Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			 
		}

		// when the button is pressed
		
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if (s.equals("Submit")) {
				l.setText("Password captured");
				//System.out.println(pass.getText());
				//passEncrypted = Encriptar(pass.getText());
				passEncrypted = pass.getText();
				// set the text of password field to blank
				pass.setText("");
				 
				 
			}
			
			//Close window 
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
		}

		public static String getPass() {
			GetPassw();
			//String strpass = "MD5:"+passEncrypted;
			String strpass = passEncrypted;
			return strpass;
		}

}
