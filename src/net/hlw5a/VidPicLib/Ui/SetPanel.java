package net.hlw5a.VidPicLib.Ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.hlw5a.VidPicLib.Model;
import net.hlw5a.VidPicLib.Set;

public class SetPanel extends JPanel {

	private static final long serialVersionUID = 7529093994691453362L;
	private Set set;
	private JLabel id;
	
	public int getId() {
		return set.getId();
	}
	
	public SetPanel(Set set) {
		this.set = set;
				
		id = new JLabel(String.valueOf(this.set.getId()), JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		if (set.getFileExists()) { id.setBackground(VPLStyles.TEXT_BACKGROUND); } else { id.setBackground(Color.red); }
		
		final JLabel name = new JLabel(this.set.getName(), JLabel.CENTER);
		name.setFont(VPLStyles.BOLD);
		name.setPreferredSize(new Dimension(165, VPLStyles.TEXTFIELD_HEIGHT));
		name.setOpaque(true);
		name.setBackground(VPLStyles.TEXT_BACKGROUND);
		name.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (name.getText() == SetPanel.this.set.getName()) name.setText(SetPanel.this.set.getNumber());
					else if (name.getText() == SetPanel.this.set.getNumber()) name.setText(SetPanel.this.set.getName());
				}
			}
		});
		
		JLabel date = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(this.set.getDate()), JLabel.CENTER);
		date.setFont(VPLStyles.REGULAR);
		date.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		date.setOpaque(true);
		date.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JComboBox<Model> model = new JComboBox<Model>(this.set.getModels());
		model.setSelectedItem(this.set.getMainModel());
		model.setFont(VPLStyles.REGULAR);
		model.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.COMBOBOX_HEIGHT));
		model.setOpaque(true);
		model.setBackground(VPLStyles.TEXT_BACKGROUND);
		model.setRenderer(new ListCellRenderer<Model>() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			public Component getListCellRendererComponent(JList<? extends Model> list, Model value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel renderer = (JLabel)defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				renderer.setText(value.getName());
			    return renderer;
			}
		});
		
		JLabel site = new JLabel(this.set.getSite().getName(), JLabel.CENTER);
		site.setFont(VPLStyles.ITALIC);
		site.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		site.setOpaque(true);
		site.setBackground(VPLStyles.TEXT_BACKGROUND);
		JLabel picture = new JLabel(new ImageIcon(this.set.getImage()));
		picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 256));
		
		this.add(id);
		this.add(name);
		this.add(date);
		this.add(model);
		this.add(site);
		this.add(picture);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 366));
	}
	
	public void setFileExist() {
		if (set.getFileExists()) { id.setBackground(VPLStyles.TEXT_BACKGROUND); } else { id.setBackground(Color.red); }
	}
}
