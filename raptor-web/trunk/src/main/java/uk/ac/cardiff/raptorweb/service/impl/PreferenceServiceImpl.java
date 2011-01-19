package uk.ac.cardiff.raptorweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import uk.ac.cardiff.raptorweb.model.WebUser;
import uk.ac.cardiff.raptorweb.service.PreferenceService;

public class PreferenceServiceImpl implements PreferenceService{
    
    static Logger log = LoggerFactory.getLogger(StartServiceImpl.class);

    @Override
    public WebUser loadUserPrefs(UsernamePasswordAuthenticationToken user) {
	// TODO load any preferences from the MUA?
	if (user!=null)
	    log.info("Loading preferences for user {}",user.getName());
	else
	    log.info("User has not logged in correctly");
	WebUser webUser = new WebUser();
	
	return webUser;
	
    }

}
