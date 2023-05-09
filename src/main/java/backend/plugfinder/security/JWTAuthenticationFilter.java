package backend.plugfinder.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials credentials = new AuthCredentials();
        try {
            credentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (Exception e) {
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.generateAccessToken(userDetails.getId(), userDetails.getUsername(), userDetails.isUser_Api()); //Get username returns the email

        PrintWriter w = response.getWriter();
        w.println(token);
        w.flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
