package org.rhq.common.drift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DirectoryEntry implements Iterable<FileEntry>, Serializable {

    private static final long serialVersionUID = 1L;

    private String directory;

    private List<FileEntry> files = new ArrayList<FileEntry>();

    public DirectoryEntry(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public DirectoryEntry add(FileEntry entry) {
        files.add(entry);
        return this;
    }

    public DirectoryEntry remove(FileEntry entry) {
        FileEntry entryToRemove = null;
        int i = 0;
        for (FileEntry fileEntry : files) {
            if (entry.getFile().equals(fileEntry.getFile())) {
                entryToRemove = fileEntry;
                break;
            }
            ++i;
        }
        if (entryToRemove != null) {
            files.remove(i);
        }
        return this;
    }

    public int getNumberOfFiles() {
        return files.size();
    }

    public Iterator<FileEntry> iterator() {
        return files.iterator();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[directory: " + directory + "]";
    }
}
