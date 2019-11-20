package com.explorea;

import com.explorea.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Arrays;

public class TokenVerifier {

    private static final String CLIENT_ID_1 =
            "";
    private static final String CLIENT_ID_2 =
            "";
    private static final String CLIENT_ID_3 =
            "";
    private static final String CLIENT_ID_4 =
            "";
    private static final String CLIENT_ID_5 =
            "";

    private static TokenVerifier verifier;

    private GoogleIdTokenVerifier googleVerifier;

    private TokenVerifier() {
        googleVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3, CLIENT_ID_4, CLIENT_ID_5))
                .build();
    }

    public static TokenVerifier getInstance(){
        if(verifier == null){
            verifier = new TokenVerifier();
        }
        return verifier;
    }

    public String getGoogleUserId(String tokenString) throws Exception{

        tokenString = tokenString.replace("Bearer ","");
        GoogleIdToken idToken = googleVerifier.verify(tokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();

            return userId;

        } else {
            return null;
        }
    }

}
