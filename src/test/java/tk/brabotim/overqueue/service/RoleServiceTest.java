package tk.brabotim.overqueue.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import tk.brabotim.overqueue.entity.Role;

public class RoleServiceTest {

	RoleService roleService;
	Role role = new Role();
	
	@Before
	public void before() {
		roleService = mock(RoleService.class);
	}
	
	@Test
	public void testFindaAll() {
		List<Role> rolesList = new ArrayList<Role>();
		when(roleService.findaAll()).thenReturn(rolesList);
		assertEquals(rolesList, roleService.findaAll());
	}

	@Test
	public void testFindById() {
		Optional<Role> roleFoundById = null;
		when(roleService.findById(1L)).thenReturn(roleFoundById);
		assertEquals(roleFoundById, roleService.findById(1L));
	}

}
