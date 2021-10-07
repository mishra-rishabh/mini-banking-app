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

public class TransferFunds extends JFrame implements ActionListener
{
	JLabel label_sender_bank , label_sender_acc_no , label_beneficiary_bank , label_beneficiary_acc_no , label_msg , label_amount ;
	JTextField tf_sender_account_no , tf_beneficiary_account_no, tf_amount ;
	JButton btn_transfer , btn_exit ;
	JComboBox combo_bank_list_sender , combo_bank_list_beneficiary ;
	JFrame frame ;
	JLabel bgImage ;
	Font f ;
	
	public TransferFunds()
	{
		// frame
		frame = new JFrame() ;
		frame.setTitle( "Transfer Funds" ) ;
		frame.setSize( 600 , 500 ) ;
		frame.setLayout( null ) ;
		frame.setLocationRelativeTo( null ) ;
		frame.setResizable( false ) ;
		frame.setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
		
		// setting bg image of frame
		ImageIcon bg = new ImageIcon( "E:\\wallpaper04.jpg" ) ;
		Image img = bg.getImage() ;
		
		Image tempImg = img.getScaledInstance( 600 , 500 , Image.SCALE_SMOOTH ) ;	// to make image fit to window
		bg = new ImageIcon( tempImg ) ;
		bgImage = new JLabel( "" , bg , JLabel.CENTER ) ;
		bgImage.setBounds( 0 , 0 , 600 , 500 ) ;	// from top corner to window size
		frame.add( bgImage ) ;
		
		// labels
		label_msg = new JLabel( "Please fill the following details" ) ;
		f = new Font( "Arial" , Font.PLAIN , 18 ) ;
		label_msg.setFont( f ) ;
		
		label_sender_bank = new JLabel( "Sender Bank:" ) ;
		label_sender_acc_no = new JLabel( "Sender Account Number:" ) ;
		
		label_beneficiary_bank = new JLabel( "Beneficiary Bank:" ) ;
		label_beneficiary_acc_no = new JLabel( "Beneficiary Account Number:" ) ;
		
		label_amount = new JLabel( "Amount:" ) ;
		
		// text field
		tf_sender_account_no = new JTextField() ;
		
		tf_beneficiary_account_no = new JTextField() ;
	
		tf_amount = new JTextField() ;
		
		// adding items to combo box
		String []bank_list_sender = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list_sender = new JComboBox( bank_list_sender ) ;
		
		String []bank_list_beneficiary = { "----Select----" , "State Bank of India" , "Kotak Mahindra Bank" , "Punjab National Bank" , "HDFC Bank" } ;
		combo_bank_list_beneficiary = new JComboBox( bank_list_beneficiary ) ;
		
		// buttons
		btn_transfer = new JButton( "Transfer" ) ;
		btn_exit = new JButton( "Exit" ) ;
		
		// setting label bounds
		label_msg.setBounds( 170 , 20 , 300 , 25 ) ;
		bgImage.add( label_msg ) ;
		
		label_sender_bank.setBounds( 80 , 100 , 100 , 20 ) ;
		bgImage.add( label_sender_bank ) ;
		
		label_sender_acc_no.setBounds( 80 , 130 , 170 , 20 ) ;
		bgImage.add( label_sender_acc_no ) ;
		
		label_beneficiary_bank.setBounds( 80 , 160 , 100 , 20 ) ;
		bgImage.add( label_beneficiary_bank ) ;

		label_beneficiary_acc_no.setBounds( 80 , 190 , 170 , 20 ) ;
		bgImage.add( label_beneficiary_acc_no ) ;
		
		label_amount.setBounds( 80 , 220 , 100 , 20 ) ;
		bgImage.add( label_amount ) ;
		
		// setting combo box bounds
		combo_bank_list_sender.setBounds( 280 , 100 , 220 , 20 ) ;
		bgImage.add( combo_bank_list_sender ) ;
		
		combo_bank_list_beneficiary.setBounds( 280 , 160 , 220 , 20 ) ;
		bgImage.add( combo_bank_list_beneficiary ) ;
		
		// setting text box bounds
		tf_sender_account_no.setBounds( 280 , 130 , 220 , 20 ) ;
		bgImage.add( tf_sender_account_no ) ;
		
		tf_beneficiary_account_no.setBounds( 280 , 190 , 220 , 20 ) ;
		bgImage.add( tf_beneficiary_account_no) ;
		
		tf_amount.setBounds( 280 , 220 , 220, 20 ) ;
		bgImage.add( tf_amount ) ;
		
		// setting button bounds
		btn_transfer.setBounds( 280 , 270 , 100 , 25 ) ;
		bgImage.add( btn_transfer ) ;
		btn_transfer.addActionListener( this ) ;
		
		btn_exit.setBounds( 400 , 270 , 100 , 25 ) ;
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
			String account_num_sender =  tf_sender_account_no.getText().toUpperCase() ;
			String account_num_beneficiary = tf_beneficiary_account_no.getText().toUpperCase() ;
			
			try
			{
				if( tf_sender_account_no.equals( "" ) || tf_beneficiary_account_no.equals( "" ) || 
						combo_bank_list_sender.getSelectedIndex() == 0 || combo_bank_list_beneficiary.getSelectedIndex() == 0 || tf_amount.equals( "" ) )
				{
					JOptionPane.showMessageDialog( null , "Please fill all the details" );
				}
				
				else
				{
					ConnectionJDBC conn1 = new ConnectionJDBC() ;
					ConnectionJDBC conn2 = new ConnectionJDBC() ;
					
					ResultSet rs_sender , rs_beneficiary ;
					
					String bank_sender = "" ;
					String bank_beneficiary = "" ;
					
					int bank_index_sender = combo_bank_list_sender.getSelectedIndex() ;
					int bank_index_beneficiary = combo_bank_list_beneficiary.getSelectedIndex() ;
					
//					if( bank_index_sender == 1 && bank_index_beneficiary == 1 )
//					{
//						bank = "SBI" ;
//					}
//					
//					else if( bank_index_sender == 2 && bank_index_beneficiary == 2 )
//					{
//						bank = "KOTAK" ;
//					}
//					
//					else if( bank_index_sender == 3 && bank_index_beneficiary == 3 )
//					{
//						bank = "PNB" ;
//					}
//					
//					else if( bank_index_sender == 4 && bank_index_beneficiary == 4 )
//					{
//						bank = "HDFC" ;
//					}
//					
//					else
//					{
//						bank = "No such bank found" ;
//					}
					
					switch( bank_index_sender )
					{
						case 1:
							bank_sender = "SBI" ;
							break ;
							
						case 2:
							bank_sender = "KOTAK" ;
							break ;
						
						case 3:
							bank_sender = "PNB" ;
							break ;
						
						case 4:
							bank_sender = "HDFC" ;
							break ;
							
						default:
							bank_sender = "No such bank found" ;
							break ;
					}
					
					switch( bank_index_beneficiary )
					{
						case 1:
							bank_beneficiary = "SBI" ;
							break ;
							
						case 2:
							bank_beneficiary = "KOTAK" ;
							break ;
						
						case 3:
							bank_beneficiary = "PNB" ;
							break ;
						
						case 4:
							bank_beneficiary = "HDFC" ;
							break ;
							
						default:
							bank_beneficiary = "No such bank found" ;
							break ;
					}
					
					rs_sender = conn1.state.executeQuery( "SELECT * FROM " + bank_sender + " WHERE ACCOUNT_NUMBER = '" + account_num_sender + "'" ) ;
					rs_beneficiary = conn2.state.executeQuery( "SELECT * FROM " + bank_beneficiary + " WHERE ACCOUNT_NUMBER = '" + account_num_beneficiary + "'" ) ;
					
					int transfer_amount ;
					int temp_transfer_amount = 0  ;
					
					// sender
					if( rs_sender.next() && rs_beneficiary.next() )
					{
						String account_number_sender = rs_sender.getString( "ACCOUNT_NUMBER" ).toUpperCase() ;
						String account_number_beneficiary = rs_beneficiary.getString( "ACCOUNT_NUMBER" ).toUpperCase() ;
						
						String acc_holder = rs_sender.getString( "NAME" ) ;
						
						int status_sender = rs_sender.getInt( "STATUS" ) ;
						int status_beneficiary = rs_beneficiary.getInt( "STATUS" ) ;
						
						int avail_bal = rs_sender.getInt( "AMOUNT" ) ;
						
						try
						{
							transfer_amount = Integer.parseInt( tf_amount.getText() ) ;
							 temp_transfer_amount = transfer_amount ;
							
							// debit amount from sender's account
							if( account_number_sender.equals( account_num_sender ) && account_number_beneficiary.equals( account_num_beneficiary ) )
							{
								if( status_sender == 1 && status_beneficiary == 1 )
								{
									if( avail_bal > transfer_amount )
									{
										// debit
										String update_query_sender = "UPDATE " + bank_sender + " SET AMOUNT = AMOUNT - " + transfer_amount + " WHERE ACCOUNT_NUMBER = '" + account_num_sender + "'" ; 
										
										conn1.state.executeUpdate( update_query_sender ) ;
										
										// credit
										String update_query_beneficiary = "UPDATE " + bank_beneficiary + " SET AMOUNT = AMOUNT + " + temp_transfer_amount + " WHERE ACCOUNT_NUMBER = '" + account_num_beneficiary + "'" ; 
										
										conn2.state.executeUpdate( update_query_beneficiary ) ;
											
										JOptionPane.showMessageDialog( null ,  "Rs. " + transfer_amount + " , transfered successfully" ) ;
									}
									
									else if( avail_bal < transfer_amount )
									{
										JOptionPane.showMessageDialog( null , "Insufficient Balance!!" ) ;
									}
								}
								
								else if( status_sender == 0 || status_beneficiary == 0 )
								{
									JOptionPane.showMessageDialog( null , "Deactivated Account Found!!" ) ;
//									break ;
								}
							}
						}
						
						catch( NumberFormatException nfe )
						{
							JOptionPane.showMessageDialog( null , "Please re-enter the amount" ) ;
//							break ;
						}
					}
					
					// beneficiary
//					else if( rs_beneficiary.next() )
//					{
//						String account_number_beneficiary = rs_beneficiary.getString( "ACCOUNT_NUMBER" ).toUpperCase() ;
//						
//						String acc_holder = rs_beneficiary.getString( "NAME" ) ;
//						
//						int status_beneficiary = rs_beneficiary.getInt( "STATUS" ) ;
//						
////						int avail_bal = rs_beneficiary.getInt( "AMOUNT" ) ;
//						
//						try
//						{
////							int transfer_amount = Integer.parseInt( tf_amount.getText() ) ;
//							
//							// credit amount in beneficiary's account
//							if( account_number_beneficiary.equals( account_num_beneficiary ) )
//							{
//								if( status_beneficiary == 1 )
//								{
//									String update_query_beneficiary = "UPDATE " + bank_beneficiary + " SET AMOUNT = AMOUNT + " + temp_transfer_amount + " WHERE ACCOUNT_NUMBER = '" + account_num_beneficiary + "'" ; 
//									
//									conn2.state.executeUpdate( update_query_beneficiary ) ;
//										
////									JOptionPane.showMessageDialog( null ,  "Rs. " + transfer_amount + " , transfered successfully" ) ;
////									if( avail_bal > transfer_amount )
////									{
////										
////									}
////									
////									else if( avail_bal < transfer_amount )
////									{
////										JOptionPane.showMessageDialog( null , "Insufficient Balance!!" ) ;
////									}
//									
//								}
//								
//								else if( status_beneficiary == 0 )
//								{
//									JOptionPane.showMessageDialog( null , "Deactivated Account Found!!" ) ;
////									break ;
//								}
//							}
//						}
//						
//						catch( NumberFormatException nfe )
//						{
//							JOptionPane.showMessageDialog( null , "Please re-enter the amount" ) ;
////							break ;
//						}
//					}
					
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
		TransferFunds transfer = new TransferFunds() ;
	}
}
