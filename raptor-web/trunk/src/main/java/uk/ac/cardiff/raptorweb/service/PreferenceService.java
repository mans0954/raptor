package uk.ac.cardiff.raptorweb.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import uk.ac.cardiff.raptorweb.model.WebSession;

public interface PreferenceService {

    public WebSession loadUserPrefs();

}
