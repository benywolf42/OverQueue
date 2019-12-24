package tk.brabotim.overqueue.model;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import tk.brabotim.overqueue.entity.User;
import tk.brabotim.overqueue.repository.UserRepository;

public class ResetPasswordModel {

	private static final int PASSWORD_EXPIRATION_TIME = 30;

	private static final Logger LOG = LogManager.getLogger();

	private UserRepository userRepository;

	@Autowired
	public ResetPasswordModel(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ModelAndView requestNewPasswordToken(String username, String phoneNumber) {

		try {
			User user = userRepository.findByUsername(username);
			if (user.getPhoneNumber().compareTo(phoneNumber) != 0) {
				LOG.info("Usuario e telefone nao sao equivalentes");
				return new ModelAndView("redirect:forgotpassword?error");
			}

			String passwordToken = createToken();
			setUserPasswordToken(user, passwordToken);
			sendSMS(user, phoneNumber, passwordToken);

			userRepository.save(user);
			return new ModelAndView("resetpasswordinstruction");
		} catch (Exception e) {
			LOG.info("Fail to send SMS");
			LOG.info(e);
			return new ModelAndView("redirect:forgotpassword?error");
		}
	}

	public ModelAndView changePassword(String username, String phoneNumber, String password, String passwordToken) {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		
		try {
			User user = userRepository.findByUsername(username);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			long nowInMiliseconds = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			long creationTimeInMiliseconds = user.getPasswordTokenCreatedTime().atZone(ZoneId.systemDefault())
					.toInstant().toEpochMilli();
			long passwordExpirationTime = nowInMiliseconds - creationTimeInMiliseconds;
			passwordExpirationTime = TimeUnit.MILLISECONDS.toMinutes(passwordExpirationTime);

			if (user.getPhoneNumber().compareTo(phoneNumber) == 0 
				&& bCryptPasswordEncoder.matches(passwordToken, user.getPasswordToken()) 
				&& passwordExpirationTime < PASSWORD_EXPIRATION_TIME
				&& !passwordToken.isEmpty()) {
				
				user.setPassword(bCryptPasswordEncoder.encode(password));
				user.setPasswordToken("");
				userRepository.save(user);
				return new ModelAndView("redirect:/login?successOnPasswordReset");
				
			} else {
				LOG.info("Password comparison error");
				return new ModelAndView("redirect:reset-password?error");
			}
		} catch (Exception e) {
			LOG.info("Invalid password parameters");
			LOG.info(e);
			return new ModelAndView("redirect:reset-password?error");
		}
		
	}

	public void setUserPasswordToken(User user, String passwordToken) {
		LocalDateTime passwordTokenCreatedTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		user.setPasswordToken(new BCryptPasswordEncoder().encode(passwordToken));
		user.setPasswordTokenCreatedTime(passwordTokenCreatedTime);
	}

	public String createToken() {
		return Integer.toString(new SecureRandom().nextInt(10000) + 1);
	}

	public void sendSMS(User user, String phoneNumber, String passwordToken) throws Exception {
		TotalVoiceSms sms = new TotalVoiceSms();
		String message = "OverQueue - código de verificação, válido por 30 minutos: " + passwordToken
				+ ". Troque sua senha em https://overqueue.tk/reset-password";
		sms.sendSms(phoneNumber, message);
		LOG.info("Solicitação de alteração de senha do usuário " + user.getUsername() + ". SMS enviado para o telefone "
				+ phoneNumber);
	}
}
