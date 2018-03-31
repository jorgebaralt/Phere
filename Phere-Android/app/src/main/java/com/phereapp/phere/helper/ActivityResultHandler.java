package com.phereapp.phere.helper;

import android.content.Intent;

public interface ActivityResultHandler {
    void activityResultHandler(int requestCode, int resultCode, Intent data);
}
