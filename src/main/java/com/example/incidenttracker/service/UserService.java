package com.example.incidenttracker.service;

import com.example.incidenttracker.dto.UserRequestDto;
import com.example.incidenttracker.dto.UserResponseDto;
import com.example.incidenttracker.exception.ResourceNotFoundException;
import com.example.incidenttracker.model.User;
import com.example.incidenttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        //Check if username is taken
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceNotFoundException("User with username " + dto.getUsername() + " already exists");
        }

        //Check if email was already used
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceNotFoundException("User with email " + dto.getEmail() + " already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .build();
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found with id: " + id));
        return mapToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found with username: " + username));
        return mapToDto(user);
    }

    //Private helper to map Entity to User DTO
    private UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }
}
