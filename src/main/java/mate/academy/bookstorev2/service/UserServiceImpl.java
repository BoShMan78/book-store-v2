package mate.academy.bookstorev2.service;

import static mate.academy.bookstorev2.model.Role.RoleName.ROLE_USER;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.UserRegistrationRequestDto;
import mate.academy.bookstorev2.dto.UserResponseDto;
import mate.academy.bookstorev2.exception.RegistrationException;
import mate.academy.bookstorev2.mapper.UserMapper;
import mate.academy.bookstorev2.model.Role;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.role.RoleRepository;
import mate.academy.bookstorev2.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " already exist");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role role;
        role = roleRepository.findByName(ROLE_USER).orElseGet(
                () -> {
                    Role newRole = new Role();
                    newRole.setName(ROLE_USER);
                    return roleRepository.save(newRole);
                });
        user.setRoles(Set.of(role));
        return userMapper.toDto(userRepository.save(user));
    }
}
