package org.test.restAPI.service;


import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.test.restAPI.controller.APIUserController;
import org.test.restAPI.domain.APIUser;
import org.test.restAPI.dto.APITokenDTO;
import org.test.restAPI.repository.APIUserRepository;
import org.test.restAPI.util.JWTUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class APITokenServiceImpl implements APITokenService{

    private final JWTUtil jwtUtil;


    private final APIUserRepository apiUserRepository;

    @Override
    public APITokenDTO makeTokens(String mid, String mpw) {

        Optional<APIUser> result = apiUserRepository.findAPIUserByMidAndMpw(mid, mpw);

        //존재하지 않을 시에 예외 처리
        APIUser apiUser = result.orElseThrow(() -> new APIUserController.APIUserNotFoundException());

        Map<String, Object> claimMap = Map.of("mid", mid);

        //access 토큰 생성
        String accessToken = jwtUtil.generateToken(claimMap, 10);

        //refresh 토큰 생성
        String refreshToken = jwtUtil.generateToken(claimMap, 60);

        APITokenDTO tokenDTO = APITokenDTO.builder()
                .mid(mid)
                .access(accessToken)
                .refresh(refreshToken)
                .build();

        return tokenDTO;
    }

    @Override
    public APITokenDTO refreshTokens(String refreshToken) throws JwtException {
        //RefreshToken 자체가 문제가 있거나 만료되면 다시 모든걸 시작해야 함
        //Refresh Token 자체의 만료 시간이 넘는다면 ExpiredJwtException 발생
        Map<String, Object> parseResult = jwtUtil.validateToken(refreshToken);

        log.info(parseResult);

        //Refresh Token의 유효시간이 얼마 남지 않은 경우
        Integer exp = (Integer)parseResult.get("exp");

        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재 시간의 간격 계산
        //만일 1일 미만인 경우에는 Refresh Token도 다시 생성
        long gapTime = (expTime.getTime() - current.getTime());

        log.info("-----------------------------------------");
        log.info("current: " + current);
        log.info("expTime: " + expTime);
        log.info("gap: " + gapTime );

        String mid = (String)parseResult.get("mid");
        //Access Token 생성
        String accessTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 100);

        //Refresh Token의 유효시간이 충분하다면 그대로
        String refreshTokenValue = refreshToken;

        //유효시간이 부족하다면
        //1000* 60 * 60 * 24  7day
        //if(gapTime < (1000 * 60 *60 * 24 * 7 ) ){
        if(gapTime < (1000 * 60 * 30  ) ){
            log.info("new Refresh Token required...  ");
            refreshTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 600);
        }

        return APITokenDTO.builder()
                .mid(mid)
                .refresh(refreshTokenValue)
                .access(accessTokenValue)
                .build();
    }
}
