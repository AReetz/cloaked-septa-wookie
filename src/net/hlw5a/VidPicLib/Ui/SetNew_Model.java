package net.hlw5a.VidPicLib.Ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.DataSort;
import net.hlw5a.VidPicLib.Database.Database;

public class SetNew_Model extends JPanel {
	
	private static final long serialVersionUID = 5922297647696898018L;

	public SetNew_Model(final Model model) {
		JLabel id = new JLabel("X", JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JTextField setName = new JTextField("set name");
		setName.setFont(VPLStyles.ITALIC);
		setName.setPreferredSize(new Dimension(166, VPLStyles.TEXTFIELD_HEIGHT));
		setName.setOpaque(true);
		setName.setBackground(VPLStyles.TEXT_BACKGROUND);
		setName.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) { if (setName.getText().equals("")) setName.setText("set name"); }
			public void focusGained(FocusEvent arg0) { if (setName.getText().equals("set name")) setName.setText(""); }
		});
		
		final JFormattedTextField setDate = new JFormattedTextField("1999-12-31");
		MaskFormatter df;
		try {
			df = new MaskFormatter("####-##-##");
			df.setPlaceholderCharacter('_');
			setDate.setFormatterFactory(new DefaultFormatterFactory(df)); }
		catch (ParseException e1) { }
		setDate.setFont(VPLStyles.ITALIC);
		setDate.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		setDate.setOpaque(true);
		setDate.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		Vector<Site> tmpSites = Database.getInstance().getSites();
		Collections.sort(tmpSites, new DataSort.Sites.ByName());
		
		final JComboBox siteName = new JComboBox(tmpSites);
		siteName.setFont(VPLStyles.ITALIC);
		siteName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.COMBOBOX_HEIGHT));
		siteName.setOpaque(true);
		siteName.setBackground(VPLStyles.TEXT_BACKGROUND);
		siteName.setRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value != null) renderer.setText(((Site)value).getName());
			    return renderer;
			}
		});

		Vector<Model> tmpList = Database.getInstance().getModels();
		Collections.sort(tmpList, new DataSort.Models.ByName());
		
		final JTextField imageName = new JTextField(model.getName().replace(' ', '_').toLowerCase() + "_set_X.jpg");
		imageName.setFont(VPLStyles.ITALIC);
		imageName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		imageName.setOpaque(true);
		imageName.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JLabel setMainModel = new JLabel(model.getName(), JLabel.CENTER);
		setMainModel.setFont(VPLStyles.ITALIC);
		setMainModel.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		setMainModel.setOpaque(true);
		setMainModel.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JList setModels = new JList(tmpList);
		setModels.setFont(VPLStyles.ITALIC);
		setModels.setOpaque(true);
		setModels.setBackground(VPLStyles.TEXT_BACKGROUND);
		setModels.setCellRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value != null) renderer.setText(((Model)value).getName());
			    return renderer;
			}
		});
		JScrollPane setModelsScroll = new JScrollPane(setModels);
		setModelsScroll.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 198));
		
		JButton save = new JButton("save set");
		save.setFont(VPLStyles.ITALIC);
		save.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.COMBOBOX_HEIGHT));
		save.setOpaque(true);
		save.setBackground(VPLStyles.TEXT_BACKGROUND);
		save.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) { }
			public void mousePressed(MouseEvent arg0) { }
			public void mouseExited(MouseEvent arg0) { }
			public void mouseEntered(MouseEvent arg0) { }
			public void mouseClicked(MouseEvent arg0) {
				try {
					Vector<Model> models = new Vector<Model>();
					for (Object model : setModels.getSelectedValues()) { models.add((Model)model); }
					Database.getInstance().CreateSet(setName.getText(), (new SimpleDateFormat("yyyy-MM-dd")).parse(setDate.getText()), imageName.getText(), model, models, (Site)siteName.getItemAt(siteName.getSelectedIndex()));
				}
				catch (IOException e) { e.printStackTrace(); }
				catch (ParseException e) {  e.printStackTrace(); }
			}
		});
		
		this.add(id);
		this.add(setName);
		this.add(setDate);
		this.add(setMainModel);
		this.add(setModelsScroll);
		this.add(imageName);
		this.add(siteName);
		this.add(save);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 366));
	}
}