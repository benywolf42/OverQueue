package tk.brabotim.overqueue.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.entity.dto.UserDTO;
import tk.brabotim.overqueue.service.UserService;

public class UserModel {
    
    static final Logger logger = LogManager.getLogger(UserModel.class);

    @Autowired
    private UserService userService;
    
    private User user;
    
    public void creatingNewUser(UserDTO userDTO) {
        user = userService.findByUsername(userDTO.getUsername());
        
        if (isUserAlreadyRegistered(user) && isUserActive(user)) {
            logger.info("User already registered and inactive, activating: " + user.getUsername());
            copyFromDtoToUserEntity(userDTO, user);
            userService.save(user);
        }
        
        if (isUserAlreadyRegistered(user) && !isUserActive(user)) {
            logger.info("User already registered and active: " + user.getUsername());
        }
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
