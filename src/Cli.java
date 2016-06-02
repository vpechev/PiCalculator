import java.util.logging.Level;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
	private String[] args = null;
	private Options options = new Options();
	public static ProgramParams programParams;	

	public Cli(String[] args) {

		this.args = args;

		options.addOption("h", "help", false, "show help.");
		options.addOption("v", "var", true, "Here you can set parameter .");

	}

	public ProgramParams parse() {
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
			int threadsCount = 1;
			int precisionValue = 10;
			boolean isQuiet = false;
			String fileName = null;
			
			if (cmd.hasOption("t")) {
				threadsCount = Integer.parseInt(cmd.getOptionValue("t"));
			} else if (cmd.hasOption("tasks")) {
				threadsCount = Integer.parseInt(cmd.getOptionValue("t"));
			} 
			
			if (cmd.hasOption("p")) {
				precisionValue = Integer.parseInt(cmd.getOptionValue("p"));
			} 
			
			if (cmd.hasOption("q")) {
				isQuiet = Boolean.parseBoolean(cmd.getOptionValue("q"));
			} 
			
			if (cmd.hasOption("o")) {
				fileName = cmd.getOptionValue("o");
			}
			
			return new ProgramParams(threadsCount, precisionValue, isQuiet, fileName, null, null);
			
		} catch (ParseException e) {
			System.out.println("Problem while parsing arguments");
		}
		return null;
	}
}
