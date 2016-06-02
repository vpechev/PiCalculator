import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;

import EDU.oswego.cs.dl.util.concurrent.Mutex;


public class PiCalculator {
	public static void initializeData(int numThreads, int chunkLength, Long prec) {
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
	
	public static Apfloat calculatePi(final int numThreads, final long precision){
	      Double   a = Math.log10(precision/7)*2;
	      Long chunkLengthtmp = a.longValue();
	      int chunkLength = chunkLengthtmp.intValue() ;
	      
	      chunkLength = 15;
	      PiThread myThreads[] = new PiThread[numThreads];  
	      initializeData(numThreads, chunkLength,precision);

	      for (int i = 0; i < numThreads; i++) {
	          myThreads[i] = new PiThread(i, precision,chunkLength);
	      }
	      long start_time = System.nanoTime();
	      for (int i = 0; i < numThreads; i++) {
	          myThreads[i].start();
	      }
	      for (int i = 0; i < numThreads; i++) {
	          while(myThreads[i].isAlive()){}
	      }
	      Apfloat result ;
	      Apfloat mult = new Apfloat(8L, precision + 50);
	      mult = ApfloatMath.sqrt(mult).divide(new Apfloat(9801L,precision + 50));
	      result            = Data.result[0].multiply(mult);
	      long   end_time   = System.nanoTime();
	      double difference = (end_time - start_time)*1e-6;  
	      System.out.println("result: " + result.toString(true));
	      
	      Apfloat pi9 = new Apfloat("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317253594081284811174502841027019385211055596446229489549303819644288109756659334461284756482337867831652712019091456485669234603486104543266482133936072602491412737245870066063155881748815209209628292540917153643678925903600113305305488204665213841469519415116094330572703657595919530921861173819326117931051185480744623799627495673518857527248912279381830119491298336733624406566430860213949463952247371907021798609437027705392171762931767523846748184676694051320005681271452635608277857713427577896091736371787214684409012249534301465495853710507922796892589235420199561121290219608640344181598136297747713099605187072113499999983729780499510597317328160963185950244594553469083026425223082533446850352619311881710100031378387528865875332083814206171776691473035982534904287554687311595628638823537875937519577818577805321712268066130019278766111959092164201989",precision);
	      System.out.println("result multiplied by pi " + result.multiply(pi9).toString(true));
	      System.out.println("diff: " + difference);
	      
	      return result;
    }
}
