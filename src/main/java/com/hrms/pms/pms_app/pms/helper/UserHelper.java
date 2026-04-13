package com.hrms.pms.pms_app.pms.helper;

import java.util.UUID;

public class UserHelper {

    public static UUID parseUUID(String uuid){
        return UUID.fromString(uuid);
    }
}
