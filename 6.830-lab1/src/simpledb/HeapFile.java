package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection
 * of tuples in no particular order.  Tuples are stored on pages, each of
 * which is a fixed size, and the file is simply a collection of those
 * pages. HeapFile works closely with HeapPage.  The format of HeapPages
 * is described in the HeapPage constructor.
 *
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {

	private File file;
	private TupleDesc tupledescriptor;
	private int id;
	
    /**
     * Constructs a heap file backed by the specified file.
     *
     * @param f the file that stores the on-disk backing store for this heap file.
     */
    public HeapFile(File f, TupleDesc td) {
        // some code goes here
    	this.file = f;
    	this.tupledescriptor = td;
    	this.id = f.getAbsoluteFile().hashCode();
    }

    /**
     * Returns the File backing this HeapFile on disk.
     *
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here
        return this.file;
    }

    /**
    * Returns an ID uniquely identifying this HeapFile. Implementation note:
    * you will need to generate this tableid somewhere ensure that each
    * HeapFile has a "unique id," and that you always return the same value
    * for a particular HeapFile. We suggest hashing the absolute file name of
    * the file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
    *
    * @return an ID uniquely identifying this HeapFile.
    */
    public int getId() {
        // some code goes here
    	return this.id;
        
    }
    
    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
    	// some code goes here
    	return this.tupledescriptor;
    	
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
        int pageSize = Database.getBufferPool().getPageSize();
        byte[] bytes = new byte[pageSize];
        HeapPageId id = new HeapPageId(pid.getTableId(), pid.pageno());
        HeapPage page = null;
        try {
        	RandomAccessFile reader = new RandomAccessFile(file, "r");
        	
        	long offset = 1L * pid.pageno() * pageSize;
        	reader.seek(offset);
        	reader.read(bytes);
        	page = new HeapPage(id, bytes);
        	reader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return page;
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for lab1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        // some code goes here
    	int pageSize = Database.getBufferPool().getPageSize();
        long fileSize = file.length();
        return (int) fileSize/pageSize;
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> addTuple(TransactionId tid, Tuple t)
        throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public Page deleteTuple(TransactionId tid, Tuple t)
        throws DbException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public DbFileIterator iterator(TransactionId tid) {
        // some code goes here
    	return new HeapFileIterator(this, tid);
    }
    
    public static class HeapFileIterator implements DbFileIterator {

    	private TransactionId tid;
		private HeapPageId pid;
		private int currPgId;
		private int numPages;
		private Iterator<Tuple> iter;
    	
		public HeapFileIterator(HeapFile file, TransactionId tid) {
			// TODO Auto-generated constructor stub
			this.tid = tid;
			this.numPages = file.numPages();
			this.currPgId = numPages + 1;
			this.pid = new HeapPageId(file.getId(), this.currPgId);
			
			
		}

		@Override
		public void open() throws DbException, TransactionAbortedException {
			// TODO Auto-generated method stub
			HeapPage page = (HeapPage) Database.getBufferPool().getPage(this.tid, this.pid, Permissions.READ_WRITE);
			iter = page.iterator();
		}

		@Override
		public boolean hasNext() throws DbException, TransactionAbortedException {
			// TODO Auto-generated method stub
			if (iter == null) {
				return false;
			}
			return iter.hasNext();
		}

		@Override
		public Tuple next() throws DbException, TransactionAbortedException, NoSuchElementException {
			// TODO Auto-generated method stub
			if (this.hasNext()) return null;
			throw new NoSuchElementException();
		}

		@Override
		public void rewind() throws DbException, TransactionAbortedException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}
    	
    }
}

