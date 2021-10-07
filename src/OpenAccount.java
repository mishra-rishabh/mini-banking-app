import java.awt.* ;
import javax.swing.* ;
import java.awt.event.* ;
import java.sql.* ;
import java.util.* ;

public class OpenAccount extends JFrame implements ActionListener
{
	JLabel label_choose_bank , label_cust_name , label_cust_email , label_amount , label_msg ;
	JTextField tf_name , tf_email , tf_amount , tf_choose_bank ;
	JButton btn_open_account , btn_exit ;
	JComboBox combo_bank_list ;
	JLabel bgImage ;
	JFrame frame ;
	Font f ;
	
	// generate account number randomly
	Random ran = new Random() ;

	public OpenAccount()
	{
		// frame
		frame = new JFrame() ;
		frame.setTitle( "New Account Application Form" ) ;
		frame.setSize( 500 , 500 ) ;
		frame.setLayout( null ) ;
		frame.setLocationRelativeTo( null ) ;	// used to center the frame in screen
		frame.setResizable( false ) ;
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		
		// setting bg image of a frame
		ImageIcon bg = new ImageIcon( "E:\\wallpaper04.jpg" ) ;
		Image img = bg.getImage() ;
		
		Image tempImg = img.getScaledInstance( 500 , 500 , Image.SCALE_SMOOTH ) ; // to make bg image fit to window
		bg = new ImageIcon( tempImg ) ;
		bgImage = new JLabel( "" , bg , JLabel.CENTER ) ;
		bgImage.setBounds( 0 , 0 , 500 , 500 ) ;	// from top corner to window size
		frame.add( bgImage ) ;
		
		// labels
		label_msg = new JLabel( "Please fill the following details" ) ;
		f = new Font( "Arial" , Font.PLAIN, 18 ) ;
		label_msg.setFont( f ) ;
		
		label_choose_bank = new JLabel( "Choose Bank:" ) ;
		label_cust_name = new JLabel( "Full Name:" ) ;
		label_cust_email = new JLabel( "Email-id:" ) ;
		label_amount = new JLabel( "Amount:" ) ;
		
		// text fields
		tf_name = new JTextField() ;
		tf_email = new JTextField() ;
		tf_amount = new JTextField() ;
		
		// adding items into JComboBox
		String []bank_list = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list = new JComboBox( bank_list ) ;
		
		// button
		btn_open_account = new JButton( "Submit" ) ;
		btn_exit = new JButton( "Exit" ) ;
		
		// setting label bounds
		label_msg.setBounds( 120 , 20 , 300 , 25 ) ;
		bgImage.add( label_msg ) ;
		
		label_choose_bank.setBounds( 100 , 100 , 100 , 20 ) ;
		bgImage.add( label_choose_bank ) ;
		
		label_cust_name.setBounds( 100 , 130 , 100 , 20 ) ;
		bgImage.add( label_cust_name ) ;
		
		label_cust_email.setBounds( 100 , 160 , 100 , 20 ) ;
		bgImage.add( label_cust_email ) ;
		
		label_amount.setBounds( 100 , 190 , 100 , 20 ) ;
		bgImage.add( label_amount ) ;
		
		// setting combo box and text field bounds
		combo_bank_list.setBounds( 200 , 100 , 220 , 20 ) ;
		bgImage.add( combo_bank_list ) ;
		
		tf_name.setBounds( 200 , 130, 220 , 20 ) ;
		bgImage.add( tf_name ) ;
		
		tf_email.setBounds( 200 , 160 , 220 , 20 ) ;
		bgImage.add( tf_email ) ;
		
		tf_amount.setBounds( 200 , 190 , 220 , 20 ) ;
		bgImage.add( tf_amount ) ;
		
		// setting button bounds
		btn_open_account.setBounds( 200 , 250 , 100 , 25 ) ;
		bgImage.add( btn_open_account ) ;
		btn_open_account.addActionListener( this ) ;
		
		btn_exit.setBounds( 320 , 250 , 100 , 25 ) ;
		bgImage.add( btn_exit ) ;
		btn_exit.addActionListener( this ) ;
		
		frame.setVisible( true ) ;
	}
	
	public void actionPerformed( ActionEvent ae )
	{
		int flag = 0 ;
		
		if(ae.getSource() == btn_exit)
		{
			new BankFrame().frame.setVisible( true ) ;	// Constructor of Bank class
			frame.setVisible( false ) ;
		}
		
		else
		{
			// taking inputs
			String bank1 = ( String ) combo_bank_list.getSelectedItem() ; 
			String name = tf_name.getText() ;
			String email = tf_email.getText() ;
			String temp_amount = tf_amount.getText() ;
			//int amount = Integer.parseInt( tf_amount.getText() ) ;
			
			try
			{
				if( ( temp_amount.equals( "" ) || tf_email.getText().equals( "" ) || tf_name.getText().equals( "" ) || combo_bank_list.getSelectedIndex() == 0 ) )
				{
					
					JOptionPane.showMessageDialog( null , "Please fill all the details" ) ;
				}
				
				else
				{
					try
					{
						int amount = Integer.parseInt( tf_amount.getText() ) ;
						String bank = "" ;
						
						ConnectionJDBC conn = new ConnectionJDBC() ;
						
						Random ran = new Random() ;
						long random_num = 0L ; // generates random account numbers
						String acc_num = "" ;
						
						for( int i = 1 ; i <= 10 ; i++ )
						{
							random_num = ran.nextInt( (int) 9000000000L ) + 1000000000 ;
						}
						
						int bank_index = combo_bank_list.getSelectedIndex() ;
						
						if( bank_index == 1 )
						{
							bank = "SBI" ;
							acc_num = "SBI_" + random_num ; 
							
							if( amount < 1000 )
							{
								flag = 1 ;
								JOptionPane.showMessageDialog( null , "Minimum deposit should be Rs. 1000" ) ;
							}
						}
						else if( bank_index == 2 )
						{
							bank = "KOTAK" ;
							acc_num = "KOTAK_" + random_num ;
							
							if( amount < 10000 )
							{
								flag = 1 ;
								JOptionPane.showMessageDialog( null , "Minimum deposit should be Rs. 10000" ) ;
							}
						}
						else if( bank_index == 3 )
						{
							bank = "PNB" ;
							acc_num = "PNB_" + random_num ;
							
							if( amount < 2000 )
							{
								flag = 1 ;
								JOptionPane.showMessageDialog( null , "Minimum deposit should be Rs. 2000" ) ;
							}
						}
						else if( bank_index == 4 )
						{
							bank = "HDFC" ;
							acc_num = "HDFC_" + random_num ;
							
							if( amount < 5000 )
							{
								flag = 1 ;
								JOptionPane.showMessageDialog( null , "Minimum deposit should be Rs. 5000" ) ;
							}
						}
						
						if( flag == 0 )
						{
							String query1 = "INSERT INTO " + bank + " values( '" + acc_num + "' , '"+ name +"' , '"+ email +"' ,"+ amount + " ," + 1 + " )" ;
							conn.state.executeUpdate( query1 ) ;
							
							JOptionPane.showMessageDialog( null , "Hello " + name + " , your account number is " + acc_num ) ;
						}
					}
					
					catch( NumberFormatException nfe )
					{
						JOptionPane.showMessageDialog( null , "Please re-enter the amount" ) ;
					}
				}
			}
			
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
	}
	
	public static void main(String[] args)
	{
		OpenAccount open_acc = new OpenAccount() ;
	}
}
