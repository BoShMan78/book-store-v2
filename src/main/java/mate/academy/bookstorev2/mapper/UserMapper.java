package mate.academy.bookstorev2.mapper;

import mate.academy.bookstorev2.config.MapperConfig;
import mate.academy.bookstorev2.dto.UserResponseDto;
import mate.academy.bookstorev2.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
}
