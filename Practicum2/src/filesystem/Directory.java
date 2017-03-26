package filesystem;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Directory extends FilesystemItem {

	private ArrayList<File> files;
	private Directory parent;
	private String name;
	private boolean isWritable;
	private final Date creationTime = new Date();
	private Date modificationTime = null;

	/**********************************************************
	 * constructors
	 **********************************************************/
	
	/**
	 * Full constructor of the class, initialising directory with 
	 * given parent, name and writability.
	 * 
	 * @param	dir
	 * 			Parent of the new directory.
	 * @param	name
	 * 			Name of the new directory
	 * @param	writable
	 * 			Writability of the new directory.
	 * @effect	The directory is initialised as a FileSystemObject with
	 * 			the given directory, name and writablilty.
	 * 			| new FileSystemObject(dir, name, writable)
	 */
	
	public Directory(Directory dir, String name, boolean writable) {
		super(dir, name, writable);
	}

	public Directory(String name, boolean writable) {
		this(null, name, writable);
	}

	public Directory(String name) {
		this(null, name, true);
	}

	/**********************************************************
	 * name - total programming
	 **********************************************************/

	/**
	 * Requirement by superclass: give name to use when given name is not valid.
	 *
	 * @return A valid file name. | isValidName(result)
	 */
	@Raw
	public String getDefaultName() {
		return "new_directory";
	}

	/**********************************************************
	 * directory methods
	 **********************************************************/

}
