package com.conciliacion.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.conciliacion.auth.dto.SignInUserDTO;
import com.conciliacion.auth.service.CognitoService;


@RestController
@RequestMapping("/v1/login")
public class CognitoController {
	@Autowired
	public CognitoService service;
	
	@PostMapping
	public ResponseEntity<AuthenticationResultType> verify(@RequestBody SignInUserDTO signInUser){

		InitiateAuthResult reponse = service.signIn(signInUser);
		
		return ResponseEntity.ok(reponse.getAuthenticationResult());		
	}
	
	@GetMapping
	public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String code){

		String reponse = service.confirmSignUp(email, code);
		
		return ResponseEntity.ok(reponse);		
	}
}
