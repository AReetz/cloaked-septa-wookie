package net.hlw5a.VidPicLib.Ui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.hlw5a.VidPicLib.Model;

public class ModelPanel extends JPanel {
	
	private static final long serialVersionUID = -4790554520103975164L;
	private Model model;
	
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
		modelName.setPreferredSize(new Dimension(165, VPLStyles.TEXTFIELD_HEIGHT));
		modelName.setOpaque(true);
		modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel picture = new JLabel(new ImageIcon(this.model.getImage()));
		picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 256));
		
		this.add(id);
		this.add(modelName);
		this.add(picture);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 294));
	}
}
