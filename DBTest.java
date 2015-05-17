import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DBTest {

	private DBAccess db;

	@Before
	public void setUp() throws Exception 
	{
		db = new DBAccess();
	}



	@After
	public void tearDown() throws Exception 
	{
		//db.closeConnection();
	}



	/**
	 * Test #2
	 * Tests adding System User to the database.
	 * The test will add SystemUser "Moshe".
	 * After adding the user, the test will retrieve SystemUser "Moshe" information using getUser.
	 * Expect TRUE- SystemUser "Moshe" is in the database- "Moshe" was retrieved.      
	 */
	@Test
	public void test_addSystemUser() throws SQLException 
	{
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		ResultSet rs = db.getUser("Moshe");
		if(rs.next())
			assertEquals("Moshe", rs.getString(1));
		db.deleteUser("Moshe");
	}

	/**
	 * Test #3
	 * Tests adding System User to the database with the same UserName (key).
	 * The test will add SystemUser with UserName "Moshe" twice.
	 * Expect TRUE- There is only 1 SystemUser with UserName "Moshe" in database.
	 */
	@Test
	public void test_add_same_user() throws SQLException 
	{
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		ResultSet rs = db.getUser("Moshe");
		rs.last();
		assertEquals(1, rs.getRow());
		db.deleteUser("Moshe");
	}

	/**
	 * Test #4
	 * Tests deleting System User from the database.
	 * The test will delete SystemUser with UserName "Moshe".
	 * Expect TRUE- There are 0 SystemUser with UserName "Moshe" in database.
	 */
	@Test
	public void test_delete_user() throws SQLException 
	{
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		db.deleteUser("Moshe");
		ResultSet rs = db.getUser("Moshe");
		rs.last();
		assertEquals(0, rs.getRow());

	}


	/**
	 * Test #6
	 * Tests deleting System User Client from the database.
	 * The test will delete SystemUser Client "MosheU".
	 * Expect TRUE- "MosheU" was also deleted from 'Client' table.
	 */

	@Test
	public void test_update_password() throws SQLException {
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		db.updatePassword("Moshe", "12345", "99999");
		ResultSet rs = db.sqlQuery("SELECT Password FROM systemuser WHERE userName='Moshe'");
		if(rs.next())
			assertEquals("99999", rs.getString(1));
		db.deleteUser("Moshe");
	}




	/**
	 * Test #10
	 * Tests approving a coupon.
	 * The test will approve coupon 11111.
	 * Expect TRUE- after approval, 'isApproved' field equals to 1. 
	 */
	@Test
	public void test_approve_coupon() throws SQLException {
		db.approveCoupon("11111");
		ResultSet rs = db.sqlQuery("SELECT isApproved FROM coupon");
		if(rs.next())
			assertEquals(1, rs.getInt(1));
		db.deapproveCoupon("11111");
	}
	
	@Test
	public void test_add_client() throws SQLException{
		db.addSystemUser("Moshe", "1111","1234","moshe","unger","1234","aw", "client", "male",null);
		ResultSet rs = db.sqlQuery("SELECT * FROM client WHERE UserName='Moshe'");
		try{
			rs.next();
			assertEquals(rs.getString(1), "Moshe");
		}
		catch(SQLException e){
			assertEquals(0,  1);
		}
	}


}


