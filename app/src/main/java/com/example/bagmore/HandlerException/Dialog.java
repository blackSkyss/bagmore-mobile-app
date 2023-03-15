package com.example.bagmore.HandlerException;

import android.app.AlertDialog;
import android.content.Context;

public class Dialog {
    public static void showDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("OK", null).show();
    }
}
