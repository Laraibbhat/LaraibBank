package org.example.laraib.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.laraib.accounts.constants.AccountsConstants;
import org.example.laraib.accounts.dto.AccountsDto;
import org.example.laraib.accounts.dto.CustomerDto;
import org.example.laraib.accounts.dto.ResponseDto;
import org.example.laraib.accounts.exception.CustomerAlreadyExistsException;
import org.example.laraib.accounts.service.IAccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST API Operations",
        description = "Controller for managing customer accounts"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {
    private IAccountsService accountsService;

//    public AccountsController(IAccountsService accountsService) {
//        this.accountsService = accountsService;
//    }

    @Operation(
            summary = "Create a new customer account",
            description = "This endpoint allows you to create a new customer account by providing the necessary details."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
            accountsService.createAccount(customerDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch customer account details",
            description = "This endpoint allows you to fetch the details of a customer account by providing the mobile number."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customer account details fetched successfully"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "^[0-9]{10}$") String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccountDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "Update customer account details",
            description = "This endpoint allows you to update the details of an existing customer account."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customer account details updated successfully"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid CustomerDto customerDto) {
        boolean updated =  accountsService.updateAccount(customerDto);
        if (!updated) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }
    
    @Operation(
            summary = "Delete customer account",
            description = "This endpoint allows you to delete a customer account by providing the mobile number."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer account deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error while deleting customer account"
            )}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto>  deleteAccount (@RequestParam @Pattern(regexp = "^[0-9]{10}$") String mobileNumber) {
        boolean deleted = accountsService.deleteAccount(mobileNumber);
        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }
}
