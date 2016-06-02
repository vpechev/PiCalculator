import javax.swing.JTextArea;

public class ProgramParams {
	private int threadsCount;
	private int precisionValue;
	private boolean isQuiet;
	private String outputFileName;
	private JTextArea resultField;
	private JTextArea logsField;
	
	public ProgramParams(final int threadsCount, final int precisionValue, final  boolean isQuiet, final String fileName, final JTextArea resultField, final JTextArea logsField) {
		this.threadsCount = threadsCount;
		this.precisionValue = precisionValue;
		this.isQuiet = isQuiet;
		this.outputFileName = (fileName != null && fileName != "") ? fileName + ".txt" : "result.txt";
		this.resultField = resultField;
		this.logsField = logsField;
	}

	public int getThreadsCount() {
		return threadsCount;
	}

	public int getPrecisionValue() {
		return precisionValue;
	}

	public boolean isQuiet() {
		return isQuiet;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public JTextArea getResultField() {
		return resultField;
	}

	public JTextArea getLogsField() {
		return logsField;
	}
	
}
