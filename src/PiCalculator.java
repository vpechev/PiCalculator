import java.util.concurrent.locks.ReentrantLock;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;


public class PiCalculator {
	public static void initializeData(int numThreads, int chunkLength, Long prec) {
        Data.lock         = new ReentrantLock();
        Data.denominator  =  new Apint[10*numThreads+2];
        Data.numerator    =  new Apint[10*numThreads+2];
        Data.result        =  new Apfloat[10*numThreads+2];
        Data.denominator2  =  new Apint[11];
        Data.numerator2    =  new Apint[11];
        Data.result2        =  new Apfloat[11];
        Data.passed = new int[11*2];
        for(int i=0;i<Data.denominator.length;i++){
            Data.denominator  [i]= new Apint(1);
            Data.numerator    [i]=new Apint(1);
            Data.result       [i]= new Apfloat(0, prec+5);
        }
        for(int i=0;i<Data.denominator2.length;i++){
            Data.denominator2  [i]= new Apint(1);
            Data.numerator2    [i]=new Apint(1);
            Data.result2       [i]= new Apfloat(0, prec+5);
            Data.passed[2*i] = numThreads;
            Data.passed[2*i+1] = 2*numThreads;
            
        }
        Data.passed[0]  -=numThreads;
        Data.passed[(prec.intValue()+10)/(chunkLength*7*numThreads)*2+1] += ((prec.intValue()+10)%(numThreads*chunkLength*7))/7 +1;
       // System.out.println((prec.intValue()+10)/(chunkLength*7*numThreads));
       // System.out.println( ((prec.intValue()+10)%(numThreads*chunkLength*7))/7 +1);
        Data.result         [0]= new Apfloat(1103, prec+5);
        Data.denominator   [0]= new Apint(1);
        Data.numerator     [0]=new Apint(1);
        Data.progress        = new Apint(1);
        Data.pInt            = 1;
        Data.result2         [0]= new Apfloat(1103, prec+5);
        Data.denominator2   [0]= new Apint(1);
        Data.numerator2     [0]=new Apint(1);
        Data.pInt2             = 1;
    }
	
	public static Apfloat calculatePi(ProgramParams programParams){
		int numThreads = programParams.getThreadsCount();
	      
        Long precision = 1000L;
        Double   a = precision/(7.0*10*numThreads)+1L;
        Long chunkLengthtmp = a.longValue();
        int chunkLength = chunkLengthtmp.intValue() ;
        PiThread myThreads[] = new PiThread[numThreads];  
        initializeData(numThreads, chunkLength,precision);

        for (int i = 0; i < numThreads; i++) {
            myThreads[i] = new PiThread(i, programParams, chunkLength);
        }
        //long start_time = System.nanoTime();
        for (int i = 0; i < numThreads; i++) {
            myThreads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            while(myThreads[i].isAlive()){}
        }
        Apfloat result  = Data.result2[0];
        Apfloat mult = new Apfloat(8L, precision + 50);
        mult = ApfloatMath.sqrt(mult).divide(new Apfloat(9801L,precision + 50));
        Apint   tmpNum = Apint.ONE;
        Apint   tmpDen = Apint.ONE;
        for(int i=1;i<11;i++){
            tmpNum = Data.numerator2[i-1].multiply(tmpNum);
            tmpDen = Data.denominator2[i-1].multiply(tmpDen);
            result= result.add(Data.result2[i].multiply(tmpNum).divide(tmpDen)); 
        }
        result            =result.multiply(mult);
        //long   end_time   = System.nanoTime();
        // double difference = (end_time - start_time)*1e-6;  
        //System.out.println(result.toString(true));
        //Apfloat pi9 = new Apfloat(Constants.PI_9, precision+5);
        //System.out.println(Apfloat.ONE.divide(result).subtract(pi9).toString());
        //System.out.println(difference);
        return result;
    }
}
