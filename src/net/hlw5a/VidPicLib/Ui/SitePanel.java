package net.hlw5a.VidPicLib.Ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.hlw5a.VidPicLib.Site;

public class SitePanel extends JPanel {

	private static final long serialVersionUID = 6997043197623025612L;
	private Site site;
	
	public int getId() {
		return site.getId();
	}
	
	public SitePanel(Site site) {
		this.site = site;
		
		JLabel id = new JLabel(String.valueOf(this.site.getId()), JLabel.CENTER);
		id.setFont(VPLStyles.SMALL);
		id.setPreferredSize(new Dimension(VPLStyles.TEXTFIELD_HEIGHT, VPLStyles.TEXTFIELD_HEIGHT));
		id.setOpaque(true);
		id.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel name = new JLabel(this.site.getName(), JLabel.CENTER);
		name.setFont(VPLStyles.BOLD);
		name.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH - VPLStyles.TEXTFIELD_HEIGHT - 5, VPLStyles.TEXTFIELD_HEIGHT));
		name.setOpaque(true);
		name.setBackground(VPLStyles.TEXT_BACKGROUND);
		
		JLabel url = new JLabel(this.site.getUrl().toString(), JLabel.CENTER);
		url.setFont(VPLStyles.REGULAR);
		url.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, VPLStyles.LABEL_HEIGHT));
		url.setOpaque(true);
		url.setBackground(VPLStyles.TEXT_BACKGROUND);
		url.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringSelection selection = new StringSelection(SitePanel.this.site.getUrl().toString());
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
				}
			}
		});
		
		JLabel picture = new JLabel(new ImageIcon(this.site.getImage()));
		picture.setPreferredSize(new Dimension(VPLStyles.COMPONENT_WIDTH, 64));
		
		this.add(id);
		this.add(name);
		this.add(url);
		this.add(picture);
		this.setBackground(VPLStyles.CONTAINER_BACKGROUND);
		this.setPreferredSize(new Dimension(VPLStyles.CONTROL_WIDTH, 124));
	}
}
