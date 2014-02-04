package net.hlw5a.VidPicLib;

import java.awt.Image;
import java.net.URL;
import java.util.Vector;

public class Site {
	
	private Integer id;
	private String name;
	private URL url;
	private String imageName;
	private Image image;
	private Vector<Set> sets = new Vector<Set>();
	private Vector<Model> models = new Vector<Model>();
	
    public Integer getId() { return id; }
    public String getName() { return name; }
    public URL getUrl() { return url; }
    public String getImageName() { return imageName; }
    public Image getImage() { return image; }
	public Vector<Set> getSets() { return new Vector<Set>(sets); }
	public Vector<Model> getModels() { return new Vector<Model>(models); } 
    
    public Site(Integer id, String name, URL url, String imageName, Image image)
    {
    	this.id = id;
    	this.name = name;
    	this.url = url;
    	this.imageName = imageName;
    	this.image = image;
    }
    
	public void addSet(Set set) {
		sets.add(set);
	}
	
	public void addModel(Model model) {
		models.add(model);
	}
}

