import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DBTest {
	private static final String url = "jdbc:mysql://127.0.0.1:3306/group_on_earth_db";  
	private static final String user = "root";  
	private static final String password = "groupOnEarth";  
	private Connection con;
	@Before
	public void setUp() throws Exception {
		try {  
			Class.forName("com.mysql.jdbc.Driver");  
			con = DriverManager.getConnection(url, user, password);  



		} catch (Exception e) { 
			System.out.println("exception cought");
		}  
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_addSystemUser() throws SQLException {
		addSysemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		ResultSet rs = getUser("Moshe");
		if(rs.next())
			assertEquals("Moshe", rs.getString(1));
		deleteUser("Moshe");

	}
	@Test
	public void test_add_same_user() throws SQLException {
		addSysemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		addSysemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		ResultSet rs = getUser("Moshe");
		rs.last();

		assertEquals(1, rs.getRow());



	}
	@Test
	public void test_delete_user() throws SQLException {
		deleteUser("Moshe");

		ResultSet rs = getUser("Moshe");
		rs.last();
		assertEquals(0, rs.getRow());
	}
	@Test
	public void test_add_client_cascade() throws SQLException {
		addSysemUser("MosheU", "1234567", "1111", "moshe", "unger", "12345", "", "Client", "Male", 0, new Date(1000));
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("Select * FROM Client WHERE UserName='MosheU'");
		rs.next();
		assertEquals("MosheU", rs.getString(1));
		deleteUser("MosheU");
	}/*
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}
	@Test
	public void test_shahaf_admin() throws SQLException {
		ResultSet rs = _st.executeQuery("SELECT UserName FROM SystemUser WHERE UserName='shahafs'");
		if(rs.next())
			assertEquals("shahafs", rs.getString(1));
	}*/

	public boolean addSysemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType)
	{
		CallableStatement addUser;
		try 
		{
			addUser = con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
			addUser.setString(1, _UserName);
			addUser.setString(2, _ID);
			addUser.setString(3, _Password);
			addUser.setString(4, _FirstName);
			addUser.setString(5, _LastName);
			addUser.setString(6, _Phone);
			addUser.setString(7, _EMail);
			addUser.setString(8, _UserType);
			return addUser.execute();

		} 
		catch (SQLException e) 
		{
			return false;
		}
	}

	public boolean addSysemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType, String _Gender, int _Location, Date _DateOfBirth)
	{
		CallableStatement addUser;
		try 
		{
			addUser = con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
			addUser.setString(1, _UserName);
			addUser.setString(2, _ID);
			addUser.setString(3, _Password);
			addUser.setString(4, _FirstName);
			addUser.setString(5, _LastName);
			addUser.setString(6, _Phone);
			addUser.setString(7, _EMail);
			addUser.setString(8, _UserType);
			boolean ans = addUser.execute();
			if(_UserType.equals("Client")){
				/*CallableStatement addClient;
				addClient = con.prepareCall("call add_client (?,?,?,?");
				addClient.setString(1, _UserName);
				addClient.setString(2, _Gender);
				addClient.setInt(3, _Location);
				addClient.setDate(4, _DateOfBirth);
				ans = ans & (addClient.execute());
				return ans;

			}*/
				Statement st = con.createStatement();
				ans = ans & st.execute("INSERT INTO Client (username, gender) VALUES('MosheU', 'Male')");
				return ans;
			}
			return ans;
		} 
		catch (SQLException e) 
		{
			return false;
		}

	}
	public ResultSet getClient(String _UserName)
	{
		CallableStatement addUser;
		try 
		{
			addUser = con.prepareCall("call get_client (?)");
			addUser.setString(1, _UserName);
			return addUser.executeQuery();

		} 
		catch (SQLException e) 
		{
			return null;
		}
	}


	public ResultSet getUser(String _UserName)
	{
		CallableStatement addUser;
		try 
		{
			addUser = con.prepareCall("call get_user (?)");
			addUser.setString(1, _UserName);
			return addUser.executeQuery();

		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	public boolean deleteUser(String _UserName)
	{
		CallableStatement addUser;
		try 
		{
			addUser = con.prepareCall("call delete_user (?)");
			addUser.setString(1, _UserName);
			return addUser.execute();

		} 
		catch (SQLException e) 
		{
			return false;
		}
	}

}
