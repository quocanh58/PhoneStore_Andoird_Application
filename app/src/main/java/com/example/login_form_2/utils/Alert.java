package com.example.login_form_2.utils;

import android.app.AlertDialog;
import android.content.Context;

public class Alert {
    public interface OnDialogButtonClickListener {
        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }

    public static void confirm(
            Context context,
            String title,
            String message,
            final OnDialogButtonClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (listener != null) {
                        listener.onPositiveButtonClick();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    if (listener != null) {
                        listener.onNegativeButtonClick();
                    }
                })
                .show();
    }

    public static void confirm(
            Context context,
            String message,
            final OnDialogButtonClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cảnh báo")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (listener != null) {
                        listener.onPositiveButtonClick();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    if (listener != null) {
                        listener.onNegativeButtonClick();
                    }
                })
                .show();
    }


    public static void confirmLogin(
            Context context,
            String title,
            String message,
            final OnDialogButtonClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (listener != null) {
                        listener.onPositiveButtonClick();
                    }
                })
                .show();
    }



    public static void alert(
            Context context,
            String title,
            String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .show();
    }

    public static void alert(
            Context context,
            String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .show();
    }

    public static void error(
            Context context,
            String message) {
//        final MediaPlayer mp = MediaPlayer.create(context, R.raw.error_sound);
//        mp.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .show();
    }
}
