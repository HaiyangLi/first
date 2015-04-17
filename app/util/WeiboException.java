package util;
/**
 * 异常类
 * @author lihaiyang
 *
 */
public class WeiboException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6491102915915761351L;

	public WeiboException(String msg) {
        super(msg);
    }

    public WeiboException(Exception cause) {
        super(cause);
    }
    
    
}

