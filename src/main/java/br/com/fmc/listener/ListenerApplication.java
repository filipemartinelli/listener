package br.com.fmc.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.fmc.listener.whatcher.WatcherService;

@SpringBootApplication
@EnableScheduling
public class ListenerApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerApplication.class);

	@Autowired
	public WatcherService watcherService;

	public static void main(String[] args) {
		SpringApplication.run(ListenerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        LOGGER.info("watcherService Started");
        new Thread(watcherService, "watcher-service").start();
	}

}
