package it.uniroma3.siw.siwfood.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class customAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final utenteService utenteService;

    public customAuthenticationProvider(it.uniroma3.siw.siwfood.services.utenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails utente = utenteService.loadUserByUsername(username);
        if (utente == null) {
            throw new BadCredentialsException("Credenziali errate");
        }
        return utente;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = utenteService.loadUserByUsername(username);
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Password errata");
        }
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
