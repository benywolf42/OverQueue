package tk.brabotim.overqueue.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.entity.dto.UserDTO;
import tk.brabotim.overqueue.service.UserService;

public class UserCreationModelTest {

    private static final String ADMIN = "ADMIN";

    private static final String PHONE_NUMBER = "phoneNumber";

    private static final String PASSWORD = "PASSWORD";

    private static final String NAME = "NAME";

    @InjectMocks private UserCreationModel userCreation;
    
    private UserService mockUserService;
    private UserDTO userDTO; 
    
    @Before
    public void setUp() {
        userDTO = new UserDTO();
        mockUserService = Mockito.mock(UserService.class);
        userCreation = new UserCreationModel(mockUserService);
    }
    
    @Test
    public void testCreateNewUserGivenAdminRoleReturnSuccess() {
        userDTO.setName(NAME);
        userDTO.setPassword(PASSWORD);
        userDTO.setPhoneNumber(PHONE_NUMBER);
        userDTO.setRole(ADMIN);
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/admin/manager/user", 
                userCreation.createNewUser(userDTO).getViewName());
    }
    
    @Test
    public void testCreateNewUserWhenAlreadyRegisteredAndActiveReturnError() {
        User user = new User();
        user.setStatus((short) 1);
        when(mockUserService.findByUsername(userDTO.getName())).thenReturn(user);
        
        assertEquals("UNEXPECTED VIEW RETURN", "redirect:/admin/manager/user?errorUsername", 
                userCreation.createNewUser(userDTO).getViewName());
    }

}
