package org.test.restAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.restAPI.domain.APIUser;

import java.util.Optional;

public interface APIUserRepository extends JpaRepository<APIUser,String> {

    Optional<APIUser> findAPIUserByMidAndMpw(String mid, String mpw);

}
