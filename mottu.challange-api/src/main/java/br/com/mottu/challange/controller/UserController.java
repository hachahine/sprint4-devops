package br.com.mottu.challange.controller;

import br.com.mottu.challange.domain.dto.user.UserDTO;
import br.com.mottu.challange.domain.entity.User;
import br.com.mottu.challange.infra.TokenDTO;
import br.com.mottu.challange.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity logIn(@RequestBody @Valid UserDTO userDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.login(), userDTO.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.getToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

}
