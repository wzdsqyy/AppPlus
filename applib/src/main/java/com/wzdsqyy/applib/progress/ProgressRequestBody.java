package com.wzdsqyy.applib.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Quinta on 2016/8/19.
 */
class ProgressRequestBody extends RequestBody {

    private final RequestBody body;//实际的待包装请求体
    private final ProgressListener progressListener;//进度回调监听
    private BufferedSink bufferedSink;//包装完成的BufferedSink
    private long contentLength=-1;

    /**
     * 构造函数，赋值
     *
     * @param requestBody      待包装的请求体
     * @param progressListener 回调接口
     */
    public ProgressRequestBody(RequestBody requestBody, ProgressListener progressListener) {
        this.body = requestBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        if(contentLength==-1){
            contentLength=body.contentLength();
        }
        return contentLength;
    }

    /**
     * 重写进行写入
     *
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        body.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                if(!progressListener.onProgress(bytesWritten, contentLength(), bytesWritten == contentLength())){
                    close();
                }
            }
        };
    }
}
