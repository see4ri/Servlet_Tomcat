package com.shen.accesstomcatserver;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final int HANDLE_RECEIVE_DATA = 10;
	private static final String TAG = "AccessTomcatServer";
	private static final String URL_TOMCAT_SERVER = "http://192.168.5.110:8080/ServletDemo";
	
	Button m_access_server;
	Button m_access_internet;
	Button m_page_back;
	Button m_page_forward;
	TextView m_display_data;
	WebView	m_webview_page;
	ProgressBar m_pageLoadProgress;
	EditText m_urlText;
	
	String m_url = URL_TOMCAT_SERVER;
	Handler m_handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what) 
			{
			case HANDLE_RECEIVE_DATA:
		    	if (null != m_display_data) 
		    	{
		    		m_display_data.setText((String)(msg.obj));
				}
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_access_server = (Button) findViewById(R.id.btn_access_tomcat_server);
		m_access_internet = (Button) findViewById(R.id.btn_access_internet);
		m_page_back = (Button) findViewById(R.id.btn_go_back);
		m_page_forward = (Button) findViewById(R.id.btn_go_forward);
		m_display_data = (TextView) findViewById(R.id.text_data);
		m_urlText = (EditText) findViewById(R.id.editText1);
		m_urlText.setText("http://www.sina.com.cn");
		m_pageLoadProgress = (ProgressBar) findViewById(R.id.page_load_progress);
		m_webview_page = (WebView) findViewById(R.id.webView_client);
		/*设置webview的背景颜色*/
		m_webview_page.setBackgroundColor(Color.GRAY);
		/*WebView在load url时，默认会启动一个browser app，这个browser会open&load目的url；
		但是我们可以override这种行为for my-webView，然后在my-webView中open&load目的url；
		方法：需要为my-webView provide一个WebViewClient，using method setWebViewClient()；*/
//		m_webview_page.setWebViewClient(new WebViewClient());
		/*当a clicked link load时，如果想more control over，可以创建自己的webviewclient，并override
		shouldOverrideUrlLoading(webview，url)；*/
		m_webview_page.setWebViewClient(new MyWebViewClient());
		
		m_webview_page.setWebChromeClient(new MyWebChromeClient());
		
		/*Using JavaScript in WebView*/
		WebSettings webSettings = m_webview_page.getSettings();
		webSettings.setJavaScriptEnabled(true);
		m_webview_page.addJavascriptInterface(new WebAppInterface(this), "Android");
		
		/*取得WebView的Settings属性*/
		WebSettings settings = m_webview_page.getSettings();
		Log.d(TAG, "DefaultTextEncodingName: " + settings.getDefaultTextEncodingName());
		
		/*WebView默认Text编码格式为latin-1*/
		/*前提：Server.xml已经配置了UTF-8的编码格式。 保证jsp/html页面，不会出现乱码*/
		/*但是从访问的server/folder-test/.txt文件却是GB2312，所以造成txt内容中文为乱码*/
		/*解决方法：1.修改原txt文件格式为UTF-8或者与WebView编码格式一致
			  2.设置WebView的settings编码格式为GB2312或者与server/txt文件编码格式一致。*/
//		settings.setDefaultTextEncodingName("utf-8"/*"UTF-8"*/);
		settings.setDefaultTextEncodingName("gb2312"/*"UTF-8"*/);
		Log.d(TAG, "UpdateTextEncodingName: " + settings.getDefaultTextEncodingName());
		
		m_access_server.setOnClickListener(this);
		m_access_internet.setOnClickListener(this);
		m_page_back.setOnClickListener(this);
		m_page_forward.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			/*点击tomcat-server按钮，请求服务器数据*/
		case R.id.btn_access_tomcat_server:
			m_url = URL_TOMCAT_SERVER;
			Log.d(TAG, "load url = " + m_url);
			asyncLoadMyServerData();
//			loadMyServerData();
			break;
			
		case R.id.btn_access_internet:
			m_url = m_urlText.getText().toString();
//			m_url = "http://rss.sina.com.cn/news/marquee/ddt.xml";
			m_url = (null != m_url) ? m_url : m_urlText.getHint().toString();
			Log.d(TAG, "load url = " + m_url);
//			asyncLoadInternetData();
			loadInternetData();
			break;
			
			/*点击tomcat-server按钮，请求服务器数据*/
		case R.id.btn_go_back:
			// go back
			pageGoBack();
			break;

			/*点击tomcat-server按钮，请求服务器数据*/
		case R.id.btn_go_forward:
			// go forward
			pageGoForward();
			break;

		default:
			break;
		}
	}
	
	private void asyncLoadInternetData() {
		asyncLoadMyServerData();
	}
	
	private void loadInternetData() {
	   loadMyServerData();
	}

	private void pageGoForward() {
		/*如果有forward history，则可以go forward；*/
		if (m_webview_page.canGoForward()) {
			m_webview_page.goForward();
		}
	}

	private void pageGoBack() {
		/*如果有back history，则可以go back；*/
		if (m_webview_page.canGoBack()) {
			m_webview_page.goBack();
		}
	}

	/*异步加载server网页。*/
	/*webview不支持异步加载，会抛异常warning：All WebView methods must be called on the UI thread. 
	 * Future versions of WebView may not support use on other threads.*/
	private void asyncLoadMyServerData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
