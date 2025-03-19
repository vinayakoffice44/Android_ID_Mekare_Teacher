package com.example.id_maker_teacher.Utility;

import android.content.Context;

import com.example.id_maker_teacher.Model.API_Model.ResponseModel;


public class ErrorUtility {
    public void CatchError(Context context){
        AnimationUtility.showMessageDialog(context, "000", "Application Unable to Response,Close App And try again", new AnimationUtility.OnOkClickListener() {
            @Override
            public void onOkClicked() {
                AnimationUtility.dismissMessageDialog();
            }
        });
    }

    public void APIFailedError(Context context,Throwable t){
        if (t instanceof java.net.SocketTimeoutException) {
            // Handle Timeout Exception
            AnimationUtility.showMessageDialog(context, "408", "Server is taking too long to respond. Please try again later.", new AnimationUtility.OnOkClickListener() {
                @Override
                public void onOkClicked() {
                    AnimationUtility.dismissMessageDialog();
                }
            });
        }
        else if (t instanceof java.net.UnknownHostException) {
            // Handle No Internet Exception
            AnimationUtility.showMessageDialog(context, "503", "No Internet Connection. Please check your network and try again.", new AnimationUtility.OnOkClickListener() {
                @Override
                public void onOkClicked() {
                    AnimationUtility.dismissMessageDialog();
                }
            });
        }
        else {
            // Handle General Failure
            AnimationUtility.showMessageDialog(context, "000", "Unable to connect to the server. Please try again.", new AnimationUtility.OnOkClickListener() {
                @Override
                public void onOkClicked() {
                    AnimationUtility.dismissMessageDialog();
                }
            });
        }
    }

    public void APIErrorMessage(Context context, ResponseModel responseModel){
        AnimationUtility.showMessageDialog(context, String.valueOf(responseModel.getCode()), responseModel.getMessage(), new AnimationUtility.OnOkClickListener() {
            @Override
            public void onOkClicked() {
                AnimationUtility.dismissMessageDialog();
            }
        });
    }

    public void SimpleError(Context context,String message){
        AnimationUtility.showMessageDialog(context, "000", message, new AnimationUtility.OnOkClickListener() {
            @Override
            public void onOkClicked() {
                AnimationUtility.dismissMessageDialog();
            }
        });
    }
}
