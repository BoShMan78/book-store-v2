package mate.academy.bookstorev2.service;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.UserRegistrationRequestDto;
import mate.academy.bookstorev2.dto.UserResponseDto;
import mate.academy.bookstorev2.exception.RegistrationException;
import mate.academy.bookstorev2.mapper.UserMapper;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " already exist");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        return userMapper.toDto(userRepository.save(user));
    }
}
