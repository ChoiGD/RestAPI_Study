package org.test.restAPI.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.test.restAPI.service.APITokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.test.restAPI.dto.APITokenDTO;
import org.test.restAPI.dto.APIUserDTO;

@RestController
@Log4j2
@RequiredArgsConstructor
public class APIUserController {

    private final APITokenService apiTokenService;

    public static class APIUserNotFoundException extends RuntimeException{

    }

    public static class BadRefreshRequestException extends RuntimeException {

    }

    @ApiOperation("Generate Tokens with POST ")
    @PostMapping("/generateToken")
    public APITokenDTO generateToken(@RequestBody APIUserDTO apiUserDTO) {

        return apiTokenService.makeTokens(apiUserDTO.getMid(),apiUserDTO.getMpw());
    }

    @ApiOperation("Refresh Tokens with POST ")
    @PostMapping("/refreshAccessToken")
    public APITokenDTO refreshAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken ){

        log.info("grantType: " + grantType);
        log.info("refreshToken: " + refreshToken);


        if(grantType == null || grantType.equals("refresh_token") == false){
            throw new BadRefreshRequestException();
        }

        return apiTokenService.refreshTokens(refreshToken);
    }

}
