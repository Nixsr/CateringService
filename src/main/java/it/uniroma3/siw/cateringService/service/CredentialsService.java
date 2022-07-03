package it.uniroma3.siw.cateringService.service;

import java.util.Optional;

import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.cateringService.model.Credentials;
import it.uniroma3.siw.cateringService.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;

	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

	@Transactional
	public Credentials saveCredentials(Credentials credentials, boolean isAdmin) {
		if(isAdmin) {
			credentials.setRole(Credentials.ADMIN_ROLE);
		}
		else {
			credentials.setRole(Credentials.DEFAULT_ROLE);
		}


		if(credentials.getUser().getCognome()==null) {
			credentials.getUser().setCognome("OAuthDefault");
		}

		try {
			credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		}
		catch(Exception e) {
			credentials.setPassword(this.passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)));
		}

		return this.credentialsRepository.save(credentials);
	}


	@Transactional
	public String getRoleAuthenticated() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.getCredentials(userDetails.getUsername());
		return credentials.getRole();
	}
}