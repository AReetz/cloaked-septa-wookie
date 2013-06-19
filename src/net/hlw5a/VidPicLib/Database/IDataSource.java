package net.hlw5a.VidPicLib.Database;

import java.awt.Image;
import java.io.IOException;

public interface IDataSource {
	void Load();
	void Save();
	void Open();
	Image getImage(String imageName) throws IOException;
}
