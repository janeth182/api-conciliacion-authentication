package com.conciliacion.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class SignInUserDTO {
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
}
