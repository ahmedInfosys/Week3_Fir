import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class MovieTitleGen
{
	/**
	*	@param url - the URL to read words from
	*	@return An array of words, initialized from the given URL
	*/	
	public static String[] arrayFromUrl( String url ) throws Exception
	{
		Scanner fin = new Scanner((new URL(url)).openStream());
		int count = fin.nextInt();

		String[] words = new String[count];

		for ( int i=0; i<words.length; i++ )
		{
			words[i] = fin.next();
		}
		fin.close();

		return words;
	}

	public static String  select_adjective(String sql_command) throws SQLException{
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        
        props.setProperty("user", "testdb");
        props.setProperty("password", "password");
      
        //creating connection to Oracle database using JDBC
          Connection conn = DriverManager.getConnection(url,props);
         PreparedStatement preStatement = conn.prepareStatement(sql_command);
        
        ResultSet result = preStatement.executeQuery();
        
        String Adjective= null ;
        
        while(result.next()){
        	Adjective = result.getString("Adjective");
        }
        return Adjective;
  
	}
	public static String  select_Noun(String sql_command) throws SQLException{
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
	      
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        
        props.setProperty("user", "testdb");
        props.setProperty("password", "password");
      
        //creating connection to Oracle database using JDBC
          Connection conn = DriverManager.getConnection(url,props);
         PreparedStatement preStatement = conn.prepareStatement(sql_command);
        
        ResultSet result = preStatement.executeQuery();
        

        String Noun = null ;
        
        while(result.next()){
        	Noun = result.getString("Noun");
        }

        return Noun;
      
	}
	
	
	public static  void  Store_movie(String sql_command,int ID, String Movie_Title, String Movie_description) throws SQLException{
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
	     
        //properties for creating connection to Oracle database
        Properties props = new Properties();
        
        props.setProperty("user", "testdb");
        props.setProperty("password", "password");
      
        //creating connection to Oracle database using JDBC
          Connection conn = DriverManager.getConnection(url,props);
         
 
        
        try
		{
        	//PreparedStatement prepareStatement = getConnection().prepareStatement(sql_command);
        	PreparedStatement preStatement = conn.prepareStatement(sql_command);
        	
        	preStatement.setInt(1, ID);
        	preStatement.setString(2, Movie_Title);
        	preStatement.setString(3, Movie_description);
			preStatement.executeUpdate();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}
	public static void main(String[] args) throws Exception
	{
		int ID = 0;
		Scanner key = new Scanner(System.in);
		
		String[] adjectives = arrayFromUrl("https://cs.leanderisd.org/txt/adjectives.txt");
		String[] nouns      = arrayFromUrl("https://cs.leanderisd.org/txt/nouns.txt");

		System.out.println("Myxyllplyk's Random Movie Title Generator\n");

		System.out.print("Choosing randomly from " + adjectives.length + " adjectives ");
		System.out.println("and " + nouns.length + " nouns (" + (adjectives.length*nouns.length) + " combinations).");
		
 		String Adjective  ;
		String Noun;
		
		String choice ;
		
		//Store_movie("insert into movie (ID,Title,Description) values (?,?,?)",1, Adjective + " " + Noun, "This is really " + Adjective + " Movie."  );
		while(true){
			System.out.println("Wanna insert a movie into my database? (y/n). ");
			choice = key.next();
			key.nextLine();
			if(choice.equalsIgnoreCase("y")){
				Adjective  = select_adjective("SELECT * FROM (SELECT * FROM Adjective ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1");
				Noun = select_Noun("SELECT * FROM (SELECT * FROM Noun ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum =1");
			
				Store_movie("insert into movie (ID,Title,Description) values (?,?,?)",ID++, Adjective + " " + Noun, "This is really " + Adjective + " Movie."  );
			}else break;
		}
		
		
	}

	
}