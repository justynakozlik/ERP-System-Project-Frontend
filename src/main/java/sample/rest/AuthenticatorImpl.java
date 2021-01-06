package sample.rest;

import sample.dto.UserCredentialsDto;

public class AuthenticatorImpl implements Authenticator {

    private static final String LOGIN = "user";
    private static final String PASSWORD = "user";

    @Override
    public void authenticate(UserCredentialsDto userCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {
        
       Runnable authenticationTask = createAuthenticationTask(userCredentialsDto,authenticationResultHandler);
       Thread authenticationThread = new Thread(authenticationTask);
       authenticationThread.setDaemon(true);
       authenticationThread.start();

    }

    private Runnable createAuthenticationTask(UserCredentialsDto userCredentialsDto, AuthenticationResultHandler authenticationResultHandler) {
        return () -> {
            try {
                Thread.sleep(1000);
                boolean authenticated = LOGIN.equals(userCredentialsDto.getLogin()) && PASSWORD.equals(userCredentialsDto.getPassword());
                authenticationResultHandler.handle(authenticated);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
