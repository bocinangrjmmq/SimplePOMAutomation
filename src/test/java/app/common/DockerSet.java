package app.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DockerSet {

	private final Logger log = LogManager.getLogger(DockerSet.class.getName());
	Runtime runtime = Runtime.getRuntime();

	/**
	 * 
	 * Runs the commands to create a new Docker instance
	 * 
	 * @param file
	 * @param file1
	 * @param statusOP
	 * @param line
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void starBat(String file, String file1, String statusOP, String line)
			throws IOException, InterruptedException {
		runtime.exec(file);
		Thread.sleep(2000);

		runtime.exec(file1);
		boolean flag = false;

		Thread.sleep(2000);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		long stopnow = cal.getTimeInMillis();
		//Thread.sleep(5000);

		outerloop:
			while (System.currentTimeMillis() < stopnow) {

				BufferedReader reader = new BufferedReader(new FileReader(statusOP));
				String currentLine = reader.readLine();
				while (currentLine != null && !flag) {
					if (currentLine.contains(line)) {
						log.info("Docker has started successfully");
						log.info(currentLine);
						System.out.println("Docker has started successfully");
						System.out.println(currentLine);
						flag = currentLine.contains(line);
						break outerloop;
					}

					currentLine = reader.readLine();
				}
				reader.close();
			}
		Thread.sleep(5000);
	}


	/**
	 * 
	 * Runs the commands to close the active Docker instance
	 * 
	 * @param file
	 * @param file1
	 * @param file2
	 * @param statusOP
	 * @param line
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void stopBat(String file, String file1,String file2, String statusOP, String line)
			throws IOException, InterruptedException {
		runtime.exec(file);
		Thread.sleep(2000);
		
		runtime.exec(file1);
		boolean flag = false;

		Thread.sleep(2000);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 45);
		long stopnow = cal.getTimeInMillis();

		outerloop:
			while (System.currentTimeMillis() < stopnow) {

				BufferedReader reader = new BufferedReader(new FileReader(statusOP));
				String currentLine = reader.readLine();
				while (currentLine != null && !flag) {

					if (currentLine.contains(line)) {
						log.info("Docker has stopped successfully");
						log.info(currentLine);
						System.out.println("Docker has stopped successfully");
						System.out.println(currentLine);
						flag = currentLine.contains(line);
						break outerloop;
					}

					currentLine = reader.readLine();
				}
				reader.close();
			}
		runtime.exec(file2);
		Thread.sleep(10000);
	}
	
	/**
	 * 
	 * This method set the Selenium Grid in docker
	 * 
	 * @param file
	 * @param statusOP
	 * @param line
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void starGridBat(String file, String statusOP, String line)
			throws IOException, InterruptedException {
		runtime.exec(file);
		boolean flag = false;
		
		Thread.sleep(3000);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 45);
		long stopnow = cal.getTimeInMillis();
		Thread.sleep(5000);
		
		outerloop:
		while (System.currentTimeMillis() < stopnow) {
			
			BufferedReader reader = new BufferedReader(new FileReader(statusOP));
			String currentLine = reader.readLine();
			while (currentLine != null && !flag) {
				if (currentLine.contains(line)) {
					log.info("Docker Grid has started successfully");
					log.info(currentLine);
					flag = currentLine.contains(line);
					break outerloop;
				}

				currentLine = reader.readLine();
			}
			reader.close();
		}
		Thread.sleep(5000);
	}
	
	/**
	 * 
	 * This Method Stops the Grid in docker
	 * 
	 * @param file
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void stopGridBatNoLogsGen(String file) throws IOException, InterruptedException {
		runtime.exec(file);
		//Thread.sleep(2000);
		//boolean flag = false;
		
		//Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.SECOND, 45);
		//long stopnow = cal.getTimeInMillis();
		Thread.sleep(25000);
		
	}
}
