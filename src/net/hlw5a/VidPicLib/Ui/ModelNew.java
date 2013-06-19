package net.hlw5a.VidPicLib.Ui;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import net.hlw5a.VidPicLib.Database.Database;

public class ModelNew extends JPanel {
	
	private static final long serialVersionUID = 5922297647696898018L;

	public ModelNew() {
		JLabel id = new JLabel("X", JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JTextField imageName = new JTextField("model picture name");
		imageName.setFont(VPLStyles.ITALIC);
		imageName.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		imageName.setOpaque(true);
		imageName.setBackground(VPLStyles.TEXT_BACKGROUND);

		final JTextField modelName = new JTextField("model name");
		modelName.setFont(VPLStyles.ITALIC);
		modelName.setPreferredSize(new Dimension(165, VPLStyles.TEXTFIELD_HEIGHT));
		modelName.setOpaque(true);
		modelName.setBackground(VPLStyles.TEXT_BACKGROUND);
		modelName.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) { if (modelName.getText().equals("")) modelName.setText("model name"); }
			public void focusGained(FocusEvent arg0) { if (modelName.getText().equals("model name")) modelName.setText(""); }
		});
		modelName.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent de) { }
			public void insertUpdate(DocumentEvent de) {
				try { imageName.setText(de.getDocument().getText(0, de.getDocument().getLength()).replace(' ', '_').toLowerCase() + "_model_X.jpg"); }
				catch (BadLocationException e) { }
			}
			public void changedUpdate(DocumentEvent de) { }
		});
		
		JButton save = new JButton("save model");
		save.setFont(VPLStyles.ITALIC);
		save.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		save.setOpaque(true);
		save.setBackground(VPLStyles.TEXT_BACKGROUND);
		save.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) { }
			public void mousePressed(MouseEvent arg0) { }
			public void mouseExited(MouseEvent arg0) { }
			public void mouseEntered(MouseEvent arg0) { }
			public void mouseClicked(MouseEvent arg0) { try {
				Database.getInstance().CreateModel(modelName.getText(), imageName.getText());
			} catch (IOException e) {
				e.printStackTrace();
			} }
		});
		
		this.add(id);
		this.add(modelName);
		this.add(imageName);
		this.add(save);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 295));
	}
}
