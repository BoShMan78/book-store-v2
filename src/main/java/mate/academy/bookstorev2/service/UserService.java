package mate.academy.bookstorev2.service;

import mate.academy.bookstorev2.dto.UserRegistrationRequestDto;
import mate.academy.bookstorev2.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
