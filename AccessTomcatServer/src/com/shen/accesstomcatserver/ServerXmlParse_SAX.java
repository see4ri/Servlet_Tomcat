package com.shen.accesstomcatserver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpConnection;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.shen.accesstomcatserver.entity.RSSFeed;
import com.shen.accesstomcatserver.parser.MyXmlHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServerXmlParse_SAX extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.xml_sax_layout);
        
        Button parseSAX = (Button) findViewById(R.id.btn_parse_sax);
        parseSAX.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        parseXml();
                    }
                }, "Parse XML").start();
            }
        });
    }
    
    private void parseXml()
    {
        parseStyle_SAX();
    }

    private RSSFeed parseStyle_SAX()
    {
//        String url_str = "http://rss.sina.com.cn/news/marquee/ddt.xml";
        String url_str = "http://192.168.5.110:8080/HelloWorld/xml/ddt.xml";
        
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try
        {
            URL url = new URL(url_str);
            
            saxParser = saxFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            MyXmlHandler xmlHandler = new MyXmlHandler();
            xmlReader.setContentHandler(xmlHandler);
            
            if (url != null)
            {
                URLConnection connection = url.openConnection();
                connection.connect();
//                if ()
//                {
//                    
//                }
                xmlReader.parse(new InputSource(/*url.openStream()*/connection.getInputStream()));
                
                return xmlHandler.getFeed();
            }
            
            
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
