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

public class ModelPanel extends JPanel {

	private static final long serialVersionUID = -4790554520103975164L;
	private Model model;
	
	private JLabel siteName, numberSets;
	private Checkbox complete;
	private ItemListener completeListener;
	
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

		this.add(id);
		this.add(modelName);
		this.add(picture);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
		
		siteName = new JLabel("", JLabel.CENTER);
		numberSets = new JLabel("", JLabel.CENTER);
		complete = new Checkbox(null, false);
	}
	
	public void setSite(final Site site) {
		if (site != null) {
			int setCount = 0;
			int setExists = 0;
			for (Set set : Database.getInstance().getSets()) {
				if (set.getModels().contains(model) && set.getSite() == site) {
					setCount++;
					if (set.getFileExists()) setExists++;
				}
			}
			
			siteName.setText(site.getName());
			siteName.setOpaque(true);
			siteName.setBackground(VPLStyles.TEXT_BACKGROUND);
			siteName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH - 4 * VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));
			
			numberSets.setText(String.valueOf(setExists) + "/" + String.valueOf(setCount));
			numberSets.setOpaque(true);
			numberSets.setBackground(VPLStyles.TEXT_BACKGROUND);
			numberSets.setPreferredSize(new Dimension(3 * VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));
			
			complete.setState(Database.getInstance().getModelSiteMapping(model, site));
			complete.removeItemListener(completeListener);
			completeListener = new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					Database.getInstance().setModelSiteMapping(model, site, complete.getState());
				}
			};
			complete.addItemListener(completeListener);
			complete.setBackground(VPLStyles.TEXT_BACKGROUND);
			complete.setPreferredSize(new Dimension(VPLStyles.LABEL_HEIGHT, VPLStyles.LABEL_HEIGHT));

			this.add(siteName);
			this.add(numberSets);
			this.add(complete);
			this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 314));
		}
		else {
			this.remove(siteName);
			this.remove(numberSets);
			this.remove(complete);
			this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
		}
	}
}
