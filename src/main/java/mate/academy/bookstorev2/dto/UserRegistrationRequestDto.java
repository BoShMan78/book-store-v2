package mate.academy.bookstorev2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import mate.academy.bookstorev2.validation.FieldMatch;
import mate.academy.bookstorev2.validation.UniqueEmail;

@Getter
@Setter
@FieldMatch(firstString = "password",
        seconfString = "repeatPassword",
        message = "The password fields must match")
public class UserRegistrationRequestDto {
    @NotBlank
    @UniqueEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
