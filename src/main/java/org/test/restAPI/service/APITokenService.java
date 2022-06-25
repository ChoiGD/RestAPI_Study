package org.test.restAPI.service;

import io.jsonwebtoken.JwtException;
import org.test.restAPI.dto.APITokenDTO;

public interface APITokenService {

    APITokenDTO makeTokens(String mid, String mpw);

    APITokenDTO refreshTokens(String refreshToken) throws JwtException;
}
