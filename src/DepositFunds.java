import javax.swing.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;
import java.sql.* ;

public class DepositFunds extends JFrame implements ActionListener
{
	JLabel label_choose_bank , label_acc_no , label_amount , label_msg ;
	JTextField tf_acc_no , tf_amount ;
	JButton btn_deposit , btn_exit ;
	JComboBox combo_bank_list ;
	JFrame frame ;
	JLabel bgImage ;
	Font f ;
	
	public DepositFunds()
	{
		// frame
		frame = new JFrame() ;
		frame.setTitle( "Deposit Funds" ) ;
		frame.setSize( 500 , 500 ) ;
		frame.setLayout( null ) ;
		frame.setLocationRelativeTo( null ) ;	// used to center the frame in screen
		frame.setResizable( false ) ;
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		
		// setting bg image of a frame 
		ImageIcon bg = new ImageIcon( "E:\\wallpaper04.jpg" ) ;
		Image img = bg.getImage() ;
		
		Image tempImg = img.getScaledInstance( 500 , 500 , Image.SCALE_SMOOTH ) ;	// to make image fit to window
		bg = new ImageIcon( tempImg ) ;
		bgImage = new JLabel( "" , bg , JLabel.CENTER ) ;
		bgImage.setBounds( 0 , 0 , 500 , 500 ) ;	// from top corner to window size
		frame.add( bgImage ) ;
		
		// labels
		label_msg = new JLabel( "Please fill the following details" ) ;
		f = new Font( "Arial" , Font.PLAIN , 18 ) ;
		label_msg.setFont( f ) ;
		
		label_choose_bank = new JLabel( "Choose Bank:" ) ;
		label_acc_no = new JLabel( "Account Number:" ) ;
		label_amount = new JLabel( "Amount:" ) ;
		
		// text fields
		tf_acc_no = new JTextField() ;
		tf_amount = new JTextField() ;
		
		// adding items into combo box
		String []bank_list = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list = new JComboBox( bank_list ) ;
		
		// buttons
		btn_deposit = new JButton( "Deposit" ) ;
		btn_exit = new JButton( "Exit" ) ;
		
		// setting label bounds
		label_msg.setBounds( 120 , 20 , 300 , 25 ) ;
		bgImage.add( label_msg ) ;
		
		label_choose_bank.setBounds( 100 , 100 , 100 , 20 ) ;
		bgImage.add( label_choose_bank ) ;
		
		label_acc_no.setBounds( 100 , 130 , 100 , 20 ) ;
		bgImage.add( label_acc_no ) ;
		
		label_amount.setBounds( 100 , 160 , 100 , 20 ) ;
		bgImage.add( label_amount ) ;
		
		// setting combo box and text field bounds
		combo_bank_list.setBounds( 210 , 100 , 220 , 20 ) ;
		bgImage.add( combo_bank_list ) ;
		
		tf_acc_no.setBounds( 210 , 130 , 220 , 20 ) ;
		bgImage.add( tf_acc_no ) ;
		
		tf_amount.setBounds( 210 , 160 , 220 , 20 ) ;
		bgImage.add( tf_amount ) ;
		
		// setting button bounds
		btn_deposit.setBounds( 210 , 220 , 100 , 25 ) ;
		bgImage.add( btn_deposit ) ;
		btn_deposit.addActionListener( this ) ;
		
		btn_exit.setBounds( 330 , 220 , 100 , 25 ) ;
		bgImage.add( btn_exit ) ;
		btn_exit.addActionListener( this ) ;
		
		frame.setVisible( true ) ;
	}
	
	public void actionPerformed( ActionEvent ae )
	{
		if( ae.getSource() == btn_exit )
		{
			new BankFrame().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else
		{
			// taking inputs
			String account_num = tf_acc_no.getText().toUpperCase() ;
			String temp_amount = tf_amount.getText() ;
			
			try
			{
				if( temp_amount.equals( "" ) || tf_acc_no.equals( "" ) || combo_bank_list.getSelectedIndex() == 0 )
				{
					JOptionPane.showMessageDialog( null , "Please fill all the details" ) ;
				}
				
				else
				{	
					ConnectionJDBC connect = new ConnectionJDBC() ;
					
					ResultSet rs ;
					
					//int bank_index = 0 ;
					String bank = "" ;
					
					int bank_index = combo_bank_list.getSelectedIndex() ;
					
					switch ( bank_index )
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
					
					rs = connect.state.executeQuery( "SELECT * FROM " + bank + " WHERE ACCOUNT_NUMBER = '" + account_num + "'" ) ;
					
					if( rs.next() )
					{
						String account_number = rs.getString( "ACCOUNT_NUMBER" ).toUpperCase() ;
						String acc_holder = rs.getString( "NAME" ) ;
						int status = rs.getInt( "STATUS" ) ;
						
//						System.out.println( account_number ) ;
//						System.out.println( acc_holder ) ;
//						System.out.println( status ) ;
//						ResultSet status = connect.state.executeQuery( "SELECT STATUS FROM " + bank + " WHERE ACCOUNT_NUMBER = '" + account_num + "'" ) ;
						
						try
						{
							int deposit_amount = Integer.parseInt( tf_amount.getText() ) ;
							
							if( account_number.equals( account_num ) )
							{
								if( status == 1 )
								{
									String update_query = "UPDATE " + bank + " SET AMOUNT = AMOUNT + " + deposit_amount + " WHERE ACCOUNT_NUMBER = '" + account_num + "'" ;
									
									connect.state.executeUpdate( update_query ) ;
									
									JOptionPane.showMessageDialog( null , "Hello " + acc_holder + " , Rs. " + deposit_amount + " has been deposited to your account." ) ;
								}
								
								else if( status == 0 )
								{
									JOptionPane.showMessageDialog( null , "Deactivated account found!!" ) ;
								}
							}
							
//							else
//							{
//								JOptionPane.showMessageDialog( null , "Invalid Account Number!!" ) ;
//								break ;
//							}
						}
						
						catch( NumberFormatException nfe )
						{
							JOptionPane.showMessageDialog( null , "Please re-enter the amount" ) ;
//							break ;
						}
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
		DepositFunds deposit = new DepositFunds() ;
	}
}
