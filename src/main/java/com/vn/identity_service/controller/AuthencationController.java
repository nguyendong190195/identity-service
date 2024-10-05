package com.vn.identity_service.controller;
import com.nimbusds.jose.JOSEException;
import com.vn.identity_service.dto.request.ApiResponse;
import com.vn.identity_service.dto.request.AuthencationRequest;
import com.vn.identity_service.dto.request.InstrospectRequest;
import com.vn.identity_service.dto.response.AuthencationResponse;
import com.vn.identity_service.dto.response.IntrospectResponse;
import com.vn.identity_service.service.AuthencationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthencationController {

    AuthencationService authencationService;

    @PostMapping("/token")
    ApiResponse<AuthencationResponse> authenticate(@RequestBody AuthencationRequest authencationRequest) {
        var result = authencationService.authenticate(authencationRequest);
        return ApiResponse.<AuthencationResponse>builder()
                .result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody InstrospectRequest request) throws ParseException, JOSEException {
        var result = authencationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result).build();
    }
}
