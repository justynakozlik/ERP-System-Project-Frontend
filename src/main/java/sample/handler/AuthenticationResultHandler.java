package sample.handler;

import sample.dto.OperatorAuthenticationResultDto;

@FunctionalInterface
public interface AuthenticationResultHandler {

    void handle(OperatorAuthenticationResultDto operatorAuthenticationResultDto);
}
