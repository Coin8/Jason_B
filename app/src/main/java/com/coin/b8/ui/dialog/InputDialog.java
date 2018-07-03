package com.coin.b8.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.coin.b8.R;

/**
 * Created by zhangyi on 2018/7/3.
 */
public class InputDialog{
    public boolean isDialogShown = false;
    private AlertDialog alertDialog;
    private boolean isCanCancel = false;

    private Context context;
    private String title;
    private String defaultInputText = "";
    private String defaultInputHint = "";
    private String okButtonCaption = "确定";
    private String cancelButtonCaption = "取消";
    private InputDialogOkButtonClickListener onOkButtonClickListener;


    public interface InputDialogOkButtonClickListener {
        void onClick(Dialog dialog, String inputText);
    }

    public InputDialog(Context context, String title,String okButtonCaption,
                       InputDialogOkButtonClickListener onOkButtonClickListener,
                       String cancelButtonCaption) {
        this.alertDialog = null;
        this.context = context;
        this.title = title;
        this.okButtonCaption = okButtonCaption;
        this.cancelButtonCaption = cancelButtonCaption;
        this.onOkButtonClickListener = onOkButtonClickListener;
    }

    private TextView txtDialogTitle;
    private EditText txtInput;
    private TextView btnSelectNegative;
    private TextView btnSelectPositive;

    public void showDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context, R.style.inputDialoStyle);
        builder.setCancelable(isCanCancel);
        alertDialog = builder.create();
        alertDialog.setView(new EditText(context));
        if (isCanCancel) alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (alertDialog != null) alertDialog.dismiss();
                isDialogShown = false;
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (alertDialog != null) alertDialog.dismiss();
                isDialogShown = false;
            }
        });

        Window window = alertDialog.getWindow();
        window.setWindowAnimations(R.style.inputDialogAnimStyle);
        alertDialog.show();
        window.setContentView(R.layout.input_dialog_layout);
        txtDialogTitle = window.findViewById(R.id.txt_dialog_title);
        txtInput = window.findViewById(R.id.txt_input);
        btnSelectNegative = window.findViewById(R.id.btn_selectNegative);
        btnSelectPositive = window.findViewById(R.id.btn_selectPositive);
        txtInput = window.findViewById(R.id.txt_input);
        txtInput.setVisibility(View.VISIBLE);
        txtInput.setText(defaultInputText);
        txtInput.setHint(defaultInputHint);
        if (isNull(title)) {
            txtDialogTitle.setVisibility(View.GONE);
        } else {
            txtDialogTitle.setVisibility(View.VISIBLE);
            txtDialogTitle.setText(title);
        }
        if(!isNull(okButtonCaption)){
            btnSelectPositive.setText(okButtonCaption);
        }
        btnSelectPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkButtonClickListener != null)
                    onOkButtonClickListener.onClick(alertDialog, txtInput.getText().toString());
                alertDialog.dismiss();
            }
        });
        btnSelectNegative.setVisibility(View.VISIBLE);
        if(!isNull(cancelButtonCaption)){
            btnSelectNegative.setText(cancelButtonCaption);
        }
        btnSelectNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    public void doDismiss() {
        if (alertDialog!=null)alertDialog.dismiss();
    }

    public InputDialog setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) alertDialog.setCancelable(canCancel);
        return this;
    }

    public InputDialog setDefaultInputText(String defaultInputText) {
        this.defaultInputText = defaultInputText;
        if (alertDialog != null) {
            txtInput.setText(defaultInputText);
            txtInput.setHint(defaultInputHint);
        }
        return this;
    }

    public InputDialog setDefaultInputHint(String defaultInputHint) {
        this.defaultInputHint = defaultInputHint;
        if (alertDialog != null) {
            txtInput.setText(defaultInputText);
            txtInput.setHint(defaultInputHint);
        }
        return this;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private boolean isNull(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("null")) {
            return true;
        }
        return false;
    }

}
