package com.conciliacion.auth.service;

import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.conciliacion.auth.dto.SignInUserDTO;

public interface CognitoService {
	InitiateAuthResult signIn(SignInUserDTO signInUser);
	String confirmSignUp(String userName,String code);
}
