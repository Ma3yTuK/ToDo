package com.todo.todo_back.web_controllers;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo.todo_back.entities.User;
import com.todo.todo_back.services.UserService;

@RestController
public class AuthController {

	JwtEncoder encoder;
	UserService userService;

	public AuthController(JwtEncoder encoder, UserService userService) {
		this.encoder = encoder;
		this.userService = userService;
	}

	@GetMapping("/token")
	public String token(Authentication authentication) {
		Instant now = Instant.now();
		long expiry = 36000L;

		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
                
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();

		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	@PostMapping("/register")
	public String register(@RequestBody @Validated User user) {
		Instant now = Instant.now();
		long expiry = 36000L;

		userService.encryptPassword(user);
		Optional<User> inDbUser = userService.findUserByUsername(user.getUsername());
		
		if (inDbUser.isEmpty()) {
			userService.saveUser(user);
			
			String scope = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
                
			JwtClaimsSet claims = JwtClaimsSet.builder()
					.issuer("self")
					.issuedAt(now)
					.expiresAt(now.plusSeconds(expiry))
					.subject(user.getUsername())
					.claim("scope", scope)
					.build();
			
			return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist!");
	}

}
