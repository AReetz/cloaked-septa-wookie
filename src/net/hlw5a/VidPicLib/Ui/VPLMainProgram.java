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
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
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
import net.hlw5a.VidPicLib.Database.Filebase;
import net.hlw5a.VidPicLib.Database.ObservableObject;
import net.hlw5a.VidPicLib.Database.ObservableObject.Action;

public class VPLMainProgram extends JPanel implements Runnable, Observer {

	private static final long serialVersionUID = 4232342981332473693L;

	private JTabbedPane tabsPane;
	private VPLTabPanel tabSitesModels;
	private VPLTabPanel tabSitesSets;
	private VPLTabPanel tabSitesPasses;
	private VPLTabPanel tabModelsSets;
	private Map<Integer, ModelPanel> modelPanels = new TreeMap<Integer, ModelPanel>();
	private Map<Integer, ModelInfoPanel> modelInfoPanels = new TreeMap<Integer, ModelInfoPanel>();
	private Map<Integer, SitePanel> sitePanels = new TreeMap<Integer, SitePanel>();
	private Map<Integer, SetPanel> setPanels = new TreeMap<Integer, SetPanel>();
	private Map<Integer, PassPanel> passPanels = new TreeMap<Integer, PassPanel>();
	private Site activeSite;
	private Model activeModel;
	private VPLTabPanel activeTab;
	private ModelView modelView = ModelView.MainSite;
	
	private void mouseClicked(Component comp) {
		tabSitesModels.removeAll(VPLTabPanel.Panel.Right);
		tabSitesSets.removeAll(VPLTabPanel.Panel.Right);
		tabSitesPasses.removeAll(VPLTabPanel.Panel.Right);
		tabModelsSets.removeAll(VPLTabPanel.Panel.Right);
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
		else if (activeTab == tabModelsSets) {
			activeTab.add(modelInfoPanels.get(Model.getId()), VPLTabPanel.Panel.Right);
			List<Set> tmpList = Database.getInstance().getSets();
			Collections.sort(tmpList, new DataSort.Sets.BySiteNameAndDate());
	        for (Set set: tmpList) {
	        	for (Model model : set.getModels()) {
		        	if (Model == model) {
		        		activeTab.add(setPanels.get(set.getId()), VPLTabPanel.Panel.Right);
		        	}
	        	}
	        }
	        activeTab.add(new SetNew_Model(Model), VPLTabPanel.Panel.Right);
	        tabsPane.repaint();
		}
	}
	
	public void mouseClickedSite(Component comp, Site Site) {
		mouseClicked(comp);
		activeSite = Site;
		if (activeTab == tabSitesModels) {
			List<Model> tmpModelList = new Vector<Model>();
			if (modelView == ModelView.MainSite) {
				List<Set> tmpSetList = Database.getInstance().getSets();
				for (Set set: tmpSetList) {
		        	if (Site == set.getSite()) {		        		
	        			if (!tmpModelList.contains(set.getMainModel())) {
	        				tmpModelList.add(set.getMainModel());
	        			}
		           	}
		        }
			}
			if (modelView == ModelView.MainAll) {
				List<Set> tmpSetList = Database.getInstance().getSets();
				for (Set set: tmpSetList) {
        			if (!tmpModelList.contains(set.getMainModel())) {
        				tmpModelList.add(set.getMainModel());
        			}
		        }
			}
			else if (modelView == ModelView.Appearing) {
				List<Set> tmpSetList = Database.getInstance().getSets();
				for (Set set: tmpSetList) {
		        	if (Site == set.getSite()) {
		        		for (Model model : set.getModels()) {
		        			if (!tmpModelList.contains(model)) {
		        				tmpModelList.add(model);
		        			}
		        		}
		           	}
		        }
			}
			Collections.sort(tmpModelList, new DataSort.Models.ByName());
			for (Model model: tmpModelList) {
				ModelPanel mp = modelPanels.get(model.getId());
				mp.setSite(Site);
        		activeTab.add(mp, VPLTabPanel.Panel.Right);
	        }
			activeTab.add(new ModelNew(), VPLTabPanel.Panel.Right);
			tabsPane.repaint();
		}
		else if (activeTab == tabSitesSets) {
			List<Set> tmpList = Database.getInstance().getSets();
			Collections.sort(tmpList, new DataSort.Sets.ByModelNameAndDate());
	        for (Set set: tmpList) {
	        	if (Site == set.getSite()) {
	        		activeTab.add(setPanels.get(set.getId()), VPLTabPanel.Panel.Right);
	        	}
	        }
	        activeTab.add(new SetNew_Site(Site), VPLTabPanel.Panel.Right);
	        tabsPane.repaint();
		}
		else if (activeTab == tabSitesPasses) {
			List<Pass> tmpList = Database.getInstance().getPasses();
			Collections.sort(tmpList, new DataSort.Passes.ByDate());
	        for (Pass pass: tmpList) {
	        	if (Site == pass.getSite()) {
	        		activeTab.add(passPanels.get(pass.getId()), VPLTabPanel.Panel.Right);
	        	}
	        }
	        tabSitesPasses.add(new PassNew(Site), VPLTabPanel.Panel.Right);
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
        	modelInfoPanels.put(model.getId(), new ModelInfoPanel(model));
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
        	final SetPanel comp = new SetPanel(set);
        	comp.addMouseListener(new MouseAdapter() {
        		public void mouseClicked(MouseEvent e) { VPLMainProgram.this.mouseClickedSet(comp, set); }
        	});
        	setPanels.put(set.getId(), comp);
        }
	}
	
