package com.vn.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vn.identity_service.dto.request.AuthencationRequest;
import com.vn.identity_service.dto.request.InstrospectRequest;
import com.vn.identity_service.dto.response.AuthencationResponse;
import com.vn.identity_service.dto.response.IntrospectResponse;
import com.vn.identity_service.entity.User;
import com.vn.identity_service.exception.AppExeption;
import com.vn.identity_service.exception.ErrorCode;
import com.vn.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import static com.vn.identity_service.exception.ErrorCode.USER_EXISTED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthencationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    public AuthencationResponse authenticate(AuthencationRequest authencationRequest) {
        var user = userRepository.findByUsername(authencationRequest.getUsername()).orElseThrow(() -> new AppExeption(USER_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authencatie = passwordEncoder.matches(authencationRequest.getPassword(), user.getPassword());
        if (!authencatie) {
            throw new AppExeption(ErrorCode.UNAUTHORIZED);
        }
        String token = null;
        token = generateToken(user);
        return AuthencationResponse.builder().isAuthenticated(authencatie).token(token).build();
    }

    public IntrospectResponse introspect(InstrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY);
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder().valid(verified && expityTime.after(new Date())).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("customClaim")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())).claim("customClaim", "Custom")
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppExeption(USER_EXISTED);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
//            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
