package com.example.deliveryapp.controller;

import com.example.deliveryapp.config.jwt.JwtProvider;
import com.example.deliveryapp.dto.AuthDto;
import com.example.deliveryapp.dto.AuthResponse;
import com.example.deliveryapp.dto.IntrospectRequest;
import com.example.deliveryapp.dto.RegisterDto;
import com.example.deliveryapp.enteties.Role;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.CartService;
import com.example.deliveryapp.service.RoleService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    private final UserService service;
    private final CartService cartService;

    private final RoleService roleService;
    private final JwtProvider provider;

    @Autowired
    public AuthController(UserService service, CartService cartService, RoleService roleService, JwtProvider provider) {
        this.service = service;
        this.cartService = cartService;
        this.roleService = roleService;
        this.provider = provider;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto dto) {
        Role role = roleService.findByName("ROLE_USER");
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setDeliveryAddress(dto.getDeliveryAddress());
        cartService.createCart(user);
        service.register(user, role);
        return "Ok";
    }

    @PostMapping("/register/manager")
    public String registerAsManger(@RequestBody RegisterDto dto) {
        Role role = roleService.findByName("ROLE_MANAGER");
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        cartService.createCart(user);
        service.register(user, role);
        return "Ok";
    }

    @PostMapping("/register/delivery")
    public String registerAsDeliveryBoy(@RequestBody RegisterDto dto){
        Role role = roleService.findByName("ROLE_DELIVERY");
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        cartService.createCart(user);
        service.register(user, role);
        return "Ok";
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthDto dto) {
        Optional<User> userOptional = service.findByLoginAndPassword(dto.getLogin(), dto.getPassword());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = provider.generatedAccessToken(dto.getLogin());
        String refreshToken = provider.generateRefreshToken(dto.getLogin());

        return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
                HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<AuthResponse> introspect(@RequestBody IntrospectRequest request) {
        boolean isValid = provider.validate(request.getRefreshToken());
        if (isValid) {
            String login = provider.getLoginFromToken(request.getRefreshToken());
            return getAuth(login);
        }
        return new ResponseEntity<>(
                HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<AuthResponse> getAuth(String login) {
        String accessToken = provider.generatedAccessToken(login);
        String refreshToken = provider.generateRefreshToken(login);

        return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
                HttpStatus.OK);
    }
}
