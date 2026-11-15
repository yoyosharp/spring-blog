package com.yoyodev.springblog.engine.service.impl;

import com.yoyodev.springblog.engine.repositories.UserRepository;
import com.yoyodev.springblog.engine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


}
