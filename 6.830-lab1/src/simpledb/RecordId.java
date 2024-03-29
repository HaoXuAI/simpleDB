package simpledb;

import java.util.Objects;

/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.
 */
public class RecordId {

	private PageId recordPid;
	private int recordTupleno;
	
    /** Creates a new RecordId refering to the specified PageId and tuple number.
     * @param pid the pageid of the page on which the tuple resides
     * @param tupleno the tuple number within the page.
     */
    public RecordId(PageId pid, int tupleno) {
        // some code goes here
    	this.recordPid = pid;
    	this.recordTupleno = tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int tupleno() {
        // some code goes here
        return this.recordTupleno;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        // some code goes here
        return this.recordPid;
    }
    
    /**
     * Two RecordId objects are considered equal if they represent the same tuple.
     * @return True if this and o represent the same tuple
     */
    @Override
    public boolean equals(Object o) {
    	// some code goes here
    	if (!(o instanceof RecordId)) {
    		return false;
    	}
    	RecordId o_fake = (RecordId) o;
    	if (o_fake.getPageId().equals(this.recordPid) && o_fake.tupleno() == this.recordTupleno) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * You should implement the hashCode() so that two equal RecordId instances
     * (with respect to equals()) have the same hashCode().
     * @return An int that is the same for equal RecordId objects.
     */
    @Override
    public int hashCode() {
    	// some code goes here
    	return Objects.hashCode(this.recordPid);
    	
    }
    
}
