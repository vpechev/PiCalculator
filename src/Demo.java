import javax.swing.SwingUtilities;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;

import EDU.oswego.cs.dl.util.concurrent.Mutex;

public class Demo {

    public static void main(String[] args){
    	boolean quietMode;
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	ClassFrame g = new ClassFrame();
                g.setLocation(10, 10);
                g.setSize(500, 500);
                g.setVisible(true);
            }
        });

    }

   

}
