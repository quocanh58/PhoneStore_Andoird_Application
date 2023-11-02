package com.example.login_form_2.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {
    private static ProgressDialog progressDialog;

    public static void setLoading(Context context, boolean isLoading) {
        if (isLoading) {
            showLoadingDialog(context);
        } else {
            hideLoadingDialog();
        }
    }

    private static void showLoadingDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    private static void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
