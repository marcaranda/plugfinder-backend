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

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken authenticationToken = TokenUtils.getAuthentication(token);

            if (authenticationToken != null && authenticationToken.getPrincipal() != null) {
                UserModel us = model_mapper.map(userRepository.findOneByEmail(authenticationToken.getPrincipal().toString()), UserModel.class);

                if (us != null && !us.isDeleted()) {
                    UserDetailsAux user_details_aux = new UserDetailsAux(us.getUser_id(), us.isUser_api(), us.isPremium());
                    authenticationToken.setDetails(user_details_aux);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
