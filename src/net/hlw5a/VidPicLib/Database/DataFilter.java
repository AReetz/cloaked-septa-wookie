package net.hlw5a.VidPicLib.Database;

import java.util.Collection;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Model.Race;

public class DataFilter {
	
	public interface FilterCriteria {
		public boolean passes(Object o, Object c);
	}
	
	public static class Models {
		
		public void filter(FilterCriteria filter, Collection<Model> models, Object criteria) {
			for (Model model : models) {
				if (!filter.passes(model, criteria)) {
					models.remove(model);
				}
			}
		}
		
		public class MainModelFilter implements FilterCriteria {
		    public boolean passes(Object o, Object c) {
		    	Model model = (Model)o;
		    	for (Set set: model.getSets()) {
		    		if (set.getMainModel() == model) {
		    			return true;
		    		}
		    	}
		        return false;
		    }
		}
		
		public class RaceModelFilter implements FilterCriteria {
		    public boolean passes(Object o, Object c) {
		    	Model model = (Model)o;
		    	Race critera = (Race)c;
		    	if (model.getRace() == critera) {
	    			return true;
		    	}
		    	return false;
		    }
		}
	}
}
