import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class WithDrawFunds extends JFrame implements ActionListener
{
	JLabel label_choose_bank , label_acc_no , label_msg , label_amount ;
	JTextField tf_account_no , tf_amount ;
	JButton btn_withdraw , btn_exit ;
	JComboBox combo_bank_list ;
	JFrame frame ;
	JLabel bgImage ;
	Font f ;
	
	public WithDrawFunds()
	{
		// frame
		frame = new JFrame() ;
		frame.setTitle( "Withdraw Amount" ) ;
		frame.setSize( 500 , 500 ) ;
		frame.setLayout( null ) ;
		frame.setLocationRelativeTo( null ) ;
		frame.setResizable( false ) ;
		frame.setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
		
		// setting bg image of frame
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
		label_acc_no = new JLabel( "Account number:" ) ;
		label_amount = new JLabel( "Amount:" ) ;
		
		// text field
		tf_account_no = new JTextField() ;
		tf_amount = new JTextField() ;
		
		// adding items to combo box
		String []bank_list = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list = new JComboBox( bank_list ) ;
		
		// buttons
		btn_withdraw = new JButton( "Withdraw" ) ;
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
		
		tf_account_no.setBounds( 210 , 130 , 220 , 20 ) ;
		bgImage.add( tf_account_no ) ;
		
		tf_amount.setBounds( 210 , 160 , 220, 20 ) ;
		bgImage.add( tf_amount ) ;
		
		// setting button bounds
		btn_withdraw.setBounds( 210 , 220 , 100 , 25 ) ;
		bgImage.add( btn_withdraw ) ;
		btn_withdraw.addActionListener( this ) ;
		
		btn_exit.setBounds( 330 , 220 , 100 , 25 ) ;
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
					JOptionPane.showMessageDialog( null , "Please fill all the details" );
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
						int avail_bal = rs.getInt( "AMOUNT" ) ;
						
						try
						{
							int withdraw_amount = Integer.parseInt( tf_amount.getText() ) ;
							
							if( account_number.equals( account_num ) )
							{
								if( status == 1 )
								{
									if( avail_bal > withdraw_amount )
									{
										String update_query = "UPDATE " + bank + " SET AMOUNT = AMOUNT - " + withdraw_amount + " WHERE ACCOUNT_NUMBER = '" + account_num + "'" ; 
										
										conn.state.executeUpdate( update_query ) ;
											
										JOptionPane.showMessageDialog( null ,  "Rs. " + withdraw_amount + " , debited successfully" );
									}
									
									else if( avail_bal < withdraw_amount )
									{
										JOptionPane.showMessageDialog( null , "Insufficient Balance!!" ) ;
									}
									
								}
								
								else if( status == 0 )
								{
									JOptionPane.showMessageDialog( null , "Deactivated Account Found!!" ) ;
//									break ;
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
		WithDrawFunds withdraw = new WithDrawFunds() ;
	}
}
