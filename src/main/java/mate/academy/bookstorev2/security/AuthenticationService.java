package mate.academy.bookstorev2.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.UserLoginRequestDto;
import mate.academy.bookstorev2.model.User;
import mate.academy.bookstorev2.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean authenticate(UserLoginRequestDto requestDto) {
        Optional<User> user = userRepository.findUserByEmail(requestDto.email());
        if (user.isEmpty()) {
            return false;
        }
        String userPasswordFromDb = user.get().getPassword();
        return passwordEncoder.matches(requestDto.password(), userPasswordFromDb);
    }
}
