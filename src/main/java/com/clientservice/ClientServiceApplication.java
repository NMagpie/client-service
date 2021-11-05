package com.clientservice;

import com.clientservice.client.ClientGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientServiceApplication {

	private static String url;

	private static String port;

	private static TimeUnit timeUnit;

	private static final  SpringApplication app = new SpringApplication(ClientServiceApplication.class);

	public static void main(String[] args) {

		app.setDefaultProperties(Collections.singletonMap("server.port", port));

		app.run();

		ClientGenerator generator = new ClientGenerator(timeUnit);

		generator.start();
	}

	public static String getUrl() {
		return url;
	}

	public static TimeUnit getTimeUnit() {
		return timeUnit;
	}

	static {

		File config = new File("configCS.txt");

		try {

			Scanner scanner = new Scanner(config);

			String str = scanner.nextLine();

			timeUnit = TimeUnit.valueOf(str);

			port = scanner.nextLine();

			if (!port.matches("^\\d{4,5}$")) parsingError(2);

			url = scanner.nextLine();

			if (!url.matches("((https?://[\\w-]+)|(((https?://)?\\d{1,3}\\.){3}(\\d{1,3})(/\\d+)?)):\\d{4,5}"))
				parsingError(3);

		} catch (FileNotFoundException e) {
			parsingError(-1);
		} catch (NoSuchElementException e) {
			parsingError(0);
		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
			parsingError(1);
		}

	}

	private static void parsingError(int intCase) {

		if (intCase > -1) System.out.println("Wrong data in config-file! Config file has to contain by lines:" +
				"\n1. Time units by capslock (e.g. MILLISECONDS, SECONDS, MICROSECONDS)" +
				"\n2. Free port to be reserved for this server" +
				"\n3. IPv4 address or url of Food Ordering Service and its port (e.g. http://localhost:8081)");

		if (intCase > 0) System.out.print("ERROR IN LINE " + intCase + ": ");

		switch (intCase) {

			case -1:
				System.out.println("\"configCS.txt\" file have to be in the same directory as jar file or projects");
				break;
			case 0:
				System.out.println("ERROR: WRONG NUMBER IF LINES");
				break;
			case 1:
				System.out.println("TIMEUNITS");
				break;
			case 2:
				System.out.println("Port of this server");
				break;
			case 3:
				System.out.println("ADDRESS OR IP");
				break;
		}

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(1);

	}

}
