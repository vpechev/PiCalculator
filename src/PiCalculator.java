import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.JTextArea;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;

import EDU.oswego.cs.dl.util.concurrent.Mutex;


public class PiCalculator {
	public static void initializeData(int numThreads, int chunkLength, int prec) {
		Data.lock         = new Mutex();
		Data.denominator  =  new Apint[numThreads+2];
		Data.numerator    =  new Apint[numThreads+2];
		Data.predecesor    =  new int[numThreads+2];
		Data.succesor     =  new   int[numThreads+2];
		Data.result        =  new Apfloat[numThreads+2];
		for(int i=0;i<=numThreads+1;i++){
			Data.denominator  [i]= new Apint(1);
			Data.numerator    [i]=new Apint(1);
			Data.predecesor   [i]= 0;
			Data.succesor     [i]= numThreads+1;
			Data.result       [i]= new Apfloat(0, prec+5);
		}
		Data.result         [0]= new Apfloat(1103, prec+5);
		Data.denominator   [0]= new Apint(1);
		Data.numerator     [0]=new Apint(1);
		Data.progress        = new Apint(1);
		Data.pInt            = 1L;
		Data.last            = 0;
	}
	
	public static Apfloat calculatePi(ProgramParams programParams){
	      Double   a = Math.log10(programParams.getPrecisionValue()/7)*2;
	      Long chunkLengthtmp = a.longValue();
	      int chunkLength = chunkLengthtmp.intValue() ;
	      
	      chunkLength = 15;
	      PiThread myThreads[] = new PiThread[programParams.getThreadsCount()];  
	      initializeData(programParams.getThreadsCount(), chunkLength, programParams.getPrecisionValue());

	      for (int i = 0; i < programParams.getThreadsCount(); i++) {
	          myThreads[i] = new PiThread(i, programParams,chunkLength);
	      }
	      long start_time = System.nanoTime();
	      for (int i = 0; i < programParams.getThreadsCount(); i++) {
	    	  myThreads[i].start();
	      }
	      for (int i = 0; i < programParams.getThreadsCount(); i++) {
	          while(myThreads[i].isAlive()){}
	      }
	      Apfloat result ;
	      Apfloat mult = new Apfloat(8L, programParams.getPrecisionValue() + 50);
	      mult = ApfloatMath.sqrt(mult).divide(new Apfloat(9801L,programParams.getPrecisionValue() + 50));
	      result            = Data.result[0].multiply(mult);
	      long   end_time   = System.nanoTime();
	      double difference = (end_time - start_time)*1e-6;  
	      System.out.println("result: " + result.toString(true));
	      
	      Apfloat pi9 = new Apfloat(Constants.PI_9, programParams.getPrecisionValue());
	      System.out.println("result multiplied by pi " + result.multiply(pi9).toString(true));
	      System.out.println("diff: " + difference);
	      
	      return result;
    }
}
