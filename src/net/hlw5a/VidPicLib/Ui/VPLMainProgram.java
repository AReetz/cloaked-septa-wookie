package net.hlw5a.VidPicLib.Ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.apple.eawt.AppEvent.QuitEvent;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Pass;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.Database;
import net.hlw5a.VidPicLib.Database.DataSort;
import net.hlw5a.VidPicLib.Database.Database.Action;
import net.hlw5a.VidPicLib.Database.Database.ObservableObject;

public class VPLMainProgram extends JPanel implements Runnable, Observer {

	private static final long serialVersionUID = 4232342981332473693L;

	private JTabbedPane tabsPane;
	private VPLTabPanel tabSitesModels;
	private VPLTabPanel tabSitesSets;
	private VPLTabPanel tabSitesPasses;
	private VPLTabPanel tabModelsSets;
	private Map<Integer, ModelPanel> modelPanels = new TreeMap<Integer, ModelPanel>();
	private Map<Integer, ModelDetail> modelDetails = new TreeMap<Integer, ModelDetail>();
	private Map<Integer, SitePanel> sitePanels = new TreeMap<Integer, SitePanel>();
	private Map<Integer, SetPanel> setPanels = new TreeMap<Integer, SetPanel>();
	private Map<Integer, PassPanel> passPanels = new TreeMap<Integer, PassPanel>();
	private Site activeSite;
	private Model activeModel;
	private VPLTabPanel activeTab;

	private void mouseClicked(Component comp) {
		tabSitesModels.removeAll(VPLTabPanel.RIGHT);
		tabSitesSets.removeAll(VPLTabPanel.RIGHT);
		tabSitesPasses.removeAll(VPLTabPanel.RIGHT);
		tabModelsSets.removeAll(VPLTabPanel.RIGHT);
		for (ModelPanel model : modelPanels.values()) {
			model.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		}
		for (SitePanel site : sitePanels.values()) {
			site.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		}
		for (SetPanel set : setPanels.values()) {
			set.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		}
		for (PassPanel pass : passPanels.values()) {
			pass.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		}
		comp.setBackground(VPLStyles.CONTAINER_HIGHLIGHT);
	}
	
	public void mouseClickedModel(Component comp, Model Model) {
		mouseClicked(comp);
		activeModel = Model;
		if (activeTab == tabSitesModels) {
			tabsPane.setSelectedComponent(tabModelsSets);
			mouseClickedModel(modelPanels.get(Model.getId()), Model);
		}
		else if (activeTab == tabSitesSets) {
		}
		else if (activeTab == tabSitesPasses) {
		}
		else if (activeTab == tabModelsSets) {;
			List<Set> tmpList = Database.getInstance().getSets();
			Collections.sort(tmpList, new DataSort.Sets.BySiteNameAndDate());
	        for (Set set: tmpList) {
	        	for (Model model : set.getModels()) {
		        	if (Model == model) {
		        		activeTab.add(setPanels.get(set.getId()), VPLTabPanel.RIGHT);
		        	}
	        	}
	        }
	        activeTab.add(new SetNew_Model(Model), VPLTabPanel.RIGHT);
	        tabsPane.repaint();
		}
	}
	
