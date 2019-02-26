package com.zhi.fiction.exception;

/**
 * 
 * BizException
 *
 * @author yabing.sun
 * @date 2016年8月16日 下午1:27:23
 * @version 
 *
 */
public class BizException extends RuntimeException {
    
    public final static String ACCOUNT_ID_ERROR = "ACCOUNT_ID_ERROR";
    
    
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    /**
     * 业务编号：交易流水号、融资单号等交易唯一流水号
     */
    private String businessCode;

    public BizException(String message) {
        this.message = message;
    }
    
    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BizException(String message, String code) {
        this.message = message;
        this.businessCode = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    public String getBusinessCode() {
        return businessCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BizException{");
        sb.append("message='").append(message).append('\'');
        sb.append(", businessCode='").append(businessCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
