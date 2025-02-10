package org.edutecno.front.security;


import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

public class JwtTokenStorage {
    @Getter
    @Setter
    private static String token;
    @Getter
    @Setter
    private static String role;

    public static void clear() {
        token = null;
        role = null;
    }

    public static String extractRoleFromToken(String jwt) {
        try {
            String[] splitToken = jwt.split("\\.");
            if (splitToken.length < 2) {
                return null;
            }

            String base64Payload = splitToken[1];
            String payloadJson = new String(Base64.getDecoder().decode(base64Payload));

            int roleIndex = payloadJson.indexOf("\"role\":\"");
            if (roleIndex == -1) {
                return null;
            }

            int startIndex = roleIndex + 8;
            int endIndex = payloadJson.indexOf("\"", startIndex);
            return payloadJson.substring(startIndex, endIndex);

        } catch (Exception e) {
            return null;
        }
    }
}
