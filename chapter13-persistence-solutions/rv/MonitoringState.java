package rv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class MonitoringState {

	private HashMap<String, Object> store = new HashMap<String, Object>();

	public MonitoringState() {
		store = new HashMap<String, Object>();
	}

	public MonitoringState(String filename) {
		loadFromFile(filename);
	}

	public void saveValue(String key, Object object) {
		store.put(key, object);
	}

	public Object restoreValue(String key, Object default_value) {
		if (store.keySet().contains(key))
			return store.get(key);
		return default_value;
	}

	public void saveToFile(String filename) {
		// Serialization
		try {
			// Saving object in file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Serialization of hashmap
			out.writeObject(store);

			out.close();
			file.close();
		} catch (IOException e) {
			System.out.println("IOException is caught");
		}
	}

	public void loadFromFile(String filename) {
		try {
			// Reading the object from file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Deserialization of hashmap
			store = (HashMap) (in.readObject());

			in.close();
			file.close();
		} catch (IOException e) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}