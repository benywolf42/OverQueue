package tk.brabotim.overqueue.controllers;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.Client;
import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.model.TotalVoiceSms;
import tk.brabotim.overqueue.service.UserService;


@Controller
public class HomeController {
	
    private static final Logger logger = LogManager.getLogger();

	@RequestMapping(value={"/","/home"}, method=RequestMethod.GET)
    public ModelAndView home(Model model) {
	    logger.info("Request method: GET /");
        ModelAndView view = new ModelAndView("home");
        model.addAttribute("client", new Client());
        return view;
    }
}
