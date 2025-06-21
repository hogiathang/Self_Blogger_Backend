package com.techblog.backend.service.user;

import com.techblog.backend.dto.user.RegisterForm;
import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.dto.user.UserResponseDto;
import com.techblog.backend.dto.user.UserSecureResponse;
import com.techblog.backend.entity.user.UserEntity;
import com.techblog.backend.exception.all.NoContentException;
import com.techblog.backend.exception.user.UnAuthorizedException;
import com.techblog.backend.exception.user.UserAlreadyExistedException;
import com.techblog.backend.repository.user.UserRepository;
import com.techblog.backend.service.publicInterface.user.IUserPublicService;
import com.techblog.backend.service.publicInterface.user.IUserService;
import com.techblog.backend.utils.Mapper;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService, IUserPublicService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSecureResponse getUserInfo(String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new NoContentException("User not found with username: " + username));
        if (!userEntity.getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to view this user");
        }
        return Mapper.userEntity2UserSecureResponse(userEntity);
    }

    @Override
    public UserResponseDto addUser(RegisterForm registerForm) {
        try {
            userRepository.saveUser(
                    registerForm.getUsername(), passwordEncoder.encode(registerForm.getPassword()),
                    registerForm.getEmail(), registerForm.getPhone(), "ROLE_USER", "http://localhost:8081/api/v1/images/default/avatar.png"
            );
            return new UserResponseDto(
                    registerForm.getUsername(),
                    LocalDateTime.now(),
                    "http://localhost:8081/api/v1/images/default/avatar.png",
                    true
            );
        } catch (Exception e) {
            if (e.getMessage().contains("users_pkey")) {
                throw new UserAlreadyExistedException("User already exists with username: " + registerForm.getUsername());
            }

            if (e.getMessage().contains("users_email_key")) {
                throw new UserAlreadyExistedException("Email Already Used" + registerForm.getEmail());
            }

            if (e.getMessage().contains("users_phone_key")) {
                throw new UserAlreadyExistedException("Phone Number Already Used: " + registerForm.getPhone());
            }
        }
        return null;
    }

    @Override
    public UserResponseDto editUser(String username, UserEditForm user) {
        UserEntity existingUser = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        UserEntity updatedUser = userRepository.save(Mapper.editForm2UserEntity(user, existingUser));
        return Mapper.userEntity2UserResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
        }
    }

    @Override
    public UserResponseDto authenticate(String username, String rawPassword) {
        UserEntity user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UnAuthorizedException("Invalid password for user: " + username);
        }

        return Mapper.userEntity2UserResponseDto(user);
    }
}
