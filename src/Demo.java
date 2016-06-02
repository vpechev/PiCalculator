import javax.swing.SwingUtilities;

public class Demo {
	
	
    public static void main(String[] args){
    	Cli cli = new Cli(args);
    	ProgramParams programParams = cli.parse();

    	
    	if(programParams.isQuiet()){
    		Utility.computeMeasureWriteToFilePi(programParams);
    	} else {
    		SwingUtilities.invokeLater(new Runnable() {
    			
    			@Override
    			public void run() {
    				ClassFrame g = new ClassFrame(programParams.isQuiet());
    				g.setLocation(10, 10);
    				g.setSize(500, 500);
    				g.setVisible(true);
    			}
    		});
    	}

    	
    	
    	
    }
}
