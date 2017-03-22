package filesystem;
import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Directory  { 

	private ArrayList<File> files;
	private Directory parent;
	private String name;
	private boolean writable;
	private final Date creationTime = new Date();
	private Date modificationTime = null;
	

}
