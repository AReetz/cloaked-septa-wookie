package net.hlw5a.VidPicLib.Ui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.hlw5a.VidPicLib.Pass.State;
import net.hlw5a.VidPicLib.Site;
import net.hlw5a.VidPicLib.Database.Database;

public class PassNew extends JPanel {

	private static final long serialVersionUID = 1880638996354120578L;

	public PassNew(final Site site) {			
		final JTextField username = new JTextField(JLabel.CENTER);
		username.setFont(VPLStyles.REGULAR);
		username.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		username.setOpaque(true);
		username.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		final JTextField password = new JTextField(JLabel.CENTER);
		password.setFont(VPLStyles.REGULAR);
		password.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.TEXTFIELD_HEIGHT));
		password.setOpaque(true);
		password.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel date = new JLabel(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), JLabel.CENTER);
		date.setFont(VPLStyles.REGULAR);
		date.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		date.setOpaque(true);
		date.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JComboBox state = new JComboBox(State.values());
		state.setSelectedItem(State.unknown);
		state.setEnabled(false);
		state.setFont(VPLStyles.REGULAR);
		state.setPreferredSize(new Dimension(142, VPLStyles.COMBOBOX_HEIGHT));
		state.setOpaque(true);
		state.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JButton add = new JButton("+");
		add.setFont(VPLStyles.BOLD);
		add.setPreferredSize(new Dimension(44, VPLStyles.COMBOBOX_HEIGHT));
		add.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) { Database.getInstance().createPass(username.getText(), password.getText(), site); }
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		this.add(username);
		this.add(password);
		this.add(date);
		this.add(state);
		this.add(add);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 106));
	}
}
