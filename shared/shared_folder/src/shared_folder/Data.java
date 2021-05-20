package shared_folder;

class Data{	
	String name;
	int numberOfFile;
	String creationTime;
	String lastmodified;
	boolean isDirectory;
	long size;
	String path;
	
	Data(){
		this.name="";
		this.numberOfFile=0;
		this.creationTime="";
		this.lastmodified="";
		this.size=0;
		this.path="";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfFile() {
		return numberOfFile;
	}

	public void setNumberOfFile(int numberOfFile) {
		this.numberOfFile = numberOfFile;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}