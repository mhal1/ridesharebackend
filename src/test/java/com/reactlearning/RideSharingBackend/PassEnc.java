package com.reactlearning.RideSharingBackend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEnc {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "123";
        String encoded = encoder.encode(raw);

        System.out.println(encoded);

    }
}
