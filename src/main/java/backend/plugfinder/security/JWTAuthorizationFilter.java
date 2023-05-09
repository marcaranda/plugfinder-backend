package backend.plugfinder.security;

import backend.plugfinder.services.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepo userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ModelMapper model_mapper = new ModelMapper();
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ","");
            UsernamePasswordAuthenticationToken authenticationToken = TokenUtils.getAuthentication(token);
            UserModel us = model_mapper.map(userRepository.findOneByEmail(authenticationToken.getPrincipal().toString()), UserModel.class);
            UserDetailsAux user_details_aux = new UserDetailsAux(us.getUser_id(), us.isUser_api());
            authenticationToken.setDetails(user_details_aux);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
