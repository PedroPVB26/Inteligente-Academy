package br.edu.utfpr.inteligenteacademy.model.dto.user;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreationDto {
    @NotBlank(message = "cpf must not be blank")
    @CPF(message = "cpf must be valid")
    private String cpf;

    @NotBlank(message = "name must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "name must contain between 3 and 120 characters"
    )
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "name must contain only letters and spaces"
    )
    private String name;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    @Size(
        max = 150,
        message = "email must contain at most 150 characters"
    )
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(
        min = 8,
        max = 100,
        message = "password must contain between 8 and 100 characters"
    )
    private String password;

    @NotNull(message = "birthDate must not be null")
    @Past(message = "birthDate must be a past date")
    private LocalDate birthDate;

    @NotNull(message = "userRole must not be null")
    private UserRole userRole;


    public UserCreationDto() {
        
    }
    
    public UserCreationDto(
            String cpf,
            String name,
            String email,
            String password,
            LocalDate birthDate,
            UserRole userRole
    ) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.userRole = userRole;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getTipoUsuario() {
        return userRole;
    }

    public void setTipoUsuario(UserRole userRole) {
        this.userRole = userRole;
    }
}