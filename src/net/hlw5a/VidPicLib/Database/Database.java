package net.hlw5a.VidPicLib.Database;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.util.Vector;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Pass;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.State;

public class Database extends Observable {
	
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
	private static IDataSource dataSource;

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
			dataSource = new XmlDataSource();
			dataSource.Load();
		}
		return instance;
	}
	
    private Map<Integer, Model> models = new TreeMap<Integer, Model>();
    private Map<Integer, Site> sites = new TreeMap<Integer, Site>();
    private Map<Integer, Set> sets = new TreeMap<Integer, Set>();
    private Map<Integer, Pass> passes = new TreeMap<Integer, Pass>();
    private Map<Integer, State> states = new TreeMap<Integer, State>();
    
    public Model getModel(Integer Id) { return models.get(Id); }
    public Vector<Model> getModels() { return new Vector<Model>(models.values()); }
    public void addModel(Integer Id, Model Model) { models.put(Id, Model); }
    
    public Site getSite(Integer Id) { return sites.get(Id); }
    public Vector<Site> getSites() { return new Vector<Site>(sites.values()); }
    public void addSite(Integer Id, Site Site) { sites.put(Id, Site); }
    
    public Set getSet(Integer Id) { return sets.get(Id); }
    public Vector<Set> getSets() { return new Vector<Set>(sets.values()); }
    public void addSet(Integer Id, Set Set) { sets.put(Id, Set); }
    
    public Pass getPass(Integer Id) { return passes.get(Id); }
    public Vector<Pass> getPasses() { return new Vector<Pass>(passes.values()); }
    public void addPass(Integer Id, Pass Pass) { passes.put(Id, Pass); }
    
    public State getState(Integer Id) { return states.get(Id); }
    public Vector<State> getStates() { return new Vector<State>(states.values()); }
    public void addState(Integer Id, State State) { states.put(Id, State); }
    
    public void CreatePass(String username, String password, Site site)
    {
        Integer maxId = 1;
        for (Integer id : passes.keySet()) maxId = Math.max(maxId, id);
        Pass pass = new Pass(maxId + 1, username, password, Calendar.getInstance().getTime(), State.unknown, site);
        passes.put(pass.getId(), pass);
        setChanged();
        notifyObservers(new ObservableObject(Action.CREATE_PASS, pass));
    }
    
    public void CreateModel(String modelName, String imageName) throws IOException {
    	Integer maxId = 1;
    	for (Integer id : models.keySet()) maxId = Math.max(maxId, id);
    	Model model = new Model(maxId + 1, modelName, imageName, dataSource.getImage(imageName));
    	models.put(model.getId(), model);
    	setChanged();
    	notifyObservers(new ObservableObject(Action.CREATE_MODEL, model));
    }
    
    public void CreateSet(String setName, Date date, String imageName, Model mainModel, Vector<Model> models, Site site) throws IOException {
    	Integer maxId = 1;
    	for (Integer id : sets.keySet()) maxId = Math.max(maxId, id);
    	Set set = new Set(maxId + 1, setName, date, imageName, dataSource.getImage(imageName), mainModel, models, site);
    	sets.put(set.getId(), set);
    	setChanged();
    	notifyObservers(new ObservableObject(Action.CREATE_SET, set));
    }

    public void DeletePass(Pass pass) {
        passes.remove(pass.getId());
        setChanged();
    	notifyObservers(new ObservableObject(Action.DELETE_PASS, pass));
    }
    
    public void Save() {
    	dataSource.Save();
    }
    
	public void Open() {
		dataSource.Open();
	}
}
