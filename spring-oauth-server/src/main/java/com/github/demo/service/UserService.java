package com.github.demo.service;

import com.github.demo.dto.UserFormDto;
import com.github.demo.dto.UserJsonDto;
import com.github.demo.dto.UserOverviewDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}