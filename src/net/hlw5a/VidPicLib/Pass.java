package net.hlw5a.VidPicLib;

import java.util.Date;

public class Pass {
	
	private Integer id;
	private String username;
	private String password;
	private Date date;
	private State state;
	private Site site;
	
    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Date getDate() { return date; }
    public State getState() { return state; }
    public Site getSite() { return site; }

    public void setDate(Date Date) { date = Date; }
    public void setState(State State) { state = State; }
    
    public Pass(Integer id, String username, String password, Date date, State state, Site site)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.date = date;
        this.state = state;
        this.site = site;
    }
    
    public enum State {
    	unknown,
    	valid,
    	disabled,
    	expired
    }
}
