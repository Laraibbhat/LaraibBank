package org.example.laraib.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name="Customer",
        description="Customer details"
)
public class CustomerDto {

    @Schema(description = "Customer ID", example = "1")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @Schema(description = "Customer email", example = "john.doe@example.com")
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 10, max = 50, message = "Email must be between 10 and 50 characters")
    private String email;

    @Schema(description = "Customer mobile number", example = "1234567890")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a valid 10-digit number")
    private String mobileNumber;

    @Schema(description = "Customer accounts", example = "Accounts details")
    private AccountsDto accountsDto;
}
