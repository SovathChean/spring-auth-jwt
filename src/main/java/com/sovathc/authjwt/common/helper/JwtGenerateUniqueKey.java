package com.sovathc.authjwt.common.helper;

import java.util.UUID;

public class JwtGenerateUniqueKey {
    public static String getUniqueKey(String username)
    {
        String uniqueKey = UUID.randomUUID().toString() + username;

        return uniqueKey;
    }
}
