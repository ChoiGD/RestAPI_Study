package org.test.restAPI.service;

import org.test.restAPI.dto.APIUserDTO;

import java.util.Optional;

public interface APIUserService {

    Optional<APIUserDTO> checkUser(String mid, String mpw);

}
