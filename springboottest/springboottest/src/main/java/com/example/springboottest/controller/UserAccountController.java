package com.example.springboottest.controller;


import com.example.springboottest.model.entity.ConfirmationToken;
import com.example.springboottest.model.entity.Role;
import com.example.springboottest.model.entity.User;
import com.example.springboottest.model.repository.ConfirmationTokenRepository;
import com.example.springboottest.model.repository.RoleRepository;
import com.example.springboottest.model.repository.UserRepository;
import com.example.springboottest.model.service.serviceImp.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class UserAccountController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;

	// https://stackabuse.com/password-encoding-with-spring-security/
	// to encode our password
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);



	// Confirm registration
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if(token != null)
		{
			User user = userRepository.findById(token.getUserId()).get();
			user.setStatus((long) 0);
			userRepository.save(user);
			modelAndView.setViewName("accountVerified");
		}
		else
		{
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}	


	/**
	 * Display the forgot password page and form
	 */
	@RequestMapping(value="/forgot-password", method= RequestMethod.GET)
	public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}

	/**
	 * Receive email of the user, create token and send it via email to the user
	 */
	@RequestMapping(value="/forgot-password", method= RequestMethod.POST)
	public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user) {
		User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmail());
		if(existingUser != null) {
			// create token
			ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
			
			// save it
			confirmationTokenRepository.save(confirmationToken);
			
			// create the email
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(existingUser.getEmail());
			mailMessage.setSubject("Complete Password Reset!");
			mailMessage.setFrom("nghiemxuanhop98@gmail.com");
			mailMessage.setText("To complete the password reset process, please click here: "
			+"http://localhost:8403/confirm-reset?token="+confirmationToken.getConfirmationToken());
			
			emailSenderService.sendEmail(mailMessage);

			modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
			modelAndView.setViewName("successForgotPassword");

		} else {	
			modelAndView.addObject("message", "This email does not exist!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}


	@RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	{
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if(token != null) {

			User user = userRepository.findById(token.getUserId()).get();
			user.setStatus((long) 0);
			userRepository.save(user);
			modelAndView.addObject("user", user);
			modelAndView.addObject("email", user.getEmail());
			modelAndView.setViewName("resetPassword");
		} else {
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}	

	/**
	 * Receive the token from the link sent via email and display form to reset password
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
		// ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if(user.getEmail() != null) {
			// use email to find user
			User tokenUser = userRepository.findByEmailIdIgnoreCase(user.getEmail());
			tokenUser.setStatus((long) 0);
			tokenUser.setPassword(encoder.encode(user.getPassword()));
			// System.out.println(tokenUser.getPassword());
			userRepository.save(tokenUser);
			modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
			modelAndView.setViewName("successResetPassword");
		} else {
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}

    // Registration
    @RequestMapping(value="/register", method= RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @Autowired
    private RoleRepository roleRepository ;

	@Transactional
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, User user) {
        Set<Role> role = roleRepository.findByRoleName("ROLE_USER") ;
        User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmail());
        if(existingUser != null) {
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("error");
        } else {
            user.setRoles(role);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("nghiemxuanhop98@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8403/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("email", user.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }


	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ConfirmationTokenRepository getConfirmationTokenRepository() {
		return confirmationTokenRepository;
	}

	public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}

	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}
	
}