	public void mouseClickedSite(Component comp, Site Site) {
		mouseClicked(comp);
		activeSite = Site;
		if (activeTab == tabSitesModels) {
			List<Set> tmpList = Database.getInstance().getSets();
			List<Model> tmpList2 = new Vector<Model>();
			for (Set set: tmpList) {
	        	if (Site == set.getSite()) {
	        		for (Model model : set.getModels()) {
	        			if (!tmpList2.contains(model)) {
	        				tmpList2.add(model);
	        			}
	        		}
	           	}
	        }
			Collections.sort(tmpList2, new DataSort.Models.ByName());
			for (Model model: tmpList2) {
        		activeTab.add(modelDetails.get(model.getId()), VPLTabPanel.RIGHT);
	        }
			activeTab.add(new ModelNew(), VPLTabPanel.RIGHT);
			tabsPane.repaint();
		}
		else if (activeTab == tabSitesSets) {
			List<Set> tmpList = Database.getInstance().getSets();
			Collections.sort(tmpList, new DataSort.Sets.ByModelNameAndDate());
	        for (Set set: tmpList) {
	        	if (Site == set.getSite()) {
	        		activeTab.add(setPanels.get(set.getId()), VPLTabPanel.RIGHT);
	        	}
	        }
	        activeTab.add(new SetNew_Site(Site), VPLTabPanel.RIGHT);
	        tabsPane.repaint();
		}
		else if (activeTab == tabSitesPasses) {
			List<Pass> tmpList = Database.getInstance().getPasses();
			Collections.sort(tmpList, new DataSort.Passes.ByDate());
	        for (Pass pass: tmpList) {
	        	if (Site == pass.getSite()) {
	        		activeTab.add(passPanels.get(pass.getId()), VPLTabPanel.RIGHT);
	        	}
	        }
	        tabSitesPasses.add(new PassNew(Site), VPLTabPanel.RIGHT);
	        tabsPane.repaint();
		}
		else if (activeTab == tabModelsSets) {
		}
	}
	
	public void mouseClickedSet(Component comp, Set Set) {
		mouseClicked(comp);
		if (activeTab == tabSitesModels) {
		}
		else if (activeTab == tabSitesSets) {
			tabsPane.setSelectedComponent(tabModelsSets);
			mouseClickedModel(modelPanels.get(Set.getMainModel().getId()), Set.getMainModel());	
		}
		else if (activeTab == tabSitesPasses) {
		}
		else if (activeTab == tabModelsSets) {
			tabsPane.setSelectedComponent(tabSitesSets);
			mouseClickedSite(sitePanels.get(Set.getSite().getId()), Set.getSite());	
		}
	}
	
	public void mouseClickedPass(Component comp, Pass Pass) {
		//mouseClicked(comp);
		if (activeTab == tabSitesModels) {
		}
		else if (activeTab == tabSitesSets) {	
		}
		else if (activeTab == tabSitesPasses) {
		}
		else if (activeTab == tabModelsSets) {
		}
	}

	public void mouseClickedPassRem(Pass Pass) {
        Database.getInstance().deletePass(Pass);
        passPanels.remove(Pass.getId());
        mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
	}
	
