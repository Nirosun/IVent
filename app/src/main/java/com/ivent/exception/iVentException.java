package com.ivent.exception;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Luciferre on 11/4/15.
 */
public class iVentException extends Exception {

    private int errorNo;//error number

    private String errorMsg;//error message

    //Exception enumeration
    public enum ExceptionEnum {
        MissingInput(1);
        private int errorNumber;

        ExceptionEnum(int errorNumber) {
            this.setErrorNumber(errorNumber);
        }

        public int getErrorNumber() {
            return errorNumber;
        }

        public void setErrorNumber(int errorNumber) {
            this.errorNumber = errorNumber;
        }

    }

    public iVentException(ExceptionEnum exceptionEnum) {
        this.errorNo = exceptionEnum.getErrorNumber();
        this.errorMsg = exceptionEnum.toString();
        log();
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    //Log error message to file
    public void log() {
        File file = new File("~/AndroidStudioProjects/log.txt");
        try {
            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            Date date = new Date();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(new Timestamp(date.getTime())).append(": ErrorNo[").append(errorNo).append("] [")
                    .append(errorMsg).append("].\n");
            bw.append(stringBuilder.toString());
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Fix different exceptions
    public void fix(Context context, int errno) {

        FixHelper fixHelper = new FixHelper();

        switch (errno) {
            case 1:
                fixHelper.fixMissingInput(context);
                break;

            default:
                break;
        }
    }

}
