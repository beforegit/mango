package cc.concurrent.mango.exception;

/**
 * 获取自增key失败异常
 *
 * @author ash
 */
public class ReturnGeneratedKeyException extends DataAccessException {

    public ReturnGeneratedKeyException(String message) {
        super(message);
    }

}