//				getDataFromServer(m_url);
				loadWebPage(m_url);
			}
		}, "Http-Get").start();
	}
	
	/*在UI-Thread中同步加载server*/
	private void loadMyServerData() {
//		getDataFromServer(m_url);
	    loadWebPage(m_url);
	}

	/*Click返回按钮，如果有web page history，则返回上一页，否则，退出app；*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if ((keyCode == KeyEvent.KEYCODE_BACK) && m_webview_page.canGoBack()) {
			m_webview_page.goBack();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	protected void loadWebPage(String url) {
	      String uriServer = "http://192.168.5.110:8080/HelloWorld";
	      uriServer = null != url ? url : uriServer;
	      m_webview_page.setWebViewClient(new MyWebViewClient());
	      m_webview_page.loadUrl(uriServer);
	}

	protected void getDataFromServer(String url) {

//      String uriServer = "http://www.baidu.com";
//      String uriServer = "http://localhost:8080/";
//      String uriServer = "http://10.0.0.2:8080/";
      String uriServer = "http://192.168.5.110:8080/HelloWorld";
//      String uriServer = "http://127.0.0.1:8080/";
      uriServer = null != url ? url : uriServer;
      
      /*创建HttpGet连接*/
      HttpGet httpRequest = new HttpGet(uriServer);
      try
      {
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          int code = httpResponse.getStatusLine().getStatusCode();
          if (code == 200)
          {
              /*取出应答字符串*/
              String result = EntityUtils.toString(httpResponse.getEntity()); 
              /*删除多余字符串*/
//              result = erase_replace();
//              Toast.makeText(this, result, Toast.LENGTH_LONG).show();
              reminder(result);
          }
          else
          {
              String errorReason = "Error Response: " + httpResponse.getStatusLine().toString();
//              Toast.makeText(this, errorReason, Toast.LENGTH_LONG).show();
              reminder(errorReason);
          }
      }
      catch (ClientProtocolException e)
      {
          // TODO Auto-generated catch block
          reminder(e.getMessage());
          e.printStackTrace();
      }
      catch (IOException e)
      {
          // TODO Auto-generated catch block
          reminder(e.getMessage());
          e.printStackTrace();
      }
      catch (Exception e)
      {
    	  // TODO Auto-generated catch block
    	  reminder(e.getMessage());
    	  e.printStackTrace();
      }
      
  		
	}
	
    private void reminder(String reminder)
    {
    	String data = getString(R.string.get_data, reminder);
    	m_handler.obtainMessage(HANDLE_RECEIVE_DATA, data).sendToTarget();

//        Toast.makeText(this, reminder, Toast.LENGTH_LONG).show();
    }
    
    /*在android app中，会调用MyWebViewClient的所有方法。*/
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	/*取得WebView的Settings属性*/
        	Log.d(TAG, "shouldOverrideUrlLoading");
        	WebSettings settings = view.getSettings();
        	Log.d(TAG, "DefaultTextEncodingName: " + settings.getDefaultTextEncodingName());
        	
        	String host = Uri.parse(url).getHost(); 
        	Log.i(TAG, "access host: " + host);
        	Log.i(TAG, "access url: " + url);
        	
            if (isHostValid(host)/*host.equals("192.168.5.110"*//*"www.example.com"*/) {
                // This is my web site, so do not override; let my WebView load the page
                view.loadUrl(url);
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        /*定义一个规则，host为本机，internet(.com .cn .com.cn .net .org etc.)，启用MyWebView*/
		private boolean isHostValid(String host) {
			/*使用正则表达式判断*/
//			String regex = "(192.168.5.110)|((^[a-z]+\\.)+[a-z]{2,3}$)";
			String regex1 = "(192.168.5.110)|(^[a-z]\\.?([a-z]+\\.)+((com)|(cn)|(com.cn)|(net)|(org)|(name)|(info))$)";
			
			return Pattern.compile(regex1).matcher(host).matches();
		}

		@Override
		public void onLoadResource(WebView view, String url) {
//			Log.d(TAG, "onLoadResource");
			super.onLoadResource(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.d(TAG, "onPageFinished");
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d(TAG, "onPageStarted");
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.d(TAG, "onReceivedError");
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
    }
    
    /*在android中，只调用了onProgressChanged()方法。*/
    private class MyWebChromeClient extends WebChromeClient
    {

		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			Log.d(TAG, "onConsoleMessage");
			return super.onConsoleMessage(consoleMessage);
		}

		/*需要JavaScript中调用alert(message)方法*/
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			Log.d(TAG, "onJsAlert");
			new AlertDialog.Builder(MainActivity.this)  
            .setTitle("JS提示")  
            .setMessage(message)  
            .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {  
                public void onClick(DialogInterface dialog, int which) {  
                    result.confirm();  
                }  
            })  
            .setCancelable(false)  
            .create().show();  
//			return super.onJsAlert(view, url, message, result);
			return true;
		}

		@Override
		public boolean onJsBeforeUnload(WebView view, String url,
				String message, JsResult result) {
			Log.d(TAG, "onJsBeforeUnload");
			return super.onJsBeforeUnload(view, url, message, result);
		}

		/*需要JavaScript中调用Confirm(message)方法*/
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			Log.d(TAG, "onJsConfirm");
			/*super.onJsConfirm会自动创建一个提示dialog，title为来自"http://host"的提示*/
			return super.onJsConfirm(view, url, message, result);
		}

		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			Log.d(TAG, "onJsPrompt");
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}

		@Override
		public boolean onJsTimeout() {
			Log.d(TAG, "onJsTimeout");
			return super.onJsTimeout();
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			Log.d(TAG, "onProgressChanged");
			super.onProgressChanged(view, newProgress);
			
			int pageProgress = view.getProgress();
			Log.d(TAG, "pageProgress = " + pageProgress);
//			MainActivity.this.setProgress(pageProgress);
			if (null != m_pageLoadProgress)
			{
				m_pageLoadProgress.setProgress(pageProgress);
				if (pageProgress <= 0 | pageProgress >= 100) {
					m_pageLoadProgress.setVisibility(View.INVISIBLE);
				}
				else {
					m_pageLoadProgress.setVisibility(View.VISIBLE);
				}
			}
		}

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			Log.d(TAG, "onShowCustomView");
			super.onShowCustomView(view, callback);
		}
    	
    }
    
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
        
        /*Parse xml*/
        public void launchSAXParse(String prompt)
        {
            Toast.makeText(mContext, prompt, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ServerXmlParse_SAX.class);
            startActivity(intent);
        }
    }
}