	public void run() {
		Database.getInstance().addObserver(this);
		Filebase.getInstance().addObserver(this);
		
        final JFrame mainFrame = new JFrame("Video Library");
        
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
			com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
			application.setQuitHandler(new QuitHandler() {
				public void handleQuitRequestWith(QuitEvent arg0, QuitResponse arg1) { mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING)); }
			});
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png");
			try {
				Image icon = ImageIO.read(input);
				application.setDockIconImage(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        JMenuBar menuBar = new JMenuBar();
        JMenu menuView = new JMenu("View");
        JMenu sites_model_Submenu = new JMenu("Sites x Models");
        ButtonGroup sites_model_group = new ButtonGroup();
        JRadioButtonMenuItem sites_model_main_Item = new JRadioButtonMenuItem("Main models (site)");
        sites_model_main_Item.setSelected(true);
        sites_model_main_Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelView = ModelView.MainSite;
				mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
			}
        });
        sites_model_Submenu.add(sites_model_main_Item);
        sites_model_group.add(sites_model_main_Item);
        JRadioButtonMenuItem sites_model_appear_Item = new JRadioButtonMenuItem("Main models (all)");
        sites_model_appear_Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelView = ModelView.MainAll;
				mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
			}
        });
        sites_model_Submenu.add(sites_model_appear_Item);
        sites_model_group.add(sites_model_appear_Item);
        JRadioButtonMenuItem sites_model_all_Item = new JRadioButtonMenuItem("Appearing models (site)");
        sites_model_all_Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modelView = ModelView.Appearing;
				mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
			}
        });
        sites_model_Submenu.add(sites_model_all_Item);
        sites_model_group.add(sites_model_all_Item);
        menuView.add(sites_model_Submenu);
        menuBar.add(menuView);
        JMenu menuOptions = new JMenu("Options");
        JMenuItem rescanItem = new JMenuItem("Rescan for files", KeyEvent.VK_R);
        rescanItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        rescanItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Filebase.getInstance().rescanFiles();
			}
        });
        menuOptions.add(rescanItem);
        JMenuItem databaseItem = new JMenuItem("Open database", KeyEvent.VK_D);
        databaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        databaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.getInstance().openDatabase();
			}
        });
        menuOptions.add(databaseItem);
        JMenuItem saveItem = new JMenuItem("Save database", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Database.getInstance().saveDatabase();
			}
        });
        menuOptions.add(saveItem);
        menuBar.add(menuOptions);
        mainFrame.setJMenuBar(menuBar);
        
        tabsPane = new JTabbedPane();
        tabsPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				activeTab = ((VPLTabPanel)tabsPane.getSelectedComponent());
				if (activeTab == tabSitesModels || activeTab == tabSitesSets || activeTab == tabSitesPasses) {
					List<Site> tmpList = Database.getInstance().getSites();
					Collections.sort(tmpList, new DataSort.Sites.ByName());
					for (Site site: tmpList) {
						activeTab.add(sitePanels.get(site.getId()), VPLTabPanel.Panel.Left);
					}
					if (activeSite != null) {
						mouseClickedSite(sitePanels.get(activeSite.getId()), activeSite);
					}
				}
				else if (tabsPane.getSelectedComponent() == tabModelsSets) {
					List<Model> tmpList = Database.getInstance().getModels();
					Collections.sort(tmpList, new DataSort.Models.ByName());
					for (Model model: tmpList) {
						ModelPanel mp = modelPanels.get(model.getId());
						mp.setSite(null);
						activeTab.add(mp, VPLTabPanel.Panel.Left);
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
        mainFrame.setTitle("Video Library");
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainFrame.addWindowListener(new WindowAdapter() {
        	public void windowClosed(WindowEvent e) { Database.getInstance().saveDatabase(); }
        });
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
		}
		else if (action == Action.CREATE_SET) {
			final Set set = (Set)object;
			Filebase.getInstance().findFile(set);
        	final SetPanel comp = new SetPanel(set);
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
		else if (action == Action.FILE_FOUND) {
			final Set set = (Set)object;
			setPanels.get(set.getId()).setFileExist();
		}
	}
	
	private enum ModelView {
		MainSite,
		MainAll,
		Appearing
	}
}
