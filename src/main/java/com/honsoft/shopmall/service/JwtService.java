package com.honsoft.shopmall.service;

import java.net.http.HttpHeaders;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {

	// set in .env
	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.token.expires}")
	private Long jwtExpiresMinutes;

	@Value("${jwt.token.refresh.expires}")
	private Long jwtRefreshExpiresMinutes;

	private Claims claims;

	public void generateToken(String email, HttpServletResponse response) {
		String jwt = Jwts.builder().subject(email) // username here is indeed the email
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpiresMinutes * 60 * 1000))
				.signWith(getSignInKey()).compact();

		String refreshToken = Jwts.builder().subject(email) // username here is indeed the email
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtRefreshExpiresMinutes * 60 * 1000))
				.signWith(getSignInKey()).compact();

		// Access Token cookie
		Cookie accessTokenCookie = new Cookie(JwtName.JWT.name(), jwt);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setSecure(false); // Set to true in production (HTTPS)
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(60 * 15); // 15 minutes or your configured access token expiry
		response.addCookie(accessTokenCookie);

		// Refresh Token cookie
		Cookie refreshTokenCookie = new Cookie(JwtName.REFRESH.name(), refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); // Set to true in production (HTTPS)
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
		response.addCookie(refreshTokenCookie);
	}

	public String getJwtFromCookie(HttpServletRequest request, String tokenName) {
		Cookie cookie = WebUtils.getCookie(request, tokenName);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;

	}

	public boolean validateToken(String token) throws JwtException {

		try {
			claims = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();

		} catch (JwtException e) {
// catch null, wrong token, expired token
			throw new JwtException(e.getMessage());
		}

		return true;
	}

	public void refreshToken(String email, HttpServletResponse response) {
		String newAccessToken = Jwts.builder().subject(email) // username here is indeed the email
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtExpiresMinutes * 60 * 1000))
				.signWith(getSignInKey()).compact();

		String newRefreshToken = Jwts.builder().subject(email) // username here is indeed the email
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + jwtRefreshExpiresMinutes * 60 * 1000))
				.signWith(getSignInKey()).compact();

		// Access Token cookie
		Cookie accessTokenCookie = new Cookie(JwtName.JWT.name(), newAccessToken);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setSecure(false); // Set to true in production (HTTPS)
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(60 * 15); // 15 minutes or your configured access token expiry
		response.addCookie(accessTokenCookie);

		// Refresh Token cookie
		Cookie refreshTokenCookie = new Cookie(JwtName.REFRESH.name(), newRefreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); // Set to true in production (HTTPS)
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
		response.addCookie(refreshTokenCookie);

	}

	public void removeTokenFromCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(JwtName.JWT.name(), null);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

	private SecretKey getSignInKey() {
//        SignatureAlgorithm.HS256, this.secret
		byte[] keyBytes = Decoders.BASE64.decode(this.secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractEmail() {
		return claims.getSubject();
	}

}