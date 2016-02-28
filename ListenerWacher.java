package com.inautix.file.csv;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

public class ListenerWacher {
	public static WatchService watcher = null;
	
	private ListenerWacher() {}
	
	public static WatchService getWacherServiceInstance() {
		if(watcher == null)
			try {
				System.out.println("New Watcher instance.......");
				watcher = FileSystems.getDefault().newWatchService();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return watcher;
	}
}
