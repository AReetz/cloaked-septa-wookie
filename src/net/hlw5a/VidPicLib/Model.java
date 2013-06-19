package net.hlw5a.VidPicLib;

import java.awt.Image;

public class Model {

	private Integer id;
	private String name;
	private String imageName;
	private Image image;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getImageName() { return imageName; }
	public Image getImage() { return image; }
	
	public Model(Integer id, String name, String imageName, Image image)
	{
		this.id = id;
		this.name = name;
		this.imageName = imageName;
		this.image = image;
	}
}
