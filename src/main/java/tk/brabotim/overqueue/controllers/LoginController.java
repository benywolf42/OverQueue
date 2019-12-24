package tk.brabotim.overqueue.controllers;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.model.ResetPasswordModel;
import tk.brabotim.overqueue.model.TotalVoiceSms;
import tk.brabotim.overqueue.repository.UserRepository;
import tk.brabotim.overqueue.service.UserService;


@Controller
public class LoginController {
    
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
    private static final Logger logger = LogManager.getLogger();
	
	@GetMapping("forgotpassword")
	public ModelAndView sendSMS() {
		logger.info("GET METHOD forgorpassword");
		ModelAndView view = new ModelAndView("forgotpassword");
		view.addObject("user", new User());
		return view;
	}
	
	@PostMapping(value = "/resetpasswordinstruction")
	public ModelAndView requestNewPasswordToken(Model model, @RequestParam String username, String phoneNumber) {
		
		logger.info("POST METHOD /resetpasswordinstruction");
		return new ResetPasswordModel(userRepository).requestNewPasswordToken(username, phoneNumber);
	}
	
	@GetMapping("reset-password")
    public ModelAndView resetPassword(Model model) {
		logger.info("GET METHOD reset-password");
		model.addAttribute("user", new User());
        return new ModelAndView("resetpassword");
    }
	
	@PostMapping("/newpassword")
    public ModelAndView changePassword(Model model, @RequestParam String username, 
            String phoneNumber, String passwordToken, String password, String passwordConfirm) {
		
		logger.info("POST METHOD /newpassword");
		return new ResetPasswordModel(userRepository).changePassword(username, phoneNumber, passwordConfirm, passwordToken);
	}
	
}
