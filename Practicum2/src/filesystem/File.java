package filesystem;
import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * A class of files.
 *
 * @invar	Each file must have a properly spelled name.
 * 			| isValidName(getName())
 * @invar	Each file must have a valid size.
 * 			| isValidSize(getSize())
 * @invar   Each file must have a valid creation time.
 *          | isValidCreationTime(getCreationTime())
 * @invar   Each file must have a valid modification time.
 *          | canHaveAsModificationTime(getModificationTime())
 * @invar   Each file must have a valid type
 * 			| canHaveAsType(getType())
 * @author  Mark Dreesen
 * @author  Tommy Messelis
 * @author 	Elias Storme
 * @author  Robbe Louage
 * @version 3.0
 */
public class File extends FilesystemItem{

    /**********************************************************
     * Constructors
     **********************************************************/

    /**
     * Initialize a new file in a given directory with given name, size, writability and type.
     *
     * @param  	name
     *         	The name of the new file.
     * @param  	size
     *         	The size of the new file.
     * @param  	writable
     *         	The writability of the new file.
     * @param   type
     *          the type of the file
     * @param   dir
     * 			the directory in which you want to create a file
     * @effect  The name of the file is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The size is set to the given size (must be valid)
     * 			| setSize(size)
     * @effect	The writability is set to the given flag
     * 			| setWritable(writable)
     * @post    The new creation time of this file is initialized to some time during
     *          constructor execution.
     *          | (new.getCreationTime().getTime() >= System.currentTimeMillis()) &&
     *          | (new.getCreationTime().getTime() <= (new System).currentTimeMillis())
     * @post    The new file has no time of last modification.
     *          | new.getModificationTime() == null
     * @post	the type of the file is set to the given type
     * 			if the type is not valid the default type is used (".txt").   
     */
	public File(Directory dir,String name, int size, boolean writable,String filetype) {
		super(dir, name, writable);
        setSize(size);
        if (canHaveAsType(filetype)){
        	this.filetype = filetype;
        }
        else {
        	this.filetype = "txt";
        } 
    }
	
	/**
	 * Makes a new rootfile
	 * @param name
	 * 		  the name of the file
	 * @param size
	 * 		  the size of the file
	 * @param writable
	 * 	      the writability of the file
	 * @param type
	 * 		  the type of the file
	 */
	public File(String name,int size,boolean writable,String type){
		this(null,name,size,writable,type);
	}
	
	/**
	 * Makes a new empty file in the given directory, name and type
	 * @param dir
	 * 		  the directory in which the file is stored
	 * @param name
	 * 		  the name of the file
	 * @param type
	 * 		  the type of the file
	 */
	public File(Directory dir,String name,String type){
		this(dir,name,0,true,type);
	}
	
	/**
	 * Makes a new rootfile with given name and type
	 * @param name
	 * 		  the name of the file
	 * @param type
	 * 		  the type of the file
	 */
	public File(String name,String type){
		this(null,name,0,true,type);
	}
    
    /**********************************************************
     * size - nominal programming
     **********************************************************/
    
    /**
     * Variable registering the size of this file (in bytes).
     */
    private int size = 0;
    
    /**
     * Variable registering the maximum size of any file (in bytes).
     */
    private static final int maximumSize = Integer.MAX_VALUE;


    /**
     * Return the size of this file (in bytes).
     */
    @Raw @Basic 
    public int getSize() {
        return size;
    }
    
    /**
     * Set the size of this file to the given size.
     *
     * @param  size
     *         The new size for this file.
     * @pre    The given size must be legal.
     *         | isValidSize(size)
     * @post   The given size is registered as the size of this file.
     *         | new.getSize() == size
     */
    @Raw @Model 
    private void setSize(int size) {
        this.size = size;
    }
   
    /**
     * Return the maximum file size.
     */
    @Basic @Immutable
    public static int getMaximumSize() {
        return maximumSize;
    }

    /**
     * Check whether the given size is a valid size for a file.
     *
     * @param  size
     *         The size to check.
     * @return True if and only if the given size is positive and does not
     *         exceed the maximum size.
     *         | result == ((size >= 0) && (size <= getMaximumSize()))
     */
    public static boolean isValidSize(int size) {
        return ((size >= 0) && (size <= getMaximumSize()));
    }

    /**
     * Increases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be increased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is increased with the given delta.
     *          | changeSize(delta)
     */
    public void enlarge(int delta) throws NotWritableException {
        changeSize(delta);
    }

    /**
     * Decreases the size of this file with the given delta.
     *
     * @param   delta
     *          The amount of bytes by which the size of this file
     *          must be decreased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The size of this file is decreased with the given delta.
     *          | changeSize(-delta)
     */
    public void shorten(int delta) throws NotWritableException {
        changeSize(-delta);
    }

    /**
     * Change the size of this file with the given delta.
     *
     * @param  delta
     *         The amount of bytes by which the size of this file
     *         must be increased or decreased.
     * @pre    The given delta must not be 0
     *         | delta != 0
     * @effect The size of this file is adapted with the given delta.
     *         | setSize(getSize()+delta)
     * @effect The modification time is updated.
     *         | setModificationTime()
     * @throws NotWritableException(this)
     *         This file is not writable.
     *         | ! isWritable()
     */
    @Model 
    private void changeSize(int delta) throws NotWritableException{
        if (isWritable()) {
            setSize(getSize()+delta);
            setModificationTime();            
        }else{
        	throw new NotWritableException(this);
        }
    }
        
    /**********************************************************
     * file type: total programming
     **********************************************************/
 
    /**
     * the type of the file
     */
    private final String filetype;

    /**
     * checks of the type is valid
     * @param type
     * 	      the type you want to check if it is valid
     * @return true
     * 		   if the type is valid
     * 		   false
     * 		   of the type is not valid
     */
    private static boolean canHaveAsType(String type){
    	ArrayList<String> allowedTypes = new ArrayList<String>() {{ add("txt"); add("pdf"); add("java"); }};
    	for (String str: allowedTypes){
    	    if(str.trim().contains(type))
    	        return true;
    	}
    	return false;
    }
    
    /**
     * getting the filetype
     * @return the filetype
     */
    @Basic @Raw
    private String getType(){
    	return this.filetype;
    }
    
    /**********************************************************
     * directory
     **********************************************************/
    
    /**
     * the directory where the file is stored
     */
    private Directory dir;
    
    /**
     * gives the directory where the file is stored.
     * @return the directory where the file is stored or null if it is a rootfile
     */
    public Directory getDir(){
    	return this.dir;
    }

	@Override
	String getDefaultName() {
		return "new-file";
	}
    
	
}	
