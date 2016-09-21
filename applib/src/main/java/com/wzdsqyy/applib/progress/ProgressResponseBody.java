package com.wzdsqyy.applib.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;

/**
 * Created by Quinta on 2016/8/19
 */
class ProgressResponseBody extends ResponseBody{
    private final ResponseBody body; //实际的待包装响应体
    private final ProgressListener progressListener; //进度回调监听
    private long contentLength = -1;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.body = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        if (contentLength == -1) {
            contentLength = body.contentLength();
        }
        return contentLength;
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     * @throws IOException 异常
     */
    @Override
    public BufferedSource source() {
        return Okio.buffer(source(body.source()));
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if(!progressListener.onProgress(totalBytesRead, contentLength(), bytesRead == -1)){
                    close();
                    return -1;
                }
                return bytesRead;
            }
        };
    }
}
