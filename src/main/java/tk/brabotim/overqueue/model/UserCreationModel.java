package tk.brabotim.overqueue.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.entity.dto.UserDTO;
import tk.brabotim.overqueue.service.UserService;

public class UserCreationModel {
    
    static final Logger logger = LogManager.getLogger(UserCreationModel.class);

    private UserService userService;
    
    private User user;
    
    @Autowired
    public UserCreationModel(UserService userService) {
        this.userService = userService; 
    }
    
    public ModelAndView createNewUser(UserDTO userDTO) {
        user = userService.findByUsername(userDTO.getUsername());
        
        if (isUserAlreadyRegistered(user) && !isUserActive(user)) {
            logger.info("User already registered and inactive, activating: " + user.getUsername());
            copyFromDtoToUserEntity(userDTO, user);
            userService.save(user);
            return new ModelAndView("redirect:/admin/manager/user");
        }
        
        if (isUserAlreadyRegistered(user) && isUserActive(user)) {
            logger.info("User already registered and active: " + user.getUsername());
            return new ModelAndView("redirect:/admin/manager/user?errorUsername");
        }
        
        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        
        userService.save(newUser);
        return new ModelAndView("redirect:/admin/manager/user");
    }
    
    public boolean isUserAlreadyRegistered(User userInQuestion) {
        return (user != null);
    }
    
    public boolean isUserActive(User userInQuestion) {
        return (userInQuestion.getStatus() == 1);
    }
    
    public void copyFromDtoToUserEntity(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole());
    }
    
    public void enableUserStatus(User user) {
        logger.info("Activating User: {}", user.getUsername());
        user.setStatus((short) 1);
    }
    
    public void disableUserStatus(User user) {
        logger.info("Deactivating User: {}", user.getUsername());
        user.setStatus((short) 0);
    }
}