	private void populateTabs() {
		for (final Model model : Database.getInstance().getModels()) {
        	final ModelPanel comp = new ModelPanel(model);
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedModel(comp, model); }
        	});
        	modelPanels.put(model.getId(), comp);
        	
        	
        	ModelDetail comp2 = new ModelDetail(model);
        	modelDetails.put(model.getId(), comp2);
        }
        
        for (final Site site: Database.getInstance().getSites()) {
        	final SitePanel comp = new SitePanel(site);
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedSite(comp, site); }
        	});
        	sitePanels.put(site.getId(), comp);
        }
        
        for (final Pass pass : Database.getInstance().getPasses()) {
        	final PassPanel comp = new PassPanel(pass);
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedPass(comp, pass); }
        	});
        	passPanels.put(pass.getId(), comp);
        }
        
        for (final Set set : Database.getInstance().getSets()) {
        	final SetPanel comp = new SetPanel(set, DoesVideoExist(set));
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedSet(comp, set); }
        	});
        	setPanels.put(set.getId(), comp);
        }
	}
	
	public void run() {
		Database.getInstance().addObserver(this);
		
        final JFrame mainFrame = new JFrame("Video Library");
        
		/*com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
		application.setQuitHandler(new QuitHandler() {
			public void handleQuitRequestWith(QuitEvent arg0, QuitResponse arg1) { mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING)); }
		});
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("icon.png");
		try {
			Image icon = ImageIO.read(input);
			application.setDockIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem databaseItem = new JMenuItem("Open database", KeyEvent.VK_D);
        databaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        databaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.getInstance().openDatabase();
			}
        });
        menu.add(databaseItem);
        JMenuItem saveItem = new JMenuItem("Save database", KeyEvent.VK_S);
        databaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        databaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.getInstance().saveDatabase();
			}
        });
        menu.add(saveItem);
        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);
        
        tabsPane = new JTabbedPane();
        tabsPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				activeTab = ((VPLTabPanel)tabsPane.getSelectedComponent());
				if (activeTab == tabSitesModels || activeTab == tabSitesSets || activeTab == tabSitesPasses) {
					List<Site> tmpList = Database.getInstance().getSites();
					Collections.sort(tmpList, new DataSort.Sites.ByName());
					for (Site site: tmpList) {
						activeTab.add(sitePanels.get(site.getId()), VPLTabPanel.LEFT);
					}
					if (activeSite != null) {
						mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
					}
				}
				else if (tabsPane.getSelectedComponent() == tabModelsSets) {
					List<Model> tmpList = Database.getInstance().getModels();
					Collections.sort(tmpList, new DataSort.Models.ByName());
					for (Model model: tmpList) {
						activeTab.add(modelPanels.get(model.getId()), VPLTabPanel.LEFT);
					}
					if (activeModel != null) {
						mouseClickedModel(modelPanels.get(activeModel.getId()), activeModel);
					}
				}
			}
        });
        
        populateTabs();
        
        tabsPane.addTab("Sites x Models", tabSitesModels = new VPLTabPanel());
        tabsPane.addTab("Sites x Sets", tabSitesSets = new VPLTabPanel());
        tabsPane.addTab("Sites x Passes", tabSitesPasses = new VPLTabPanel());
        tabsPane.addTab("Models x Sets", tabModelsSets = new VPLTabPanel());
        
        mainFrame.add(tabsPane);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setTitle("Video & Picture Library");
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainFrame.addWindowListener(new WindowAdapter() {
        	public void windowClosed(WindowEvent e) { Database.getInstance().saveDatabase(); }
        });
	}
	
	private Boolean DoesVideoExist(final Set set) {
		File rootDirectory = new File(Database.getInstance().getSetting("contentfolder"));
		
		FilenameFilter siteFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String siteName = set.getSite().getName();
				if (name.contains(siteName)) { return true; } else { return false; }
			}
		};
		
		FilenameFilter setFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Pattern setNumberPattern = Pattern.compile("[\\D]+");
				String modelName = set.getMainModel().getName();
				String setNumber = setNumberPattern.matcher(set.getName()).replaceAll("");
				if (name.contains(modelName) && name.contains(setNumber)) {
					return true;
					} else {
						return false; 
						}
			}
		};
		
		File[] subDirectories = rootDirectory.listFiles(siteFilter);
		if (subDirectories.length > 0) {
			File[] files = subDirectories[0].listFiles(setFilter);
			if (files.length > 0) {
				return true;
			}
		}
		return false;
	}

	public void update(Observable arg0, Object observableObject) {
		Action action = ((ObservableObject)observableObject).getAction();
		Object object = ((ObservableObject)observableObject).getObject();
		if (action == Action.CREATE_MODEL) {
			final Model model = (Model)object;
			final ModelPanel comp = new ModelPanel(model);
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedModel(comp, model); }
        	});
        	modelPanels.put(model.getId(), comp);
			mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
			
			final ModelDetail comp2 = new ModelDetail(model);
			modelDetails.put(model.getId(), comp2);
		}
		else if (action == Action.CREATE_SET) {
			final Set set = (Set)object;
        	final SetPanel comp = new SetPanel(set, DoesVideoExist(set));
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedSet(comp, set); }
        	});
			setPanels.put(set.getId(), comp);
			if (activeTab == tabSitesSets) { mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite); }
			if (activeTab == tabModelsSets) { mouseClickedModel(modelPanels.get(activeModel.getId()), activeModel); }
		}
		else if (action == Action.CREATE_PASS) {
			final Pass pass = (Pass)object;
			final PassPanel comp = new PassPanel(pass);
			comp.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedPass(comp, pass); }
			});
			passPanels.put(pass.getId(), comp);
			mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
		}
		else if (action == Action.DELETE_PASS) {
			final Pass pass = (Pass)object;
			passPanels.remove(pass.getId());
			mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
		}
	}
}
