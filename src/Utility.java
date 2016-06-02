import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class Utility {
	public static void printMessage(String msg, ProgramParams programParams){
		if(!programParams.isQuiet()){
			System.out.println(msg);
			programParams.getResultField().setText(msg);
		}
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
	
	public static void computeMeasureWriteToFilePi(ProgramParams programParams){
    	long t1 = System.currentTimeMillis();
		String result = PiCalculator.calculatePi(programParams).toString(true);
		long t2 = System.currentTimeMillis();
		long totalTime = t2 - t1;
		String timeMsg = "Total calculating time: " + totalTime;
		Utility.printMessage(timeMsg, programParams);
		
		Utility.writeToFile(programParams.getOutputFileName(), result);
    }
}
