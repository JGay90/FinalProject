import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.revature.dao.usersDAO;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.ui.*;
import com.revature.util.ConnectionUtil;

public class TestMainMenu {

	public static usersDAO userDAO;
	public static UserService mockUServ;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	public static MainMenu mockMainMenu;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userDAO = mock(usersDAO.class);
		mockConnection = mock(Connection.class);
		mockUServ = mock(UserService.class);
		when(userDAO.getUserByName(eq("abc123"), eq(mockConnection))).thenReturn(new User("Jane", "Doe","abc123","apassword"));
		mockMainMenu = mock(MainMenu.class);
		}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelectUserMenu() throws SQLException {
		mockMainMenu.selectUserMenu();
	}
	@Test
public void testSelectEmployeeMenu() throws SQLException {
	mockMainMenu.selectEmployeeMenu();
}
	@Test
	public void testSelectNewMenu() throws SQLException
	{
		mockMainMenu.selectNewMenu();
	}
}
