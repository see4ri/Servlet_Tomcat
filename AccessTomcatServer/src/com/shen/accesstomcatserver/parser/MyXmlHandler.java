package com.shen.accesstomcatserver.parser;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.shen.accesstomcatserver.entity.RSSFeed;
import com.shen.accesstomcatserver.entity.RSSItem;

import android.util.Log;

public class MyXmlHandler extends DefaultHandler
{
    public static final String Tag = "MyXmlHandler";
    
    // The child node.
    final int RSS_CHANNEL = 0;
    final int RSS_ITEM = 1;
    final int RSS_TITLE = 2;
    final int RSS_LINK = 3;
    final int RSS_CATEGORY = 4;
    final int RSS_PUBDATE = 5;
    final int RSS_DESCRIPTION = 6;
    
    int m_current_state = -1;
    
    private RSSFeed rssFeed;
    private RSSItem rssItem;
    
    public MyXmlHandler()
    {
        
    }
    
    public RSSFeed getFeed()
    {
        return rssFeed;
    }
    
    @Override
    public void startDocument() throws SAXException
    {
        // TODO Auto-generated method stub
//        super.startDocument();
        
        Log.i(Tag, "startDocument");
        
        rssFeed = new RSSFeed();
        rssItem = new RSSItem();
    }
    
    @Override
    public void endDocument() throws SAXException
    {
        // TODO Auto-generated method stub
//        super.endDocument();
        
        Log.i(Tag, "endDocument");
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        // TODO Auto-generated method stub
//        super.startElement(uri, localName, qName, attributes);
        
        Log.i(Tag, "startElement");
        
        Log.d(Tag+"--startElement", String.format("uri=%s,localName=%s,qName=%s", uri,localName,qName));
        
        if (localName.equals("channel"))
        {
            m_current_state = RSS_CHANNEL;
            return;
        }
        
        if (localName.equals("item"))
        {
            rssItem = new RSSItem();
            return;
        }
        
        if (localName.equals("title"))
        {
            m_current_state = RSS_TITLE;
            return;
        }
        
        if (localName.equals("link"))
        {
            m_current_state = RSS_LINK;
            return;
        }
        
        if (localName.equals("category"))
        {
            m_current_state = RSS_CATEGORY;
            return;
        }
        
        if (localName.equals("pubDate"))
        {
            m_current_state = RSS_PUBDATE;
            return;
        }
        
        if (localName.equals("description"))
        {
            m_current_state = RSS_DESCRIPTION;
            return;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        // TODO Auto-generated method stub
//        super.endElement(uri, localName, qName);
        
        Log.i(Tag, "endElement");
        
        Log.d(Tag+"--endElement", String.format("uri=%s,localName=%s,qName=%s", uri,localName,qName));
        
        if (localName.equals("item"))
        {
            rssFeed.addRssItem(rssItem);
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        // TODO Auto-generated method stub
//        super.characters(ch, start, length);
        
        Log.i(Tag, "characters");
        
        String result = new String(ch, start, length);
        Log.d(Tag + "characters", "result = " + result);
        switch (m_current_state)
        {
        case RSS_CHANNEL:
            m_current_state = -1;
            break;
        case RSS_TITLE:
            rssItem.setTitle(result);
            m_current_state = -1;
            break;
        case RSS_LINK:
            rssItem.setLink(result);
            m_current_state = -1;
            break;
        case RSS_CATEGORY:
            rssItem.setCategory(result);
            m_current_state = -1;
            break;
        case RSS_PUBDATE:
            rssItem.setPubDate(result);
            m_current_state = -1;
            break;
        case RSS_DESCRIPTION:
            rssItem.setDescription(result);
            m_current_state = -1;
            break;

        default:
            m_current_state = -1;
            break;
        }
    }
    
    @Override
    public void setDocumentLocator(Locator locator)
    {
        // TODO Auto-generated method stub
        super.setDocumentLocator(locator);
        
        Log.i(Tag, "setDocumentLocator");
    }
    
    @Override
    public void error(SAXParseException e) throws SAXException
    {
        // TODO Auto-generated method stub
        super.error(e);
        
        Log.i(Tag, "error");
    }
    
    
}
