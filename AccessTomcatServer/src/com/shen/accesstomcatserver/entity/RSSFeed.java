package com.shen.accesstomcatserver.entity;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed
{
    private String mTitle = null;
    private int mCount = 0;
    private List<RSSItem> mRsses = null;
    
    public RSSFeed()
    {
        mRsses = new ArrayList<RSSItem>();
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        this.mTitle = title;
    }

    public int getCount()
    {
        return mCount;
    }

    public List<RSSItem> getAllRssItems()
    {
        return mRsses;
    }

    public void setRsses(List<RSSItem> list)
    {
        this.mRsses = list;
    }
    
    /*负责将一个RSSItem加入到RSSFeed类中*/
    public int addRssItem(RSSItem rss)
    {
        mRsses.add(rss);
        mCount++;
        
        return mCount;
    }
    
    public RSSItem getRssItem(int location)
    {
        return (null != mRsses) ? mRsses.get(location) : null;
    }
}
