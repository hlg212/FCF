package  io.github.hlg212.fcf.model;

import org.apache.commons.lang.StringUtils;

public class ActionResultBuilder<T> {
    private ActionResult<T> actionResult;

    public ActionResultBuilder() {
        actionResult = new ActionResult<T>();
    }


    /**
     * 构造响应结果为成功的构造器
     *
     *  
     */
    public static <T> ActionResultBuilder<T> success() {
        ActionResultBuilder<T> actionResultBuilder = new ActionResultBuilder<T>();
        actionResultBuilder.withStatus(ActionResultStatus.SUCCESS);
        return actionResultBuilder;
    }

    /**
     * 构造响应结果为服务器内部异常的构造器
     *
     *  
     */
    public static <T> ActionResultBuilder<T> serverError() {
        ActionResultBuilder<T> actionResultBuilder = new ActionResultBuilder<T>();
        actionResultBuilder.withStatus(ActionResultStatus.INTERNAL_SERVER_ERROR);
        return actionResultBuilder;
    }

    /**
     * 构造响应结果为服务器业务异常的构造器
     *
     *  
     */
    public static <T> ActionResultBuilder<T> businessError() {
        ActionResultBuilder<T> actionResultBuilder = new ActionResultBuilder<T>();
        actionResultBuilder.withStatus(ActionResultStatus.BUSINESS_ERROR);
        return actionResultBuilder;
    }


    public void withStatus(ActionResultStatus status) {
        actionResult.setCode(String.valueOf(status.getCode()));
        if (StringUtils.isEmpty(actionResult.getMsg())) {
            actionResult.setMsg(status.getReason());
        }
        actionResult.setSuccess(ActionResultStatus.SUCCESS.getCode() == status.getCode());
    }


    public ActionResultBuilder<T> withData(T data) {
        actionResult.setData(data);
        return this;
    }

    public ActionResultBuilder<T> withCode(String code) {
        actionResult.setCode(code);
        return this;
    }

    public ActionResultBuilder<T> withMsg(String msg) {
        actionResult.setMsg(msg);
        return this;
    }

    public ActionResultBuilder<T> withSuccess(boolean success) {
        actionResult.setSuccess(success);
        return this;
    }

    public ActionResult<T> build() {
        return actionResult;
    }


    public enum ActionResultStatus {

        /**
         * 成功.
         */
        SUCCESS(200, "SUCCESS"),

        /**
         * 服务器内部错误.
         */
        INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),

        /**
         * 业务异常
         */
        BUSINESS_ERROR(501, "BUSINESS_ERROR");

        /**
         * 状态代码.
         */
        private final int code;

        /**
         * 原因描述.
         */
        private final String reason;

        /**
         * 构造ActionResultStatus.
         *
         * @param statusCode   the status code
         * @param reasonPhrase the reason phrase
         */
        ActionResultStatus(final int statusCode, final String reasonPhrase) {
            this.code = statusCode;
            this.reason = reasonPhrase;
        }

        public int getCode() {
            return this.code;
        }

        public String getReason() {
            return this.reason;
        }

    }
}
