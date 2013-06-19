package net.hlw5a.VidPicLib.Database;

import java.util.Comparator;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Pass;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;

public class DataSort {
	
	public static class Sets {
		public static class ByModelName implements Comparator<Set> {
			public int compare(Set set0, Set set1) {
				int retVal = set0.getMainModel().getName().compareTo(set1.getMainModel().getName());
				return retVal;
			}
		}
		
		public static class BySiteNameAndDate implements Comparator<Set> {
			public int compare(Set set0, Set set1) {
				int retVal = set0.getSite().getName().compareTo(set1.getSite().getName());
				return (retVal != 0) ? retVal : set0.getDate().compareTo(set1.getDate());
			}
		}
		
		public static class ByModelNameAndDate implements Comparator<Set> {
			public int compare(Set set0, Set set1) {
				int retVal = set0.getMainModel().getName().compareTo(set1.getMainModel().getName());
				return (retVal != 0) ? retVal : set0.getDate().compareTo(set1.getDate());
			}
		}
	}
	
	public static class Passes {
		public static class ByDate implements Comparator<Pass> {
			public int compare(Pass pass0, Pass pass1) {
				return pass0.getDate().compareTo(pass1.getDate());
			}
		}
	}
	
	public static class Models {
		public static class ByName implements Comparator<Model> {
			public int compare(Model model0, Model model1) {
				int retVal = model0.getName().compareTo(model1.getName());
				return retVal;
			}
		}
	}
	
	public static class Sites {
		public static class ByName implements Comparator<Site> {
			public int compare(Site site0, Site site1) {
				int retVal = site0.getName().compareTo(site1.getName());
				return retVal;
			}
		}
	}
}
