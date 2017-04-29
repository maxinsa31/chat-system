package message;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;


public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String data;
	private File file;
	private boolean isFile = false;
	private byte[] contentFile = null;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		try {
			contentFile = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFile() {
		return isFile;
	}

	
	public byte[] getContentFile() {
		return contentFile;
	}

	public void setTypeData(Object ob) {
		if(ob instanceof File)
			isFile = true;
	}	
}
