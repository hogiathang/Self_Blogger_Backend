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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, IUserPublicService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSecureResponse getUserInfo(Long id, String username) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoContentException("User not found with id: " + id));
        if (!userEntity.getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to view this user");
        }
        return Mapper.userEntity2UserSecureResponse(userEntity);
    }

    @Override
    public UserResponseDto addUser(RegisterForm registerForm) {
        if (userRepository.findByUsername(registerForm.getUsername()).isPresent()) {
            throw new UserAlreadyExistedException("User already exists with username: " + registerForm.getUsername());
        } else {
            UserEntity userEntity =  userRepository.save(
                Mapper.registerForm2UserEntity(
                    registerForm,
                    passwordEncoder,
                    "ROLE_USER"
                )
            );
            return Mapper.userEntity2UserResponseDto(userEntity);
        }
    }

    @Override
    public UserResponseDto editUser(Long id, String username, UserEditForm user) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!existingUser.getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to edit this user");
        }

        UserEntity updatedUser = userRepository.save(Mapper.editForm2UserEntity(user, existingUser));
        return Mapper.userEntity2UserResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserResponseDto authenticate(String username, String rawPassword) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UnAuthorizedException("Invalid password for user: " + username);
        }

        return Mapper.userEntity2UserResponseDto(user);
    }
}
