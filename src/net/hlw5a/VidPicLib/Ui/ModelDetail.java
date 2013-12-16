package net.hlw5a.VidPicLib.Ui;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.Database;

public class ModelDetail extends JPanel {
	
	private static final long serialVersionUID = -4790554520103975164L;
	private Model model;
	private JLabel id;
	private JLabel modelName;
	private JLabel picture;
	private JPanel siteSummary;
	
	public int getId() {
		return model.getId();
	}
	
	public ModelDetail(Model model) {
		this.model = model;
		
		id = new JLabel(String.valueOf(this.model.getId()), JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		modelName = new JLabel(this.model.getName(), JLabel.CENTER);
		modelName.setFont(VPLStyles.BOLD);
		modelName.setPreferredSize(new Dimension(165, VPLStyles.TEXTFIELD_HEIGHT));
		modelName.setOpaque(true);
		modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		picture = new JLabel(new ImageIcon(this.model.getImage()));
		picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 256));
			
		siteSummary = new SiteSummary();
		
		this.add(id);
		this.add(modelName);
		this.add(picture);
		this.add(siteSummary);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
	}
	
	public void setMode(int mode) {
		if (mode == 1) {
			this.remove(siteSummary);
		} else if (mode == 2) {
			this.add(siteSummary);
		}
		
	}
	
	private class SiteSummary extends JPanel {

		private static final long serialVersionUID = -834119920324526254L;

		public SiteSummary() {
			for (final Site site : Database.getInstance().getSites()) {
				int setCount = 0;
				for (Set set : Database.getInstance().getSets()) {
					if (set.getMainModel() == model) setCount++;
				}
				
				JLabel siteName = new JLabel(site.getName(), JLabel.CENTER);
				JLabel numberSets = new JLabel(String.valueOf(setCount), JLabel.CENTER);
				final Checkbox complete = new Checkbox(null, Database.getInstance().getModelSiteMapping(model, site));
				complete.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						Database.getInstance().setModelSiteMapping(model, site, complete.getState());
					}
				});
				
				this.add(siteName);
				this.add(numberSets);
				this.add(complete);
			}
		}
	}
}
