package com.exapmle.userservice.service;

import com.exapmle.userservice.dto.UserDTO;
import com.exapmle.userservice.mapper.UserMapper;
import com.exapmle.userservice.model.User;
import com.exapmle.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        System.out.println(userRepository.findAll().get(0).getEmail());
        return userMapper.toDtoList(userRepository.findAll());

    }
    public UserDTO getUserById(Long id) {
        return userMapper.toDto(userRepository.getReferenceById(id));
    }
    public UserDTO createUser(UserDTO userDto) {
        User user = userMapper.toModel(userDto);
        user.setLocation("Astana");
        return userMapper.toDto(userRepository.save(user));
    }

}
