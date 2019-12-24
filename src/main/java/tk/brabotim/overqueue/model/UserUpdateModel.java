package tk.brabotim.overqueue.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.Role;
import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.entity.dto.UserDTO;
import tk.brabotim.overqueue.repository.RoleRepository;
import tk.brabotim.overqueue.service.UserService;

public class UserUpdateModel {
    
    private static final Logger LOG = Logger.getLogger(UserUpdateModel.class);
    
    private static final short ACTIVE = 1;
    private static final long USER_ID = 1L;
    private static final long ADMIN_ID = 2L;
    private static final String ADMIN = "ADMIN";

    private UserService userService;
    private List<Role> roles = new ArrayList<>();
    
    private RoleRepository roleRepository;
    
    @Autowired
    public UserUpdateModel(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public ModelAndView updateModel(UserDTO userDTO) {
        Optional<User> userToBeFound = userService.findById(userDTO.getId());

        if (!userToBeFound.isPresent()) {
            return new ModelAndView("redirect:/admin/manager/user?errorUserNotFound");
        }
        
        User userToBeUpdated = userToBeFound.get();
        
        copyFromDtoToEntity(userDTO, userToBeUpdated);

       try {
           changeRoles(userDTO, userToBeUpdated);
           
        } catch (IllegalTransactionStateException e) {
            LOG.error(e);
            return new ModelAndView("redirect:/admin/manager/user?errorParameters");
        }

        userToBeUpdated.setStatus(ACTIVE);

        userService.save(userToBeUpdated);

        return new ModelAndView("redirect:/admin/manager/user?success");
    }
    
    public void copyFromDtoToEntity(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
        user.setRoles(userDTO.getRoles());
        user.setUsername(userDTO.getUsername());
    }
    
    public void changeRoles(UserDTO userDTO, User userToBeChanged) {
        Optional<Role> userRole = roleRepository.findById(USER_ID);
        Optional<Role> adminRole = roleRepository.findById(ADMIN_ID);

        if (userRole.isPresent() && adminRole.isPresent()) {

            roles.add(userRole.get());

            if (userDTO.getRole().equalsIgnoreCase(ADMIN)) {
                roles.add(adminRole.get());
            }
            
            userToBeChanged.setRoles(roles);
        } else {
            throw new IllegalTransactionStateException("Fail to search for roles in database");
        }
    }
}
