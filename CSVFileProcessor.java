package com.file.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import static java.nio.file.StandardWatchEventKinds.*;

@Controller
@EnableScheduling
public class CSVFileProcessor extends GenericFileProcessor {
	private static Properties prop = new Properties();
	private static final String INPUT_DIR = "_INPUTDIR";
	private static final String OUTPUT_DIR = "_OUTPUTDIR";
	private static final String ARCHIVEDIR = "_ARCHIVEDIR";
	private static final String TOEMAIL = "_TOEMAIL";
	private static final String FROMMAIL = "_FROMEMAIL";
	private static final String PROCESSID = "_PROCESSID";
	private static final WatchService watcher = ListenerWacher.getWacherServiceInstance();
	private static Map<String, String> properties;
	
	static {
		try {
			System.out.println("initiallising properites.....");
			prop.load(CSVFileProcessor.class.getClassLoader().getResourceAsStream("path.properties"));
			String COMMONS_LOB = prop.getProperty("COMMONS_LOB");
			System.out.println("COMMONS_LOB = "+ COMMONS_LOB);
			properties = new HashMap<String, String>();
			String[] lobs = COMMONS_LOB.split(DELIMITER);
			for(String lob : lobs) {
				properties.put(lob+INPUT_DIR, prop.getProperty(lob+INPUT_DIR));
				Path p = Paths.get(prop.getProperty(lob+INPUT_DIR));
					p.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
				properties.put(lob+OUTPUT_DIR, prop.getProperty(lob+OUTPUT_DIR));
				properties.put(lob+ARCHIVEDIR, prop.getProperty(lob+ARCHIVEDIR));
				properties.put(lob+TOEMAIL, prop.getProperty(lob+TOEMAIL));
				properties.put(lob+FROMMAIL, prop.getProperty(lob+FROMMAIL));
				properties.put(lob+PROCESSID, prop.getProperty(lob+PROCESSID));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 1000)
	public void keepWatchingDir() {
		while (true) {
		    WatchKey key;
		    try {
		        // wait for a key to be available
		        key = watcher.take();
		    } catch (InterruptedException ex) {
		        return;
		    }
		 
		    for (WatchEvent<?> event : key.pollEvents()) {
		        WatchEvent.Kind<?> kind = event.kind();

		        @SuppressWarnings("unchecked")
		        WatchEvent<Path> ev = (WatchEvent<Path>) event;
		        Path fileName = ev.context();
		 
		        System.out.println(kind.name() + ": " + fileName);
		 
		        if (kind == OVERFLOW) {
		            continue;
		        } else if (kind == ENTRY_CREATE) {
		        	System.out.println("Create Event...");
		        	this.processFiles(ev.context().resolve(fileName));
		        } else if (kind == ENTRY_DELETE) {
		        	System.out.println("Delete Event...");
		        } else if (kind == ENTRY_MODIFY) {
		        	System.out.println("Modify Event...");
		        }
		    }
		 
		    // IMPORTANT: The key must be reset after processed
		    boolean valid = key.reset();
		    if (!valid) {
		        break;
		    }
		}
	}
	
	public void processFiles(Path p) {
		System.out.println("processing file.... "+ p);
	}
	
	
}
