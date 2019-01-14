package br.com.fmc.listener.whatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.fmc.listener.services.impl.InputService;
import br.com.fmc.listener.services.impl.OutPutService;
import br.com.fmc.listener.util.FileUtil;
import lombok.Data;

@Component
@Data
public class WatcherService implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(WatcherService.class);

	private WatchService watchService;

	@Value("${app.in}")
	private String in;
	@Value("${app.out}")
	private String out;
	@Value("#{'${file.extensions.in}'.split(',')}")
	private List<String> extensions;

	@Autowired
	private InputService inputService;

	@Autowired
	private OutPutService outPutService;

	public WatcherService() throws IOException {
		this.watchService = FileSystems.getDefault().newWatchService();
	}

	@Override
	public void run() {
		setup();
		for (;;) {
			LOGGER.info("Running ...");
			try {
				executeExistsFiles();
				Watching();
			} catch (InterruptedException | IOException e) {
				LOGGER.error("Error WatcherService.run()");
				throw new RuntimeException(e);
			}
		}
	}

	private void Watching() throws InterruptedException, IOException {
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				processEvent(event);
			}
			key.reset();
			report();
		}

	}

	private void processEvent(WatchEvent<?> event) throws IOException {
		Object context = event.context();
		process(context.toString());
	}

	private void process(String context) throws IOException {
		if (isValidExtension(context)) {
			LOGGER.info("File: " + context);
			Stream<String> file = FileUtil.readFile(in, context);
			file.forEach(x -> inputService.process(x));
		} else {
			LOGGER.info("Ignored File: " + context);
		}
	}

	private boolean isValidExtension(Object path) {
		return extensions.stream().filter(x -> path.toString().endsWith(x)).findAny().orElse(null) != null;
	}

	private void setup() {
		File dir = FileUtil.makeDirectory(in);
		FileUtil.makeDirectory(out);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			register(watchService, dir);
		} catch (IOException e) {
			LOGGER.error("Setup problems");
		}
	}

	private void register(WatchService watcher, File dir) throws IOException {
		Path path = Paths.get(dir.getAbsolutePath());
		path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

	}

	private void executeExistsFiles() throws IOException {
		File file = new File(in);
		String[] files = file.list();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				process(files[i]);
			}
		}
		report();
	}

	private void report() {
		outPutService.generateReport();
	}

}
