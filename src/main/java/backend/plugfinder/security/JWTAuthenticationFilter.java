package backend.plugfinder.security;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.services.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserRepo user_repository;

    public JWTAuthenticationFilter(UserRepo user_repository) {
        this.user_repository = user_repository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials credentials = new AuthCredentials();
        try {
            credentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (Exception e) {
        }

        //Obtener el usuario de la base de datos a través del correo electrónico ingresado en el formulario de inicio de sesión
        ModelMapper model_mapper = new ModelMapper();
        UserModel us = model_mapper.map(user_repository.findOneByEmail(credentials.getEmail()), UserModel.class);

        //Verificar si el usuario existe y no está eliminado
        if (us != null && !us.isDeleted()) {
            //Si el usuario no está eliminado, proceder a autenticarlo
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword(),
                    Collections.emptyList()
            );
            return getAuthenticationManager().authenticate(authenticationToken);
        }
        else {
            String errorMessage = "El usuario no existe";
            OurException ourException = new OurException(errorMessage);

            throw new BadCredentialsException(errorMessage, ourException);
        }
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
