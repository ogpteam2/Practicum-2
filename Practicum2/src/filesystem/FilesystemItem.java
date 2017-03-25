package filesystem;

public abstract class FilesystemItem {
	public FilesystemItem(Directory dir, String name, boolean writable){
		
	}
	public FilesystemItem(Directory dir, String name){
		this(dir, name, true);
	}
	public FilesystemItem(String name, boolean writable){
		this(null, name, writable);
	}
	public FilesystemItem(String name){
		this(null, name, true);
	}
}
