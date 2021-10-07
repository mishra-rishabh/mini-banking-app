import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.ImageIcon ;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener ;

import javax.swing.JButton ;

class BankFrame extends JFrame implements ActionListener
{
	Container c ;
	JLabel bgImage ;
	JButton btn_openAccount , btn_deposit , btn_exit , btn_withdraw , btn_trf_funds , btn_close_account ;
	JFrame frame ;
	
	public BankFrame()
	{	
		frame = new JFrame() ;
		frame.setTitle( "Banking Application" ) ;
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
		
//		// adding buttons and label to frame
//		c = frame.getContentPane() ;	// create content pane to hold the objects
//		c.setLayout( null ) ;
		
		// label
//		JLabel header = new JLabel( "Welcome" ) ;
//		header.setBounds( 165 , 30 , 150 , 25 ) ;
//		
//		Font f = new Font( "Times New Roman" , Font.BOLD , 35 ) ;
//		header.setFont( f ) ;
//		
//		bgImage.add( header ) ;	// bg image label hai to components label me add honge rather than contentpane
		
		// button01: open account
		btn_openAccount = new JButton( "Open Account" ) ;
		btn_openAccount.setBounds( 160 , 100 , 150 , 25) ;
		btn_openAccount.addActionListener( this ) ;
		btn_openAccount.setFocusPainted( false ) ;	// removing button border
		
		// button02: deposit funds
		btn_deposit = new JButton( "Deposit Funds" ) ;
		btn_deposit.setBounds( 160 , 140 , 150 , 25 ) ;
		btn_deposit.addActionListener( this ) ;
		btn_deposit.setFocusPainted( false ) ; // removing button border
		
		// button03: withdraw
		btn_withdraw = new JButton( "Withdraw" ) ;
		btn_withdraw.setBounds( 160 , 180 , 150 , 25 ) ; 
		btn_withdraw.addActionListener( this ) ;
		btn_withdraw.setFocusPainted( false ) ; // removing button border
		
		// button04: transfer funds
		btn_trf_funds = new JButton( "Transfer Funds" ) ;
		btn_trf_funds.setBounds( 160 , 220 , 150 , 25 ) ; 
		btn_trf_funds.addActionListener( this ) ;
		btn_trf_funds.setFocusPainted( false ) ; // removing button border
		
		// button05: close account
		btn_close_account = new JButton( "Close Account" ) ;
		btn_close_account.setBounds( 160 , 260 , 150 , 25) ;
		btn_close_account.addActionListener( this ) ;
		btn_close_account.setFocusPainted( false ) ;	// removing button border
		
		// button06: exit
		btn_exit = new JButton( "Exit" ) ;
		btn_exit.setBounds( 160 , 300 , 150 , 25 ) ;
		btn_exit.addActionListener( this ) ;
		btn_exit.setFocusPainted( false ) ;
		
		// adding buttons
		bgImage.add( btn_openAccount ) ;
		bgImage.add( btn_deposit ) ;
		bgImage.add( btn_withdraw ) ;
		bgImage.add( btn_trf_funds ) ;
		bgImage.add( btn_close_account ) ;
		bgImage.add( btn_exit ) ;
		
		frame.setVisible( true ) ;
	}
	
	public void actionPerformed( ActionEvent ae )
	{
		if( ae.getSource() == btn_openAccount )
		{
			new OpenAccount().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else if( ae.getSource() == btn_deposit )
		{
			new DepositFunds().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else if( ae.getSource() == btn_close_account )
		{
			new CloseAccount().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else if( ae.getSource() == btn_withdraw )
		{
			new WithDrawFunds().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else if( ae.getSource() == btn_trf_funds )
		{
			new TransferFunds().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
		
		else if( ae.getSource() == btn_exit )
		{
			new LoginFrame().frame.setVisible( true ) ;
			frame.setVisible( false ) ;
		}
	}
}

public class Banks
{
	public static void main(String[] args)
	{
		// create object of BankFrame class to call constructor
		BankFrame bFrame = new BankFrame() ;
		
	}
}
