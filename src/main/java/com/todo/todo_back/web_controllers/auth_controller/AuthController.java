package com.todo.todo_back.web_controllers.auth_controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final GoogleIdTokenVerifier googleIdTokenVerifier;
	private final UserRepository userRepository;
	private final JwtEncoder encoder;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) throws GeneralSecurityException, IOException {
		GoogleIdToken idToken = googleIdTokenVerifier.verify(loginRequest.getUserId());
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setIsFirstTime(false);

		if (idToken != null) {
			GoogleIdToken.Payload payload = idToken.getPayload();

			String email = payload.getEmail();
			User user = userRepository.findByEmail(email).orElseGet(() -> {
				User newUser = new User();
				newUser.setEmail(email);
				newUser.setName(email);
				userRepository.save(newUser);
				loginResponse.setIsFirstTime(true);
				return newUser;
			});

			String scope = user.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(" "));

			Instant now = Instant.now();
			JwtClaimsSet claims = JwtClaimsSet.builder()
					.issuer("self")
					.issuedAt(now)
					.subject(email)
					.claim("scope", scope)
					.build();

			loginResponse.setToken(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
			loginResponse.setIsFirstTime(true);

			return loginResponse;
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user");
		}
	}

	@GetMapping("/login_email/{email}")
	public LoginResponse loginEmail(@PathVariable String email) {
		LoginResponse loginResponse = new LoginResponse();

		User user = userRepository.findByEmail(email).orElseGet(() -> {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setName(email);
			newUser.setIsVerified(false);
			userRepository.save(newUser);
			loginResponse.setIsFirstTime(true);
			return newUser;
		});

		String scope = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.subject(email)
				.claim("scope", scope)
				.build();

		loginResponse.setToken(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
		loginResponse.setIsFirstTime(true);

		return loginResponse;
	}
}
