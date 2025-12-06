package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotBlank(message = "Country is mandatory to fill in")
    @Size(max = 100, message = "The name of the country should not exceed 100 characters.")
    private String country;
    @NotBlank(message = "City is mandatory to fill in")
    @Size(max = 100, message = "The name of the city should not exceed 100 characters.")
    private String city;
    @NotBlank(message = "Street is mandatory to fill in")
    @Size(max = 100, message = "The name of the street should not exceed 100 characters.")
    private String street;
    @NotBlank(message = "The house number must not exceed 20 characters.")
    private String house;
    @NotBlank(message = "The flat number must not exceed 20 characters.")
    private String flat;
}
