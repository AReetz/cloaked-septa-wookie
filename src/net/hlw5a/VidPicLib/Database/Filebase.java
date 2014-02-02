package net.hlw5a.VidPicLib.Database;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Observable;

import net.hlw5a.VidPicLib.Set;

public class Filebase extends Observable {

	private static Filebase instance;

	public static Filebase getInstance() {
		if (instance == null) {
			instance = new Filebase();
		}
		return instance;
	}
	
	private Filebase() {		
		rescanFiles();
	}
	
	public void rescanFiles() {
		for (final Set set : Database.getInstance().getSets()) {
			if (!set.getFileExists()) {
				Boolean fileExisted = false;
				findFile(set);
				if (fileExisted != set.getFileExists()) {
					setChanged();
					notifyObservers(new ObservableObject(ObservableObject.Action.FILE_FOUND, set));
				}
			}
		}
	}
	
	public void findFile(final Set set) {
		File rootDirectory = new File(Database.getInstance().getSetting("contentfolder"));
		
		FilenameFilter siteFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String siteName = set.getSite().getName();
				if (name.contains(siteName)) { return true; } else { return false; }
			}
		};
		
		FilenameFilter setFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.contains(set.getMainModel().getName()) && name.contains(set.getNumber())) { return true; } else { return false; }
			}
		};
		
		File[] subDirectories = rootDirectory.listFiles(siteFilter);
		if (subDirectories.length == 1) {
			File[] files = subDirectories[0].listFiles(setFilter);
			if (files.length == 1) {
				try {
					set.setFileName(files[0].getCanonicalPath());
				} catch (IOException e) { }
			}
		}
	}
}
