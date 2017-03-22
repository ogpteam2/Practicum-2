package filesystem;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to change a directory.
 */
public class DirectoryNotWritableException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable referencing the directory to which change was denied.
	 */
	private final Directory dir;

	/**
	 * Initialize this new directory not writable exception involving the
	 * given directory.
	 * 
	 * @param	directory
	 * 			The directory for the new directory not writable exception.
	 * @post	The directory involved in the new directory not writable exception
	 * 			is set to the given directory.
	 * 			| new.getFile() == directory
	 */
	@Raw
	public DirectoryNotWritableException(Directory dir) {
		this.dir = dir;
	}
	
	/**
	 * Return the directory involved in this directory not writable exception.
	 */
	@Raw @Basic
	public Directory getDir() {
		return this.dir;
	}
	
	
}
