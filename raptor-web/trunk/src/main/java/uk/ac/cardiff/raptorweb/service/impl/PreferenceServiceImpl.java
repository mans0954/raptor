package uk.ac.cardiff.raptorweb.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.service.PreferenceService;

public class PreferenceServiceImpl implements PreferenceService{

    static Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    @Override
    public WebSession loadUserPrefs() {
	Authentication user = SecurityContextHolder.getContext().getAuthentication();
	if (user!=null){
	    log.info("Loading preferences for user [{}]",user.getName());
	    log.info("User has rights {}",Arrays.toString(user.getAuthorities().toArray()));
	}
	else
	    log.warn("User has not logged in correctly");
	WebSession webUser = new WebSession();
	webUser.setUser(user);

	return webUser;

    }

}
