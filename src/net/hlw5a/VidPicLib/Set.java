package net.hlw5a.VidPicLib;

import java.awt.Image;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

public class Set {

	private Integer id;
	private String name;
	private String number;
	private Date date;
	private String imageName;
	private Image image;
	private Model mainModel;
	private Vector<Model> models;
	private Site site;
	
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getNumber() { return number; }
    public Date getDate() { return date; }
    public String getImageName() { return imageName; }
    public Image getImage() { return image; }
    public Model getMainModel() { return mainModel; }
    public Vector<Model> getModels() { return models; }
    public Site getSite() { return site; }

    public Set(Integer id, String name, String number, Date date, String imageName, Image image, Model mainModel, Vector<Model> models, Site site)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.date = date;
        this.imageName = imageName;
        this.image = image;
        this.mainModel = mainModel;
        this.models = models;
        this.site = site;
        
        if (!models.contains(mainModel)) models.add(0, mainModel);
        if (number == "") {
        	Pattern setNumberPattern = Pattern.compile("[\\D]+");
			String setNumber = setNumberPattern.matcher(name).replaceAll("");
			this.number = setNumber;
        }
    }
}
