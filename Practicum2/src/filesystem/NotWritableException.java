package filesystem;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signalling illegal attempts to change a file.
 */
public class NotWritableException extends RuntimeException {

	/**
	 * Required because this class inherits from Exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable referencing the file to which change was denied.
	 */
	private final FilesystemItem item;

	/**
	 * Initialise this new file not writable exception involving the
	 * given file.
	 * 
	 * @param	file
	 * 			The file for the new file not writable exception.
	 * @post	The file involved in the new file not writable exception
	 * 			is set to the given file.
	 * 			| new.getFile() == file
	 */
	@Raw
	public NotWritableException(FilesystemItem item) {
		this.item = item;
	}
	
	/**
	 * Return the file involved in this file not writable exception.
	 */
	@Raw @Basic
	public FilesystemItem getFilesystemItem() {
		return item;
	}
	
	
}
