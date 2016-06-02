import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {
	public static void printResultMessage(String msg, ProgramParams programParams){
		System.out.println(msg);
		if(!programParams.isQuiet()){
			programParams.getResultField().append("\n" + msg);
		}
	}
	
	public static void printLogsMessage(String msg, ProgramParams programParams){
		if(!programParams.isQuiet()){
			System.out.println(msg);
			programParams.getLogsField().append("\n" + msg);
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
		
		String resultMsg =  "1/Pi = " + result;
		String timeMsg = "Total calculating time: " + totalTime;
		Utility.printResultMessage(resultMsg, programParams);
		Utility.printResultMessage(timeMsg, programParams);
		
		
		Utility.writeToFile(programParams.getOutputFileName(), result);
    }
}
