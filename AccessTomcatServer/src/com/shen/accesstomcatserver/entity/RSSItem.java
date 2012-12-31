package com.shen.accesstomcatserver.entity;

public class RSSItem
{
    String channel = null;
    String item = null;
    String title = null;
    String link = null;
    String category = null;
    String pubDate = null;
    String description = null;
    
    public RSSItem()
    {
        
    }
    
    public String getChannel()
    {
        return channel;
    }
    
    public void setChannel(String channel)
    {
        this.channel = channel;
    }
    
    public String getItem()
    {
        return item;
    }
    
    public void setItem(String item)
    {
        this.item = item;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getPubDate()
    {
        return pubDate;
    }
    
    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
