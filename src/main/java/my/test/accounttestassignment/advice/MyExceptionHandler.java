package my.test.accounttestassignment.advice;

import my.test.accounttestassignment.exception.NotEnoughMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * class to return json in response body
 */
class ResponseHelper {
    private String response;

    public ResponseHelper(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

}

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ResponseHelper> notEnoughMoney(){
        return new ResponseEntity<ResponseHelper>(new ResponseHelper("not enough money"), HttpStatus.CONFLICT);
    }

}
