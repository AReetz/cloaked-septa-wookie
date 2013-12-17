package net.hlw5a.VidPicLib.Ui;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.Database;

public class ModelPanel extends JPanel {
	
	public enum Mode {
		SHORT,
		DETAIL
	};
	
	private static final long serialVersionUID = -4790554520103975164L;
	private Model model;
	private JPanel siteSummary;
	
	public int getId() {
		return model.getId();
	}
	
	public ModelPanel(Model model) {
		this.model = model;
		
		JLabel id = new JLabel(String.valueOf(this.model.getId()), JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel modelName = new JLabel(this.model.getName(), JLabel.CENTER);
		modelName.setFont(VPLStyles.BOLD);
		modelName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH - VPLStyles.TEXTFIELD_HEIGHT - 5, VPLStyles.TEXTFIELD_HEIGHT));
		modelName.setOpaque(true);
		modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel picture = new JLabel(new ImageIcon(this.model.getImage()));
		picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 256));
			
		siteSummary = new SiteSummary();
		
		this.add(id);
		this.add(modelName);
		this.add(picture);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
	}
	
	public void setMode(Mode mode) {
		switch (mode) {
		case SHORT:
			this.remove(siteSummary);
			this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
			break;
		case DETAIL:
			this.add(siteSummary);
			this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294 + siteSummary.getPreferredSize().height + VPLStyles.BORDER_SIZE));
			break;
		}
	}
	
	private class SiteSummary extends JPanel {

		private static final long serialVersionUID = -834119920324526254L;

		public SiteSummary() {
			Vector<Site> sites = Database.getInstance().getSites();
			
			for (final Site site : sites) {
				int setCount = 0;
				for (Set set : Database.getInstance().getSets()) {
					if (set.getModels().contains(model) && set.getSite() == site) setCount++;
				}
				
				JLabel siteName = new JLabel(site.getName(), JLabel.CENTER);
				siteName.setOpaque(true);
				siteName.setBackground(VPLStyles.TEXT_BACKGROUND);
				siteName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH - 2 * VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));
				
				JLabel numberSets = new JLabel(String.valueOf(setCount), JLabel.CENTER);
				numberSets.setOpaque(true);
				numberSets.setBackground(VPLStyles.TEXT_BACKGROUND);
				numberSets.setPreferredSize(new Dimension(VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));
				
				final Checkbox complete = new Checkbox(null, Database.getInstance().getModelSiteMapping(model, site));
				complete.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						Database.getInstance().setModelSiteMapping(model, site, complete.getState());
					}
				});
				complete.setBackground(VPLStyles.TEXT_BACKGROUND);
				complete.setPreferredSize(new Dimension(VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));
				
				this.add(siteName);
				this.add(numberSets);
				this.add(complete);
			}
			this.setOpaque(true);
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
			this.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, sites.size() * VPLStyles.LABEL_HEIGHT));
		}
	}
}
