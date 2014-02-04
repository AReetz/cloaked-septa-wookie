package net.hlw5a.VidPicLib;

import java.awt.Image;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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
	private Vector<Set> sets = new Vector<Set>();
	private Vector<Site> sites = new Vector<Site>();
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getImageName() { return imageName; }
	public Image getImage() { return image; }
	public Race getRace() { return race; }
	public Built getBuilt() { return built; }
	public Integer getAge() { Calendar bd = Calendar.getInstance(); bd.setTime(birthdate); return Calendar.getInstance().get(Calendar.YEAR) -  bd.get(Calendar.YEAR); }
	public Date getBirthdate() { return birthdate; }
	public String getMeasurements() { return measurements; }
	public Cup getCup() { return cup; }
	public Vector<Set> getSets() { return new Vector<Set>(sets); }
	public Vector<Site> getSites() { return new Vector<Site>(sites); }
	
	public void setRace(Race race) { this.race = race; }
	public void setBuilt(Built built) { this.built = built; }
	public void setCup(Cup cup) { this.cup = cup; }
	
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
	
	public void addSet(Set set) {
		sets.add(set);
	}
	
	public void addSite(Site site) {
		sites.add(site);
	}

	public enum Built {
		unknown,
		Petite,
		Normal,
		Curvy,
		Chubby
	}
	
	public enum Cup {
		unknown,
		AA,
		A,
		B,
		C,
		D,
		DD,
		F,
		G_up,
	}
	
	public enum Race {
		unknown,
		Asian,
		Black,
		Latin,
		White
	}
}
