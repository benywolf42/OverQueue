package tk.brabotim.overqueue.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import tk.brabotim.overqueue.entity.User;

public class UserServiceTest {

	private UserService userService;
	private User user = new User();
	
	@Before
	public void before() {
		userService = mock(UserService.class);
	}
	
	@Test
	public void testFindAll() {
		List<User> usersList = new ArrayList<User>();
		when(userService.findAll()).thenReturn(usersList);
		assertEquals(usersList, userService.findAll());
	}

	@Test
	public void testFindById() {
		Optional<User> userFoundById = null;
		when(userService.findById(1L)).thenReturn(userFoundById);
		assertEquals(userFoundById, userService.findById(1L));
	}

	@Test
	public void testFindByUsername() {
		when(userService.findByUsername("username")).thenReturn(user);
		assertEquals(user, userService.findByUsername("username"));
	}

}
