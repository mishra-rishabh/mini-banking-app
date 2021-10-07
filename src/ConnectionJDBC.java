import java.sql.* ;

public class ConnectionJDBC
{
	Connection connect ;
	Statement state ;
	
	public ConnectionJDBC()
	{
		try
		{
			// load the driver class
			Class.forName( "oracle.jdbc.driver.OracleDriver" ) ;
			
			// create the connection object
			connect = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe" , "your_username" , "your_password" ) ;
			
			// create the statement object
			state = connect.createStatement() ;
		}
		
		catch( Exception e )
		{
			System.out.println( e ) ;
		}
	}
}
