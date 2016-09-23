package com.wzdsqyy.applibDemo.jsbridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.jsbridge.BridgeHandler;
import com.wzdsqyy.jsbridge.BridgeModel;
import com.wzdsqyy.jsbridge.BridgeWebViewClient;
import com.wzdsqyy.jsbridge.JsCallBack;
import com.wzdsqyy.jsbridge.JsReturnPrame;
import com.wzdsqyy.jsbridge.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class JsBridgeActivity extends AppCompatActivity implements BridgeModel {


    private WebView webView;
    BridgeWebViewClient client;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        webView= (WebView) findViewById(R.id.webview);
        client=new BridgeWebViewClient(this);
        client.registerHandler("sharejs", new BridgeHandler() {
            @Override
            public void handler(String data) {
                text.setText(data);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.onDestroy();
    }

    public void callJs(View view){
        client.send("Java call Js");
    }

    public void callJsDelay(View v){
        client.send("Java call Js ,回调java" , new JsCallBack() {
            @Override
            public void onCallBack(String data) {
                text.setText(data);
            }
        });
    }

    @Override
    public String jsHandlerFromNative(String data) {
        return String.format("javascript:WebViewJavascriptBridge._handleMessageFromNative('%s')",data);
    }

    @Override
    public String jsHandlerFetchQuene() {
        return "javascript:WebViewJavascriptBridge._fetchQueue();";
    }
}
