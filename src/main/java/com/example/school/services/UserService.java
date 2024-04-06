package com.example.school.services;

import com.example.school.Dto.*;
import com.example.school.Entities.User;
import com.example.school.Entities.UserRole;
import com.example.school.Repositories.UserRepository;
import lombok.Data;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.school.Entities.MyUserDetails;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Data
@Service
public class UserService {

    private static Supplier<ResponseStatusException> NOT_FOUND_HANDLER = () -> {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    };

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapperImpl userDtoMapper;
   /* @Autowired
    PasswordEncoder passwordEncoder;*/

    public User getUser(final Long id) {
        return userRepository.findById(id).orElseThrow(NOT_FOUND_HANDLER);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(NOT_FOUND_HANDLER);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all users paginated
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public Page<User> searchUsers(String search, Pageable pageable) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, search, pageable);
    }

    public Page<User> searchUsers(String search, UserRole role, Pageable pageable) {
        return userRepository.findByFirstNameContainingIgnoreCaseAndRoleOrLastNameContainingIgnoreCaseAndRoleOrEmailContainingIgnoreCaseAndRole(search, role, search, role, search, role, pageable);
    }
    public Page<User> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findAllByRole(role, pageable);
    }


    public User addUser(CreateUserDto createUserDto){
        // Check if email already exists (to avoid an error from the unique constraint)
        User check = userRepository.findByEmail(createUserDto.getEmail()).orElse(null);

        if(check != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = userDtoMapper.createUserDtoToUser(createUserDto);
        /*String pw = passwordEncoder.encode(userDtoMapper.createUserDtoToUser(createUserDto).getPassword());
        System.out.println(pw);*/
        user.setPassword(userDtoMapper.createUserDtoToUser(createUserDto).getPassword());
        user.setActive(true);
        return userRepository.save(user);
    }

    public User editUser(Long id, EditUserDto editUserZoneDto) {
        User user = userRepository.findById(id).orElseThrow(NOT_FOUND_HANDLER);
        userDtoMapper.updateUserFromDto(editUserZoneDto, user);
        return userRepository.save(user);
    }

    public User editPassword(EditPasswordDto editPasswordDto){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        userDtoMapper.updatePasswordFromDto(editPasswordDto, user);
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.delete(userRepository.findById(id).orElseThrow(NOT_FOUND_HANDLER));
    }
}

