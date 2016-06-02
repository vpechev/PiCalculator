import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {
	public static void printMessage(String msg, boolean isQuiet){
		if(!isQuiet)
			System.out.println(msg);
	}
	
	public static void writeToFile(String fileName, String value){
    	try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
    		fileWriter.append(value);
        } catch (FileNotFoundException fnf) {
            System.out.println(fileName + " was not found.");
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
