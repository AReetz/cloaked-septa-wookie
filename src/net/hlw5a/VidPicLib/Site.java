package net.hlw5a.VidPicLib;

import java.awt.Image;
import java.net.URL;

public class Site {
	
	private Integer id;
	private String name;
	private URL url;
	private String imageName;
	private Image image;
	
    public Integer getId() { return id; }
    public String getName() { return name; }
    public URL getUrl() { return url; }
    public String getImageName() { return imageName; }
    public Image getImage() { return image; }

    public Site(Integer id, String name, URL url, String imageName, Image image)
    {
    	this.id = id;
    	this.name = name;
    	this.url = url;
    	this.imageName = imageName;
    	this.image = image;
    }
}

