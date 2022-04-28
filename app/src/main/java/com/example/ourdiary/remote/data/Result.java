package com.example.ourdiary.remote.data;

import java.util.List;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    /**获取文章列表
     * 到时候向下转型即可*/
    public Object toList() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return success.getData();
        } else {
            return null;
        }
    }

    // Success sub-class
    public final static class Success<T> extends Result {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // 密码错误
    public final static class Failed extends Result {
        private String msg;

        public Failed(String msg) {
            this.msg = msg;
        }

        public String getData() {
            return this.msg;
        }
    }

    // Error sub-class
    public final static class Error extends Result {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}