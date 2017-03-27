package filesystem;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Directory extends FilesystemItem {
	
	private Directory parent;
	private String itemName;
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
	 * directory content
	 **********************************************************/
	
	private ArrayList<FilesystemItem> contents = new ArrayList<>();
	
	/**********************************************************
	 * content validity checking
	 **********************************************************/
	
	/**
	 * Checks if the given FilesystemItem could be added to the directory
	 * @param 	item
	 * 			item to check for content validity
	 * @return 	whether item can be safely added to the directory
	 */
	
	public boolean canBeAddedToDirectory(FilesystemItem item){
		if (exists(item.getName())) return false;
		
		else if (item.getClass() == Directory.class && 
				(isDirectOrIndirectSubDirectoryOf((Directory) item) || item == this)) //protection against directory loop
				return false;
		
		else return true;
	}
	
	/**
	 * Recursively checks if the given directory is one of the parents of
	 * this directory
	 * @param 	directory
	 * 			directory to check for parenthood of this directory
	 * @return	whether directory is parent of this directory
	 */
	public boolean isDirectOrIndirectSubDirectoryOf(Directory directory){
		if(this.isRoot()) return false;
		else {
			Directory parent = getDirectory();
			if(parent == directory) return true;
			else return parent.isDirectOrIndirectSubDirectoryOf(directory);
		}
	}
	
	/**********************************************************
	 * Content manipulation
	 **********************************************************/
	
	public void addItem(FilesystemItem item) throws IllegalArgumentException, NotWritableException{
		if(canBeAddedToDirectory(item)){
			if (isWritable()){
				this.contents.add(binarySearchForItem(item.getName()), item);
			}
			else throw new NotWritableException(this);
		}
		else throw new IllegalArgumentException("The given object is not valid content for this directory.");
	}
	
	public void removeItem(FilesystemItem item) throws IllegalArgumentException, NotWritableException{
		if(hasAsItem(item)){
			if(isWritable()){
				this.contents.remove(item);
				if(!this.isRoot()) this.makeRoot();
			}
			else throw new NotWritableException(this);
		}
		else throw new IllegalArgumentException();
	}
	
	/**********************************************************
	 * Indexing and item existence
	 **********************************************************/
	
	public FilesystemItem getItemAt(int index) throws IndexOutOfBoundsException{
		if (index > 0 && index <= this.contents.size()){
			return this.contents.get(index);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public boolean exists(String itemName){
		for(FilesystemItem item: this.contents){
			if(item.getName().toLowerCase().equals(itemName.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public int getNbItems(){
		return contents.size();
	}
	
	/**
	 * Searches for an item in the directory with the given name using
	 * the binarySearchForItem method.
	 * @param 	itemName
	 * 			name of the required item
	 * @return	the required item
	 * @throws 	IllegalArgumentException
	 * 			if no item exists with the given name within this directory, this error is thrown.
	 */
	
	public FilesystemItem getItem(String itemName) throws IllegalArgumentException{
		if (!exists(itemName)){
			throw new IllegalArgumentException("No item exists in this directory with the given name!");
		}
		else{
			return this.contents.get(binarySearchForItem(itemName));
		}
	}
	
	/**
	 * Implements the binary search algorithm (O(log(n)) to determine the index
	 * of an element in an ArrayList
	 * @param 	itemName
	 * 			name of the item for which the index is required
	 * @return	the index of the item for which the name was given
	 */
	
	private int binarySearchForItem(String itemName){
		itemName = itemName.toLowerCase();
		int lower = 0;
		int upper = this.contents.size();
		int middle;
		while (upper != lower){
			int comparedCharIndex = 0;
			middle = Math.floorDiv(upper + lower, 2);
			
			String middleItemName = this.contents.get(middle).getName().toLowerCase();
			
			while(true){
				if(middleItemName.charAt(comparedCharIndex) > itemName.charAt(comparedCharIndex)){
					upper = middle;
					break;
				} else if (middleItemName.charAt(comparedCharIndex) > itemName.charAt(comparedCharIndex)) {
					lower = middle++;
					break;
				} else {
					if(comparedCharIndex == itemName.length() - 1){
						upper = middle;
						break;
					} else {
						comparedCharIndex++;
					}
				}
			}
		}
		return lower;
	}
	
	/**
	 * Returns whether the given item exists within the directory or not
	 * @param 	item
	 * 			item to check existance of
	 * @return	existance of the item
	 */
	public boolean hasAsItem(FilesystemItem item){
		return contents.contains(item);
	}
	
	/**
	 * Returns the index of the given item within the directory if it exists
	 * @param 	item
	 * 			item to get the index of
	 * @return	index of the given item
	 * @throws 	IllegalArgumentException
	 * 			throws this error when item does not exist within this directory
	 */
	public int getIndexOf(FilesystemItem item) throws IllegalArgumentException{
		if (hasAsItem(item)){
			return contents.indexOf(item);
		} else {
			throw new IllegalArgumentException("No given object exists within this directory");
		}
	}
	
}
