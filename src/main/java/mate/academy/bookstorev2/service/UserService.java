package mate.academy.bookstorev2.service;

import mate.academy.bookstorev2.dto.user.UserRegistrationRequestDto;
import mate.academy.bookstorev2.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
