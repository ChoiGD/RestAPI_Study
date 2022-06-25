package org.test.restAPI.service;

import lombok.RequiredArgsConstructor;
import org.test.restAPI.repository.APIUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.test.restAPI.domain.APIUser;
import org.test.restAPI.dto.APIUserDTO;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class APIUserServiceImpl implements APIUserService{

    private final APIUserRepository apiUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<APIUserDTO> checkUser(String mid, String mpw) {

        Optional<APIUser> result = apiUserRepository.findAPIUserByMidAndMpw(mid, mpw);

        if(result.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(result.get(), APIUserDTO.class));

    }
}
