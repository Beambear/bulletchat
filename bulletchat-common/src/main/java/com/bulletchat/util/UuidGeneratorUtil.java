package com.bulletchat.util;

import java.util.UUID;

public class UuidGeneratorUtil {
    private UuidGeneratorUtil(){

    }

    public static String generateUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
