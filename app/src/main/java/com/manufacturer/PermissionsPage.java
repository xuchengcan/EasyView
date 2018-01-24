package com.manufacturer;

import android.content.Intent;


public interface PermissionsPage {
    String PACK_TAG = "package";

    Intent settingIntent() throws Exception;
}
