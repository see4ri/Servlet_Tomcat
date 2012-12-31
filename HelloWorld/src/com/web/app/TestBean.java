package com.web.app;
public class TestBean
{
  private String name =null;
  public TestBean(String nameInit)
  {
    this.name = nameInit;
  }
  public void setName(String newName)
  {
    this.name=newName;
  }
  public String getName()
  {
    return this.name;
  }
}
