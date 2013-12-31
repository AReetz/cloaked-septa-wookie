package net.hlw5a.VidPicLib;

import java.awt.Image;
import java.util.Date;

public class Model {

	private Integer id;
	private String name;
	private String imageName;
	private Image image;
	private Race race;
	private Built built;
	private Date birthdate;
	private String measurements;
	private Cup cup;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getImageName() { return imageName; }
	public Image getImage() { return image; }
	public Race getRace() { return race; }
	public Built getBuilt() { return built; }
	public Integer getAge() { return -1; }
	public String getMeasurements() { return measurements; }
	public Cup getCup() { return cup; }
	
	public Model(Integer id, String name, String imageName, Image image, Race race, Built built, Date birthdate, String measurements, Cup cup)
	{
		this.id = id;
		this.name = name;
		this.imageName = imageName;
		this.image = image;
		this.race = race;
		this.built = built;
		this.birthdate = birthdate;
		this.measurements = measurements;
		this.cup = cup;
	}
	
	public void setRace(Race race) { this.race = race; }
	public void setBuilt(Built built) { this.built = built; }
	public void setCup(Cup cup) { this.cup = cup; }
	
	public enum Cup {
		unknown,
		AA,
		A,
		B,
		C,
		D,
		DD,
		F,
		G_up
	}
	
	public enum Race {
		unknown,
		Asian,
		Black,
		Latin,
		White
	}
	
	public enum Built {
		unknown,
		Petite,
		Normal,
		Curvy,
		Chubby
	}
}
