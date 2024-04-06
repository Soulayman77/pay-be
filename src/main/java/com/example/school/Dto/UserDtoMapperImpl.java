package com.example.school.Dto;

        import com.example.school.Entities.User;
        import com.example.school.Entities.UserRole;
        import org.mapstruct.BeanMapping;
        import org.mapstruct.Mapping;
        import org.mapstruct.MappingTarget;
        import org.mapstruct.NullValuePropertyMappingStrategy;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Component;

@Component  // Mark as a Spring bean
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    public User createUserDtoToUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        // Hash the password before setting it (security best practice)
        user.setPassword(bcryptEncoder.encode(createUserDto.getPassword()));
        user.setRole(createUserDto.getRole() != null ? createUserDto.getRole() : UserRole.STUDENT);  // Default to USER role
        return user;
    }

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    public void updateUserFromDto(EditUserDto editUserDto, @MappingTarget User user) {
        user.setFirstName(editUserDto.getFirstName());
        user.setLastName(editUserDto.getLastName());
        user.setEmail(editUserDto.getEmail());
    }

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    public void updatePasswordFromDto(EditPasswordDto editPasswordDto, @MappingTarget User user) {
        user.setPassword(bcryptEncoder.encode(editPasswordDto.getPassword()));
        /*user.setPassword(editPasswordDto.getPassword());*/
    }

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder bcryptEncoder ;



}