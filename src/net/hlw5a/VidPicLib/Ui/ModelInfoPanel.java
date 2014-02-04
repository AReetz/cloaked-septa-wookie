package net.hlw5a.VidPicLib.Ui;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Model.Built;
import net.hlw5a.VidPicLib.Model.Cup;
import net.hlw5a.VidPicLib.Model.Race;
import net.hlw5a.VidPicLib.Set;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.Database;

public class ModelInfoPanel extends JPanel  {

	private static final long serialVersionUID = 8798932887353983479L;
	private Model model;
	
	public ModelInfoPanel(Model model) {
		this.model = model;
		
		this.add(new ModelSummary());
		this.add(new SiteSummary());
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(2 * VPLStyles.CONTROL_WIDTH, 366));
	}
	
	private class ModelSummary extends JPanel {

		private static final long serialVersionUID = 5080178577378986479L;

		public ModelSummary() {
			
			JLabel id = new JLabel(String.valueOf(model.getId()), JLabel.CENTER);
			id.setFont(VPLStyles.SMALL);
			id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
			id.setOpaque(true);
			id.setBackground(VPLStyles.TEXT_BACKGROUND);
			
			JLabel modelName = new JLabel(model.getName(), JLabel.CENTER);
			modelName.setFont(VPLStyles.BOLD);
			modelName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH - VPLStyles.TEXTFIELD_HEIGHT - 5, VPLStyles.TEXTFIELD_HEIGHT));
			modelName.setOpaque(true);
			modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
			
			/*JLabel modelRace = new JLabel(model.getRace().name(), JLabel.CENTER);
			modelRace.setFont(VPLStyles.REGULAR);
			modelRace.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
			modelRace.setOpaque(true);
			modelRace.setBackground(VPLStyles.TEXT_BACKGROUND);*/
			
			JComboBox<Race> modelRace = new JComboBox<Race>(Database.getInstance().getRaces());
			modelRace.setSelectedItem(model.getRace());
			modelRace.setFont(VPLStyles.REGULAR);
			modelRace.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
			modelRace.setOpaque(true);
			modelRace.setBackground(VPLStyles.TEXT_BACKGROUND);
			modelRace.setRenderer(new ListCellRenderer<Race>() {
				protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
				public Component getListCellRendererComponent(JList<? extends Race> list, Race value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					renderer.setText(value.name());
				    return renderer;
				}
			});
			modelRace.addItemListener(new ItemListener() { public void itemStateChanged(ItemEvent arg0) { if (arg0.getStateChange() == ItemEvent.SELECTED) { model.setRace((Race)arg0.getItem()); } } });
			
			JComboBox<Built> modelBuilt = new JComboBox<Built>(Database.getInstance().getBuilts());
			modelBuilt.setSelectedItem(model.getBuilt());
			modelBuilt.setFont(VPLStyles.REGULAR);
			modelBuilt.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
			modelBuilt.setOpaque(true);
			modelBuilt.setBackground(VPLStyles.TEXT_BACKGROUND);
			modelBuilt.setRenderer(new ListCellRenderer<Built>() {
				protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
				public Component getListCellRendererComponent(JList<? extends Built> list, Built value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					renderer.setText(value.name());
				    return renderer;
				}
			});
			modelBuilt.addItemListener(new ItemListener() { public void itemStateChanged(ItemEvent arg0) { if (arg0.getStateChange() == ItemEvent.SELECTED) { model.setBuilt((Built)arg0.getItem()); } } });
			
			JLabel modelAge = new JLabel(String.valueOf(model.getAge()), JLabel.CENTER);
			modelAge.setFont(VPLStyles.REGULAR);
			modelAge.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 - 39, VPLStyles.TEXTFIELD_HEIGHT));
			modelAge.setOpaque(true);
			modelAge.setBackground(VPLStyles.TEXT_BACKGROUND);
	
			JLabel modelMeasurements = new JLabel(model.getMeasurements(), JLabel.CENTER);
			modelMeasurements.setFont(VPLStyles.REGULAR);
			modelMeasurements.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 * 2 - 20, VPLStyles.TEXTFIELD_HEIGHT));
			modelMeasurements.setOpaque(true);
			modelMeasurements.setBackground(VPLStyles.TEXT_BACKGROUND);
			
			JComboBox<Cup> modelCup = new JComboBox<Cup>(Database.getInstance().getCups());
			modelCup.setSelectedItem(model.getCup());
			modelCup.setFont(VPLStyles.REGULAR);
			modelCup.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH / 3 + 14, VPLStyles.COMBOBOX_HEIGHT));
			modelCup.setOpaque(true);
			modelCup.setBackground(VPLStyles.TEXT_BACKGROUND);
			modelCup.setRenderer(new ListCellRenderer<Cup>() {
				protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
				public Component getListCellRendererComponent(JList<? extends Cup> list, Cup value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					renderer.setText(value.name().replace("_up", "+"));
				    return renderer;
				}
			});
			modelCup.addItemListener(new ItemListener() { public void itemStateChanged(ItemEvent arg0) { if (arg0.getStateChange() == ItemEvent.SELECTED) { model.setCup((Cup)arg0.getItem()); } } });
			
			JLabel picture = new JLabel(new ImageIcon(model.getImage()));
			picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 256));
			
			this.add(id);
			this.add(modelName);
			this.add(modelRace);
			this.add(modelBuilt);
			this.add(modelAge);
			this.add(modelMeasurements);
			this.add(modelCup);
			this.add(picture);
			this.setOpaque(true);
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
			this.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH + 6, 366));
		}
	}
	
	private class SiteSummary extends JPanel {

		private static final long serialVersionUID = -1059863419900267604L;

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
