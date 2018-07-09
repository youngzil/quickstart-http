package org.quickstart.http.jersey.sun;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
public class Person
{
    @FormParam(value="name")
    private String name;

    @FormParam(value="id")
    private int id;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String toString()
    {
        return "name="+name+",id="+id;
    }

}