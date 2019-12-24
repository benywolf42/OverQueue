package tk.brabotim.overqueue.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.entity.dto.UserDTO;
import tk.brabotim.overqueue.model.UserCreationModel;
import tk.brabotim.overqueue.model.UserUpdateModel;
import tk.brabotim.overqueue.repository.RoleRepository;
import tk.brabotim.overqueue.repository.UserRepository;
import tk.brabotim.overqueue.service.UserService;

@Controller
public class AdminController {
	
    private static final Logger LOG = LogManager.getLogger(AdminController.class);
    
	private UserService userService;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
    @Autowired
    public AdminController(UserService userService, UserRepository userRepository, 
            RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
	
	@GetMapping("admin/relatorio")
    public ModelAndView adminRel() {
	    LOG.info("Request method: GET admin/relatorio");

        return new ModelAndView("admin/relatorio");
    }
	
	@GetMapping("admin/relatorioEng")
    public ModelAndView adminRelEng() {
	    LOG.info("Request method: GET admin/relatorioEng");

        return new ModelAndView("admin/relatorioEng");
    }
	
	@GetMapping("admin/manager/user")
	public ModelAndView homeUserPage() {
		LOG.info("Request method: GET /user");
		
		ModelAndView view = new ModelAndView("admin/user");
		
		List <User> users = userService.findAllActive();
		
		for(User user : users) {
			LOG.info(user.getRoles());
			if(user.getRoles().size() > 1 ) {
				user.setRole("ADMIN");
			}else{
				user.setRole("USER");
			}
		}
		
		view.addObject("users", users);
		view.addObject("user", new User());
		
		return view;
	}
	
	@PostMapping("/manager/user/add")
    public ModelAndView addUser(@ModelAttribute("user") @Valid  UserDTO userDTO, BindingResult result) {
	    LOG.info("Request method: POST /user/add");
	    
		if (result.hasErrors()) {
			return new ModelAndView("redirect:/admin/manager/user?errorParameters");
		}
		
		return new UserCreationModel(userService).createNewUser(userDTO);
    }
	
	@PostMapping("/manager/user/delete")
	public ModelAndView deleteUser(@ModelAttribute("user")  @Valid UserDTO userDTO, BindingResult result) {
	    LOG.info("Request method: POST /user/delete");
		
		Optional<User> user = userService.findById(userDTO.getId());
		if (!user.isPresent()) {
		    LOG.info("User not found");
		    return new ModelAndView("redirect:/admin/manager/user?errorParameters");
		} 

		user.get().setStatus((short) 0);
	    userRepository.save(user.get());
		
		return new ModelAndView("redirect:/admin/manager/user?DeleteSuccess");
	}
	
	@PostMapping("/user/update")
	public ModelAndView updateUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
	    LOG.info("Request method: POST /user/update");
	    LOG.info("/user/update NAME " + userDTO.getName());
	    
		if (result.hasErrors()) {
			LOG.info("Erros " + result.getFieldErrors());
			return new ModelAndView("redirect:/admin/manager/user?errorParameters");
		}
		
		return new UserUpdateModel(userService, roleRepository).updateModel(userDTO);
	}
	
	@GetMapping("admin/usuario")
	public ModelAndView addUserForm() {
	    LOG.info("Request method: GET /admin/usuario");
	    
		ModelAndView view = new ModelAndView("admin/registration");
		view.addObject("user", new User());
        return view;
	}
	
	@GetMapping("admin/manager/user/findOne")
	@ResponseBody
	public User findOneUser(Long id) {
		
		LOG.info("findOneUser "+id);
		
		return userRepository.findBySpecificFieldsById(id);
	}

}
