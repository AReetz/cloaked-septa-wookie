package net.hlw5a.VidPicLib.Database;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.util.Vector;

import javax.imageio.ImageIO;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Pass;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.State;

public abstract class Database extends Observable {
	
	public enum Action {
		CREATE_MODEL,
		CREATE_SET,
		CREATE_PASS,
		DELETE_PASS
	};

	public class ObservableObject {
		private Action action;
		private Object object;
		public Action getAction() { return action; }
		public Object getObject() { return object; }
		public ObservableObject(Action action, Object object) {
			this.action = action;
			this.object = object;
		}
	}
	
	private static Database instance;

	public static Database getInstance() {
		if (instance == null) {
			instance = new XmlDatabase();
		}
		return instance;
	}
	
    protected Map<Integer, Model> models = new TreeMap<Integer, Model>();
    protected Map<Integer, Site> sites = new TreeMap<Integer, Site>();
    protected Map<Integer, Set> sets = new TreeMap<Integer, Set>();
    protected Map<Integer, Pass> passes = new TreeMap<Integer, Pass>();
    protected Map<Integer, State> states = new TreeMap<Integer, State>();

    public Model getModel(Integer Id) { return models.get(Id); }
    public Vector<Model> getModels() { return new Vector<Model>(models.values()); }
    
    public Site getSite(Integer Id) { return sites.get(Id); }
    public Vector<Site> getSites() { return new Vector<Site>(sites.values()); }
    
    public Set getSet(Integer Id) { return sets.get(Id); }
    public Vector<Set> getSets() { return new Vector<Set>(sets.values()); }
    
    public Pass getPass(Integer Id) { return passes.get(Id); }
    public Vector<Pass> getPasses() { return new Vector<Pass>(passes.values()); }
    
    public State getState(Integer Id) { return states.get(Id); }
    public Vector<State> getStates() { return new Vector<State>(states.values()); }

    public abstract Image getImage(String imageName) throws IOException;
    public abstract void openDatabase();
    public abstract void saveDatabase();

    public void createPass(String username, String password, Site site)
    {
    	Integer id = Collections.max(passes.keySet()) + 1;
        Pass pass = new Pass(id, username, password, Calendar.getInstance().getTime(), State.unknown, site);
        passes.put(id, pass);
        setChanged();
        notifyObservers(new ObservableObject(Action.CREATE_PASS, pass));
    }
    
    public void createModel(String modelName, String imageName) throws IOException {
    	Integer id = Collections.max(models.keySet()) + 1;
    	Model model = new Model(id, modelName, imageName, ImageIO.read(new File(imageName)));
    	models.put(id, model);
    	setChanged();
    	notifyObservers(new ObservableObject(Action.CREATE_MODEL, model));
    }
    
    public void createSet(String setName, Date date, String imageName, Model mainModel, Vector<Model> models, Site site) throws IOException {
    	Integer id = Collections.max(sets.keySet()) + 1;
    	Set set = new Set(id, setName, date, imageName, getImage(imageName), mainModel, models, site);
    	sets.put(id, set);
    	setChanged();
    	notifyObservers(new ObservableObject(Action.CREATE_SET, set));
    }

    public void deletePass(Pass pass) {
        passes.remove(pass.getId());
        setChanged();
    	notifyObservers(new ObservableObject(Action.DELETE_PASS, pass));
    }
}
