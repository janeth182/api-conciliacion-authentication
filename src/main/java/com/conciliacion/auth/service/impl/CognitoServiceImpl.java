package com.conciliacion.auth.service.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.CodeMismatchException;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.ExpiredCodeException;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.conciliacion.auth.configuration.AwsConfig;
import com.conciliacion.auth.dto.SignInUserDTO;
import com.conciliacion.auth.service.CognitoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
@Slf4j
@Service
public class CognitoServiceImpl implements CognitoService {
	private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
	private final AwsConfig awsConfig;
	@Override
	public InitiateAuthResult signIn(SignInUserDTO signInUser) {
		
		
		final Map<String, String> authParams = new HashMap<>();
        authParams.put("PASSWORD", signInUser.getPassword());
        authParams.put("USERNAME", signInUser.getEmail());        
		
		final InitiateAuthRequest authRequest = new InitiateAuthRequest()
				.withClientId(awsConfig.getCognito().getAppClientId())				
				.withAuthFlow("USER_PASSWORD_AUTH")
				.withAuthParameters(authParams);
		
		InitiateAuthResult reponse = awsCognitoIdentityProvider.initiateAuth(authRequest);
		  String AccessToken = reponse.getAuthenticationResult().getAccessToken();
          log.info("Created User id: {}", reponse.getSession() );
				
		
		return reponse;
	}
	
	@Override
	public String confirmSignUp(String userName,String code) {
		 try {
			 log.info("userName: {}", userName);
			 log.info("code: {}", code);
		      final ConfirmSignUpRequest signUpRequest = new ConfirmSignUpRequest()
	                  .withClientId(awsConfig.getCognito().getAppClientId())	                  
	                  .withConfirmationCode(code)
	                  .withUsername(userName);
		      
		      ConfirmSignUpResult result = awsCognitoIdentityProvider.confirmSignUp(signUpRequest);
	          log.info("Confirm User: {}", result.getSdkHttpMetadata().getHttpStatusCode());
	          
	          return "Registro confirmado exitosamente.";
		      	
			 } catch (CodeMismatchException e) {
				 log.info(e.getMessage());			       
				 return "El código de confirmación es incorrecto.";
			 } catch (ExpiredCodeException e) {
				 log.info(e.getMessage());   
				 return "El código de confirmación ha expirado.";
			 } catch (Exception e) {
				 log.info(e.getMessage());   
				 return "error";
			 }
	}

}
