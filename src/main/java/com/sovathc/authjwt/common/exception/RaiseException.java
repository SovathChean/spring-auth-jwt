package com.sovathc.authjwt.common.exception;

import com.sovathc.authjwt.common.controller.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Data
public class RaiseException {
    public static void exception(HttpServletResponse response, String httpCode, String message) throws IOException {

        ResponseMessage<Exception> exception = new ResponseMessage<Exception>();
        exception.setResult(false);
        exception.setResultCode(httpCode);
        exception.setResultMessage(message);
        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), exception);
    }
}
