package cn.nevsao.common.exception;

/**
 * 文件下载异常
 */
public class FileDownloadException  extends BaseException {

    private static final long serialVersionUID = -3608667856397125671L;

    public FileDownloadException(String message) {

        super(ResponseCodeEnum.FILE_DOWNLOAD_ERROR.getCode(), message);
    }
}