package sample.rest;

import org.springframework.web.client.RestTemplate;
import sample.dto.OperatorAuthenticationResultDto;
import sample.dto.OperatorCredentialsDto;
import sample.handler.AuthenticationResultHandler;

public class AuthenticatorImpl implements Authenticator {

    private static final String AUTHENTICATION_URL = "http://localhost:8080/verify_operator_credentials";

    private final RestTemplate restTemplate;

    public AuthenticatorImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public void authenticate(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {

        Runnable authenticationTask = () -> {
            processAuthentication(operatorCredentialsDto, authenticationResultHandler);
        };
        Thread authenticationThread = new Thread(authenticationTask);
        authenticationThread.setDaemon(true);
        authenticationThread.start();

    }

    private void processAuthentication(OperatorCredentialsDto operatorCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {
        //ResponseEntity<OperatorAuthenticationResultDto> responseEntity = restTemplate.postForEntity(AUTHENTICATION_URL, operatorCredentialsDto, OperatorAuthenticationResultDto.class);
        OperatorAuthenticationResultDto dto = new OperatorAuthenticationResultDto();
        dto.setAuthenticated(true);
        dto.setFirstName("Justyna");
        dto.setLastName("Nowak");
        dto.setIdOperator(1L);

        authenticationResultHandler.handle(dto);

    }
}
