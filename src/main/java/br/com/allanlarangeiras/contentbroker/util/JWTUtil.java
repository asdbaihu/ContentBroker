package br.com.allanlarangeiras.contentbroker.util;

import br.com.allanlarangeiras.contentbroker.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

/**
 * Created by allan.larangeiras on 28/01/2019.
 */
public class JWTUtil {

    private static Algorithm algorithm = Algorithm.HMAC256("1389-12-312m3mmcasdcsladi01987213579");

//    public static String getToken(User user) {
//        try {
//            String token = JWT.create()
//                    .withIssuer(user)
//                    .sign(algorithm);
//        } catch (JWTCreationException exception){
//            //Invalid Signing configuration / Couldn't convert Claims.
//        }
//        return null;
//    }

}
