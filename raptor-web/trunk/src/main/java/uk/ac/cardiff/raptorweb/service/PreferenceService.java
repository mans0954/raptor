package uk.ac.cardiff.raptorweb.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import uk.ac.cardiff.raptorweb.model.WebUser;

public interface PreferenceService {
    
    public WebUser loadUserPrefs(UsernamePasswordAuthenticationToken user);

}
