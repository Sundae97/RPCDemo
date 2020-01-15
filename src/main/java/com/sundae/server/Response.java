package com.sundae.server;

import com.sundae.ProtocolData;

/**
 * Response
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class Response {

    private Throwable exception;

    private Object result;

    private String resultType;

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "Response{" +
                "exception=" + exception +
                ", result=" + result +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}
