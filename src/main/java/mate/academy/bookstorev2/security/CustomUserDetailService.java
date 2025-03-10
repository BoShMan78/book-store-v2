package mate.academy.bookstorev2.security;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.exception.EntityNotFoundException;
import mate.academy.bookstorev2.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find user by email: " + email));
    }
}
