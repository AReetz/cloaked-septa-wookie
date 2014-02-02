package net.hlw5a.VidPicLib.Database;

public class ObservableObject {
	
	public enum Action {
		CREATE_MODEL,
		CREATE_SET,
		CREATE_PASS,
		DELETE_PASS,
		FILE_FOUND
	};
	
	private Action action;
	private Object object;
	public Action getAction() { return action; }
	public Object getObject() { return object; }
	public ObservableObject(Action action, Object object) {
		this.action = action;
		this.object = object;
	}
}
