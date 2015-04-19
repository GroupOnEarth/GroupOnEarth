import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;

public class DBTest {

	private DBAccess db;
	private static boolean isCreated = false;

	@Before
	public void setUp() throws Exception 
	{
		if(!isCreated){
			createDB.createDataBase();
			isCreated = true;
		}
		db = new DBAccess();
	}



	@After
	public void tearDown() throws Exception 
	{
		//db.closeConnection();
	}

	/**
	 * Test #1    
	 */
	@Test
	public void test_saved_information() throws SQLException 
	{
		ResultSet rs = db.sqlQuery("SELECT Count(*) FROM systemuser");
		if(rs.next())
			assertEquals(2, rs.getInt(1));
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
	 * Test #5
	 * Tests adding System User Client to the database.
	 * The test will add SystemUser Client "MosheU".
	 * Expect TRUE- "MosheU" was also added to 'Client' table.
	 */
	@Test
	public void test_add_client_cascade() throws SQLException {
		db.addSystemUser("MosheU", "1234567", "1111", "moshe", "unger", "12345", "", "Client", "Male", 0, new Date(1000));
		ResultSet rs = db.sqlQuery("Select * FROM Client WHERE UserName='MosheU'");
		rs.next();
		assertEquals("MosheU", rs.getString(1));
		db.deleteUser("MosheU");
	}
	/**
	 * Test #6
	 * Tests deleting System User Client from the database.
	 * The test will delete SystemUser Client "MosheU".
	 * Expect TRUE- "MosheU" was also deleted from 'Client' table.
	 */
	@Test
	public void test_delete_client_cascade() throws SQLException {
		db.addSystemUser("MosheU", "1234567", "1111", "moshe", "unger", "12345", "", "Client", "Male", 0, new Date(1000));
		db.deleteUser("MosheU");
		ResultSet rs = db.sqlQuery("Select Count(*) FROM Client");
		if(rs.next()){
			assertEquals(0, rs.getInt(1));
		}
	}
	/**
	 * Test #7
	 * Tests updating user's password.
	 * The test will add SystemUser "Moshe" with password '12345'.
	 * The test will update the password using updatePassword function to '99999'.
	 * Expect TRUE- The password of "Moshe" was updated.
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
	 * Test #8
	 * Tests adding coupon_purchase.
	 * The test will add SystemUser "Moshe" with password '12345'.
	 * The test will add a purchase to "Moshe".
	 * Expect TRUE- The purchase was added to database.
	 */
	@Test
	public void test_add_coupon_purchase() throws SQLException {
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		db.addPurchase("Moshe", "11111");
		ResultSet rs = db.sqlQuery("SELECT * FROM couponpurchases");
		if(rs.next()){
			String queryResult = rs.getString(1) + " " + rs.getString(2);
			assertEquals("Moshe 11111", queryResult);
		}
		db.deletePurchase("Moshe", "11111");
		db.deleteUser("Moshe");

	}

	/**
	 * Test #9
	 * Tests deleting user's coupon_purchase.
	 * The test will delete purchase from the database. Username-Moshe couponID-11111
	 * Expect TRUE- After delete the are 0 purchases in database.
	 */
	@Test
	public void test_delete_purchase() throws SQLException {
		db.addSystemUser("Moshe", "Unger", "12345", "Moshe", "Unger", "054-5555555", "mosheun@post.bgu.ac.il", "Admin");
		db.addPurchase("Moshe", "11111");
		db.deletePurchase("Moshe", "11111");
		ResultSet rs = db.sqlQuery("SELECT Count(*) FROM couponpurchases");
		if(rs.next())
			assertEquals(0, rs.getInt(1));
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


}


