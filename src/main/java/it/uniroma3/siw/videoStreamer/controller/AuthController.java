package it.uniroma3.siw.cateringService.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.cateringService.controller.validator.CredentialsValidator;
import it.uniroma3.siw.cateringService.controller.validator.UserValidator;
import it.uniroma3.siw.cateringService.model.Credentials;
import it.uniroma3.siw.cateringService.model.User;
import it.uniroma3.siw.cateringService.service.CredentialsService;

@Controller
public class AuthController {
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@GetMapping("/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerForm";
	}
	
	@GetMapping("/adminRegister")
	public String showAdminRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		
		return "adminRegisterForm";
	}

	@GetMapping("/login") 
	public String showLoginForm (Model model) {
		return "loginForm";
	}

	@GetMapping("/logout") 
	public String logout(Model model) {
		return "index";
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/home";
		}
		return "index";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials, false);
			return "registerSuccess";
		}
		return "registerForm";
	}
	
	@PostMapping("/adminRegister")
	public String registerAdmin(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, 
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials, true);
			model.addAttribute("messageEN", "Admin registration successful!");
			model.addAttribute("messageIT", "Registrazione Admin effettuata con successo!");
			return "registerSuccess";
		}
		return "adminRegisterForm";
	}
	
	@GetMapping("/oauthDefault")
	public String defaultAfterOAuthLogin(Model model, OAuth2AuthenticationToken authentication) {		
		// ricava il client corrispondente al token
		OAuth2User oAuth2User = authentication.getPrincipal();
		Map<String,Object> attributes = oAuth2User.getAttributes();
		
		if(authentication.getAuthorizedClientRegistrationId().equals("google")) {
			String email = (String) attributes.get("email");
			Credentials userCredentials = credentialsService.getCredentials(email);
		    
			if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();
			    
				oauthUser.setNome((String) attributes.get("given_name"));
				oauthUser.setCognome((String) attributes.get("family_name"));

			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername(email);
			    credentialsService.saveCredentials(oauthCredentials, false);
		    }
		}
		
		if(authentication.getAuthorizedClientRegistrationId().equals("github")) {
			String username= (String) attributes.get("login");
			Credentials userCredentials = credentialsService.getCredentials(username);
		    
			if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();

				
				String[] nomeCompleto;
				String nome = ((String) attributes.get("name"));

				if(nome!=null){
					nomeCompleto = nome.split(" ");
					oauthUser.setNome(nomeCompleto[0]);
					if(nomeCompleto.length>1)	
						oauthUser.setCognome(nomeCompleto[1]);
				}
				else
					oauthUser.setNome(username);

			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername(username);
			    credentialsService.saveCredentials(oauthCredentials, false);
		    }
		}
		
		model.addAttribute("role", this.credentialsService.getCredentials().getRole());
		
		return "index";
	}
	
}
