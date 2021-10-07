import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon ;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener ;
import java.sql.ResultSet;

import javax.swing.JButton ;
import javax.swing.JComboBox;

class LoginFrame extends JFrame implements ActionListener
{
	JLabel label_username , label_password , label_msg ;
	JTextField tf_username , tf_password ;
	JPasswordField pf_password ;
	JButton btn_login , btn_exit ;
	JFrame frame ;
	JLabel bgImage ;
	Font f ;
	
	public LoginFrame()
	{	
		// frame
		frame = new JFrame() ;
		frame.setTitle( "Login" ) ;
		frame.setSize( 500 , 300 ) ;
		frame.setLayout( null ) ;
		frame.setLocationRelativeTo( null ) ;
		frame.setResizable( false ) ;
		frame.setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
		
		// setting bg image of frame
		ImageIcon bg = new ImageIcon( "E:\\wallpaper04.jpg" ) ;
		Image img = bg.getImage() ;
		
		Image tempImg = img.getScaledInstance( 500 , 300 , Image.SCALE_SMOOTH ) ;	// to make image fit to window
		bg = new ImageIcon( tempImg ) ;
		bgImage = new JLabel( "" , bg , JLabel.CENTER ) ;
		bgImage.setBounds( 0 , 0 , 500 , 300 ) ;	// from top corner to window size
		frame.add( bgImage ) ;
		
		// labels
		label_msg = new JLabel( "Please fill the login details" ) ;
		f = new Font( "Arial" , Font.PLAIN , 18 ) ;
		label_msg.setFont( f ) ;
		
		label_username = new JLabel( "Username:" ) ;
		label_password = new JLabel( "Password:" ) ;
		
		// text field and password field
		tf_username = new JTextField() ;
		pf_password = new JPasswordField() ;
		
		// buttons
		btn_login = new JButton( "Login" ) ;
		btn_exit = new JButton( "Exit" ) ;
		
		// setting label bounds
		label_msg.setBounds( 120 , 20 , 300 , 25 ) ;
		bgImage.add( label_msg ) ;
		
		label_username.setBounds( 100 , 100 , 100 , 20 ) ;
		bgImage.add( label_username ) ;
		
		label_password.setBounds( 100 , 130 , 100 , 20 ) ;
		bgImage.add( label_password ) ;
		
		//text field bounds
		tf_username.setBounds( 210 , 100 , 220 , 20 ) ;
		bgImage.add( tf_username ) ;
		
		pf_password.setBounds( 210 , 130 , 220 , 20 ) ;
		bgImage.add( pf_password ) ;
		
		// setting button bounds
		btn_login.setBounds( 210 , 180 , 120 , 25 ) ;
		bgImage.add( btn_login ) ;
		btn_login.addActionListener( this ) ;
		
		btn_exit.setBounds( 350 , 180 , 80 , 25 ) ;
		bgImage.add( btn_exit ) ;
		btn_exit.addActionListener( this ) ;
		
		frame.setVisible( true ) ;
	}
	
	public void actionPerformed( ActionEvent ae )
	{
		
		if( ae.getSource() == btn_login )
		{
			String username =  tf_username.getText() ;
			char[] password = pf_password.getPassword() ;
			
			try
			{
				if( tf_username.equals( "" ) || pf_password.equals( "" ) )
				{
					JOptionPane.showMessageDialog( null , "Please fill all the details" ) ;
				}
				
				else
				{
					ConnectionJDBC conn = new ConnectionJDBC() ;
					ResultSet rs ;
					
					rs = conn.state.executeQuery( "SELECT * FROM BANK_ADMIN" + " WHERE USER_ID = '" + username + "'" + "AND PASSWORD = '" + password + "'" ) ;
					
					if( rs.next() )
					{
						String u_name = rs.getString( "USER_ID" ) ;
						String pass = rs.getString( "PASSWORD" ) ;
						
						if( u_name.equals( username ) && pass.equals( password ) )
						{
							new BankFrame().frame.setVisible( true ) ;
							frame.setVisible( false ) ;
						}
						
						else
						{
							JOptionPane.showMessageDialog( null , "Invalid credentials!!" ) ;
						}
					}
				}
			}
			
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			

		}
		
		else if( ae.getSource() == btn_exit )
		{
			System.exit( 0 ) ;
		}
		
	}
}

public class LoginMain
{
	public static void main(String[] args)
	{
		// create object of BankFrame class to call constructor
		LoginFrame lFrame = new LoginFrame() ;
		
	}
}
