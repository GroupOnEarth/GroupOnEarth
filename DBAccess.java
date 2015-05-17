
import java.sql.*;


/**
 * DBAccess is a class which provides access to the database.
 * DBAccess contains:
 * (1) Connection to the database.
 * (2) INSERT/DELETE/UPDATE functions to the database.
 * 
 * @author GroupOnEarth
 */

public class DBAccess {

	private static final String url = "jdbc:mysql://grouponearth.cokwpobid1ly.us-west-2.rds.amazonaws.com:3306/GroupOnEarth";
	private static final String user = "shahaf";
	private static final String password = "grouponearth";
	private static Connection con;

	/**
	 * DBAccess constructor. 
	 * Provides the connection to the database.
	 * 
	 * @param none.
	 * 
	 * @pre: none.
	 * @post: @param con contains connection string to the database.
	 */
	public DBAccess()
	{

			try
			{	
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, password);

			}
			catch(Exception e)
			{
				//System.out.println("DBA ACCESS: Exception cought- Connection Error");
				e.printStackTrace();
			}
		
	}


	
	
	/**
	 * Add new SystemUser to the database. 
	 * 
	 * @param SystemUser's parameters.
	 * 
	 * @pre: @param _UserName doen't exists in database.
	 * @post: SystemUser.size = @pre SystemUser.size+1
	 */
	public boolean addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType)
	{
		CallableStatement addUser;
		try 
		{
			addUser = (CallableStatement) con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
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

	/**
	 * Add new SystemUser to the database. 
	 * Add suitable client to the databse. 
	 * This is an overloading function for adding a Client SystemUser.
	 * 
	 * @param SystemUser's parameters.
	 * 
	 * @pre: @param _UserName doen't exists in database.
	 * @post: SystemUser.size = @pre SystemUser.size+1, Cliet.size= @pre Client.size+1
	 */
	public boolean addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType, String _Gender, Date _DateOfBirth)
	{
		CallableStatement addUser;
		try 
		{
			addUser = (CallableStatement) con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
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
				CallableStatement addClient;
				addClient = (CallableStatement) con.prepareCall("call add_client (?,?,?)");
				addClient.setString(1, _UserName);
				addClient.setString(2, _Gender);
				addClient.setDate(3, _DateOfBirth);
				ans = ans & (addClient.execute());
				return ans;

			}
			return ans;
		} 
		catch (SQLException e) 
		{
			return false;
		}
	}

	/**
	 * The function getClient retrieves information about a given Client.   
	 * 
	 * @param _UserName
	 * 
	 * @pre: @param _UserName exists in database.
	 * @post: none.
	 */
	public ResultSet getClient(String _UserName)
	{
		CallableStatement getC;
		try 
		{
			getC = (CallableStatement) con.prepareCall("call get_client (?)");
			getC.setString(1, _UserName);
			return getC.executeQuery();

		} 
		catch (SQLException e) 
		{
			return null;
		}
	}

	/**
	 * The function getUser retrieves information about a given SysemUser.   
	 * 
	 * @param _UserName
	 * 
	 * @pre: @param _UserName exists in database.
	 * @post: none.
	 */
	public ResultSet getUser(String _UserName)
	{
		CallableStatement getU;
		try 
		{
			getU = (CallableStatement) con.prepareCall("call get_user (?)");
			getU.setString(1, _UserName);
			return getU.executeQuery();

		} 
		catch (SQLException e) 
		{
			return null;
		}
	}

	/**
	 * Delete SystemUser from the database. 
	 * 
	 * @param _UserName
	 * 
	 * @pre: @param _UserName  exists in database.
	 * @post: SystemUser.size = @pre SystemUser.size-1
	 */
	public boolean deleteUser(String _UserName)
	{
		CallableStatement delUser;
		try 
		{
			delUser = (CallableStatement) con.prepareCall("call delete_user (?)");
			delUser.setString(1, _UserName);
			return delUser.execute();

		} 
		catch (SQLException e) 
		{
			return false;
		}
	}

	/**
	 * Updating SystemUser password. 
	 * The update will be performed only if _oldPass is correct.
	 * 
	 * @param _UserName
	 * @param _oldPass
	 * @param _newPass
	 * 
	 */
	public boolean updatePassword(String _UserName, String _oldPass, String _newPass){
		CallableStatement updatePass;
		try
		{
			updatePass = (CallableStatement) con.prepareCall("call update_password (?, ?, ?)");
			updatePass.setString(1, _UserName);
			updatePass.setString(2, _oldPass);
			updatePass.setString(3, _newPass);
			return updatePass.execute();

		}
		catch (SQLException e)
		{
			return false;
		}
	}


	/**
	 * Add new Purchase to the database. 
	 * 
	 * @param _userName.
	 * @param _couponID- coupon to be purchased
	 * 
	 * @pre: @param _UserName and @param _couponID exists in database. 
	 * @post: couponpurchases.size = @pre couponpurchases.size+1
	 */
	public boolean addPurchase(String _userName, String _couponID){
		CallableStatement addPurchase;
		try{
			addPurchase = (CallableStatement) con.prepareCall("call add_coupon_purchase(?,?)");
			addPurchase.setString(1, _userName);
			addPurchase.setString(2, _couponID);
			return addPurchase.execute();
		}
		catch(SQLException e){
			return false;
		}
	}

	/**
	 * Delete a purchase from the database. 
	 * 
	 * @param _userName
	 * @param _couponID
	 * 
	 * @pre: the purchase exists in database.
	 * @post: couponpurchases.size = @pre couponpurchases.size-1
	 */
	public boolean deletePurchase(String _userName, String _couponID){
		CallableStatement deletePurchase;
		try{
			deletePurchase = (CallableStatement) con.prepareCall("call delete_coupon_purchase(?,?)");
			deletePurchase.setString(1, _userName);
			deletePurchase.setString(2, _couponID);
			return deletePurchase.execute();
		}
		catch(SQLException e){
			return false;
		}
	}

	/**
	 * Approving a coupon.
	 * The SQL query will set the coupon 'isApproved' field to 1.
	 * 
	 * @param _couponID
	 * 
	 * @pre: the coupon exists in database.
	 * @post: couponpurchases.size = @pre couponpurchases.size-1
	 */
	public boolean approveCoupon(String _couponID)
	{
		CallableStatement approveCoupon;
		try{
			approveCoupon = (CallableStatement) con.prepareCall("call approve_coupon(?)");
			approveCoupon.setString(1, _couponID);
			return approveCoupon.execute();
		}
		catch(SQLException e){
			return false;
		}
	}

	/**
	 * De-Approving a coupon.
	 * The SQL query will set the coupon 'isApproved' field to 0.
	 * 
	 * @param _couponID
	 * 
	 * @pre: the coupon exists in database.
	 * @post: couponpurchases.size = @pre couponpurchases.size-1
	 */
	public boolean deapproveCoupon(String _couponID)
	{
		CallableStatement approveCoupon;
		try{
			approveCoupon = (CallableStatement) con.prepareCall("call deapprove_coupon(?)");
			approveCoupon.setString(1, _couponID);
			return approveCoupon.execute();
		}
		catch(SQLException e){
			return false;
		}
	}


	/**
	 * Generic function to execute a SQL query.
	 * 
	 * @param Query- String- a SQL query format.
	 * 
	 * @pre: none.
	 * @post: query was executed.
	 * 
	 * @return: ResultSet- the result of the query
	 */
	public ResultSet sqlQuery(String query) throws SQLException
	{
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

}
