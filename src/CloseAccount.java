import javax.swing.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;
import java.sql.* ;

public class CloseAccount extends JFrame implements ActionListener
{
	JLabel label_choose_bank , label_acc_no , label_msg ;
	JTextField tf_account_no ;
	JButton btn_close_account , btn_exit ;
	JComboBox combo_bank_list ;
	JFrame frame ;
	JLabel bgImage ;
	Font f ;
	
	public CloseAccount()
	{
		// frame
		frame = new JFrame() ;
		frame.setTitle( "Close Account" ) ;
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
		label_msg = new JLabel( "Please fill the following details" ) ;
		f = new Font( "Arial" , Font.PLAIN , 18 ) ;
		label_msg.setFont( f ) ;
		
		label_choose_bank = new JLabel( "Choose Bank:" ) ;
		label_acc_no = new JLabel( "Account number:" ) ;
		
		// text field
		tf_account_no = new JTextField() ;
		
		// adding items to combo box
		String []bank_list = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list = new JComboBox( bank_list ) ;
		
		// buttons
		btn_close_account = new JButton( "Close Account" ) ;
		btn_exit = new JButton( "Exit" ) ;
		
		// setting label bounds
		label_msg.setBounds( 120 , 20 , 300 , 25 ) ;
		bgImage.add( label_msg ) ;
		
		label_choose_bank.setBounds( 100 , 100 , 100 , 20 ) ;
		bgImage.add( label_choose_bank ) ;
		
		label_acc_no.setBounds( 100 , 130 , 100 , 20 ) ;
		bgImage.add( label_acc_no ) ;
		
		// setting combo box and text field bounds
		combo_bank_list.setBounds( 210 , 100 , 220 , 20 ) ;
		bgImage.add( combo_bank_list ) ;
		
		tf_account_no.setBounds( 210 , 130 , 220 , 20 ) ;
		bgImage.add( tf_account_no ) ;
		
		// setting button bounds
		btn_close_account.setBounds( 210 , 180 , 120 , 25 ) ;
		bgImage.add( btn_close_account ) ;
		btn_close_account.addActionListener( this ) ;
		
		btn_exit.setBounds( 350 , 180 , 80 , 25 ) ;
		bgImage.add( btn_exit ) ;
		btn_exit.addActionListener( this ) ;
		
		frame.setVisible( true ) ;
	}
	
	public void actionPerformed( ActionEvent ae )
	{
		if( ae.getSource() == btn_exit )
		{
			new BankFrame().frame.setVisible( true ) ;	// Constructor of Bank class
			frame.setVisible( false ) ;
		}
		
		else
		{
			String account_num =  tf_account_no.getText().toUpperCase() ;
			
			try
			{
				if( tf_account_no.equals( "" ) || combo_bank_list.getSelectedIndex() == 0 )
				{
					JOptionPane.showMessageDialog( null , "Please fill all the details" ) ;
				}
				
				else
				{
					ConnectionJDBC conn = new ConnectionJDBC() ;
					
					ResultSet rs ;
					
					String bank = "" ;
					
					int bank_index = combo_bank_list.getSelectedIndex() ;
					
					switch( bank_index )
					{
						case 1:
							bank = "SBI" ;
							break ;
							
						case 2:
							bank = "KOTAK" ;
							break ;
						
						case 3:
							bank = "PNB" ;
							break ;
						
						case 4:
							bank = "HDFC" ;
							break ;
							
						default:
							bank = "No such bank found" ;
							break ;
					}
					
					rs = conn.state.executeQuery( "SELECT * FROM " + bank + " WHERE ACCOUNT_NUMBER = '" + account_num + "'" ) ;
					
					if( rs.next() )
					{
						String account_number = rs.getString( "ACCOUNT_NUMBER" ).toUpperCase() ;
						String acc_holder = rs.getString( "NAME" ) ;
						int status = rs.getInt( "STATUS" ) ;
						
						if( account_number.equals( account_num ) )
						{
							if( status == 1 )
							{
								String update_query = "UPDATE " + bank + " SET STATUS = 0 WHERE ACCOUNT_NUMBER = '" + account_num + "'" ; 
								
								conn.state.executeUpdate( update_query ) ;
								
								JOptionPane.showMessageDialog( null ,  "Hello " + acc_holder + " , your account has been deactivated" );
							}
							
							else if( status == 0 )
							{
								JOptionPane.showMessageDialog( null , "Account has been already deactivated!!" ) ;
//								break ;
							}
						}
						
//						else
//						{
//							JOptionPane.showMessageDialog( null , "Invalid Account Number!!" ) ;
//							break ;
//						}
					}
					
					else
					{
						JOptionPane.showMessageDialog( null , "Invalid Account Number!!" ) ;
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
		CloseAccount closeAcc = new CloseAccount() ;
	}
}
