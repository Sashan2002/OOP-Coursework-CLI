package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Logger class provides functionality to log messages to a console and a file.
 * It includes methods to append messages to a log file and clear the log file.
 */
public class Logger {
    /**
     * The path to the log file where messages will be written.
     */
    private final static String LOG_FILE = "resources/log.txt";

    /**
     * Logs a message with a timestamp to both the console and the log file.
     *
     * @param message the message to be logged
     */
    public static void log(String message) {
        // Add a timestamp to the message
        String timeStampMessage = LocalDateTime.now() + " : " + message;

        // Print the message to the console
        System.out.println(timeStampMessage);

        // Write the message to the log file, appending to the file
        writeToFile(timeStampMessage, true);
    }

    /**
     * Clears the log file by overwriting it with an empty string.
     * This method is synchronized to ensure thread safety when clearing the log.
     */
    public static synchronized void clearLogFile() {
        // Overwrite the log file with an empty string
        writeToFile("", false); // Overwrite file
    }

    /**
     * Writes a message to the log file. If the file or its parent directories do not exist, they are created.
     *
     * @param message the message to write to the log file
     * @param append  true to append to the file, false to overwrite the file
     */
    private static void writeToFile(String message, boolean append) {
        // Create a File object representing the log file
        File file = new File(LOG_FILE); // Log file
        try {
            // Check if the file exists; if not, create it along with any necessary parent directories
            if (!file.exists()) { // Create file if not exists
                file.getParentFile().mkdirs(); // Create parent directories
                file.createNewFile(); // Create the file
            }

            // Use a BufferedWriter to write to the file, appending or overwriting based on the append flag
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
                if (!message.isEmpty()) { // Only write if the message is not empty
                    bw.write(message); // Write the message to the file
                    bw.newLine(); // Add a new line after the message
                }
            }
        } catch (IOException e) {
            // Print an error message to the console if writing to the file fails
            System.err.println("Failed to write to log file: " + e.getMessage()); // Error message
        }
    }
}


