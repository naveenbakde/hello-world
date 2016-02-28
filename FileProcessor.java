package com.file.csv;

import java.nio.file.Path;

public interface FileProcessor {
	public void readFiles();
	public void processFiles(Path p);
	public void archiveFiles(Path p);

}
