package com.wzdsqyy.utils.helper;

import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;
import android.util.Log;

import org.xutils.http.RequestParams;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * 通过okhttp与xutils的测试
 */
public class HttpsHelper {
    private static final String TAG = "HttpsHelper";
    private static boolean isDebug = BuildConfig.DEBUG;

    /**
     * 用于单向认证
     *
     * @param alias 别名
     * @param cert  证书输入流,为Null信任所有，仅供测试使用，切勿用于实际产品
     * @return
     * @throws Exception
     */
    public static OkHttpClient getOkHttpsClient(OkHttpClient client,String alias,@Nullable InputStream cert) throws Exception {
        SSLSocketFactory factory;
        if(cert==null){
            factory=getSSLSocketFactory(null,null,null,null);
        }else{
            factory=getSSLSocketFactory(alias, cert, null, null);
        }
        return client.newBuilder().sslSocketFactory(getSSLSocketFactory(alias,cert,null,null)).build();
    }

    /**
     * 用于单向认证
     *
     * @param alias 别名
     * @param cert  证书输入流,为Null信任所有，仅供测试使用，切勿用于实际产品
     * @return
     * @throws Exception
     */
    public static RequestParams getRequestParams(RequestParams params, String alias, @Nullable InputStream cert) throws Exception {
        SSLSocketFactory factory;
        if(cert==null){
            factory=getSSLSocketFactory(null,null,null,null);
        }else{
            factory=getSSLSocketFactory(alias, cert, null, null);
        }
        params.setSslSocketFactory(factory);
        return params;
    }

    /**
     * 双向认证
     *
     * @param alias
     * @param cert
     * @param keyStore
     * @param password
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory(String alias, InputStream cert, InputStream keyStore, String password) throws Exception {
        char[] pass = password == null ? null : password.toCharArray();
        SSLContext ctx = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = null;
        TrustManagerFactory tmf;
        if (keyStore != null) {
            kmf = KeyManagerFactory.getInstance("X509");
            KeyStore kks = KeyStore.getInstance("BKS");
            kks.load(keyStore, pass);
            kmf.init(kks, pass);
        }
        TrustManager[] trustManagers;
        if (cert != null) {
            tmf = TrustManagerFactory.getInstance("X509");
            CertificateFactory crtf = CertificateFactory.getInstance("X.509");
            Certificate certificate = crtf.generateCertificate(cert);
            KeyStore tks = KeyStore.getInstance(KeyStore.getDefaultType());
            tks.load(null);
            if (tks.getCertificate(alias) == null) {
                tks.setCertificateEntry(alias, certificate);
            } else {
                if (isDebug) {
                    Log.e(TAG, "getSSLSocketFactory: " + alias + " 对应的证书已存在");
                }
            }
            tmf.init(tks);
            trustManagers = tmf.getTrustManagers();
        } else {
            trustManagers = new TrustManager[]{getUnSafeTrustManager()};
        }
        ctx.init(kmf == null ? null : kmf.getKeyManagers(), trustManagers, new SecureRandom());
        SSLSocketFactory factory = ctx.getSocketFactory();
        return factory;
    }

    private static TrustManager getUnSafeTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }
}
