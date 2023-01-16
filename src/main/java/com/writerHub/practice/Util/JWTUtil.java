package com.writerHub.practice.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.writerHub.practice.models.WriterHubUser;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JWTUtil {
    private  static Map<String,Date> expiryTime = new HashMap<String,Date>();

    public JWTUtil(){
        expiryTime.put("REFRESH",new Date(System.currentTimeMillis()+ 600*60*1000));
        expiryTime.put("ACCESS",new Date(System.currentTimeMillis()+ 60*60*1000));
    }

    public  static String getToken(WriterHubUser appUser,String url,boolean isRefresh){
        Algorithm algorithm  = Algorithm.HMAC512("TestToken".getBytes(StandardCharsets.UTF_8));
        JWTCreator.Builder token = JWT.create()
                .withExpiresAt(expiryTime.get(isRefresh?"REFRESH":"ACCESS"))
                .withSubject(appUser.getUsername())
                .withIssuer(url);
        if(!isRefresh){
            token.withClaim("role",appUser.getRole());
        }
        if(appUser.isCompany() && appUser.getCompany() != null){
            token.withAudience(appUser.getCompany().getId().toString());
        }
        else if(!appUser.isCompany() && appUser.getAuthor() != null) {
            token.withAudience(appUser.getAuthor().getId().toString());
        }

        return  token.sign(algorithm);
    }

    public static DecodedJWT decodeToken(String token) throws JWTVerificationException {
        if (token != null) {
            Algorithm algo = Algorithm.HMAC512("TestToken".getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algo).build();
            DecodedJWT decoded = verifier.verify(token);
            return decoded;
        }
        return  null;
    }

    public static String extractToken(HttpServletRequest request) throws AuthorizationHeaderMissing {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.split(" ")[1];
            return  token;
        }
        throw new AuthorizationHeaderMissing("You are unauthorized");
    }
}
