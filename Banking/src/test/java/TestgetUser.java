
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.dao.usersDAO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class TestgetUser {
	
	public static usersDAO userDAO;
	public static UserService mockUServ;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userDAO = mock(usersDAO.class);
		mockConnection = mock(Connection.class);
		mockUServ = mock(UserService.class);
		when(userDAO.getUserByName(eq("abc123"), eq(mockConnection))).thenReturn(new User("Jane", "Doe","abc123","apassword"));
		
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
	public void test() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			User actual = userDAO.getUserByName("abc123",mockConnection);
			
			User expected = new User("Jane", "Doe","abc123","apassword");
		
		
		assertEquals(expected, actual);
		}
		
	}
	@Test(expected = UserNotFoundException.class)
	public void testInvalidUserName() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			User actual = mockUServ.getUserByUsername("abc1234");
		}

}
}

