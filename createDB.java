import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class createDB {
	private static final String url = "jdbc:mysql://localhost:3306/";
	private static final String user = "root";
	private static final String password = "groupOnEarth";
	private static Connection con;


	public static void createDataBase(){

		
		String s            = new String();
		StringBuffer sb = new StringBuffer();

		try
		{
			FileReader fr = new FileReader(new File("./createDBscript.txt"));
			// be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

			BufferedReader br = new BufferedReader(fr);

			while((s = br.readLine()) != null)
			{
				sb.append(s);
			}
			br.close();

			// here is our splitter ! We use ";" as a delimiter for each request
			// then we are sure to have well formed statements
			String[] inst = sb.toString().split("DELIMITER ;");

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			Statement st = con.createStatement();

			for(int i = 0; i<inst.length; i++)
			{
				// we ensure that there is no spaces before or after the request string
				// in order to not execute empty statements
				if(!inst[i].trim().equals(""))
				{
					st.executeUpdate(inst[i]);

				}
			}


			con.close();
		}
		catch(Exception e)
		{
			//System.out.println("CREATE DB: Exception cought- Connection Error");
		}

	}

}
