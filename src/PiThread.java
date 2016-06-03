import org.apfloat.Apfloat;
import org.apfloat.Apint;
import org.apfloat.ApintMath;

public class PiThread extends Thread {
	public int prInt;
    public int numThreads;
    public int ind;
    public Apfloat  result;
    private Apint tmpNum, tmpDen;
    private final Apint one;
    private final Apint zero;
    private final Apfloat One;
    private final Apint two;
    private final Apint three;
    private final Apint four;
    private Apint curr;
    private Apint curr4;
    private final Apint c26390;
    private final Apint c1103;
    private final Apint c396_4;
    private final int   ChSz;
    private final Apint ChSzAp;
    private final int myLabel;
    private static ProgramParams programParams;
    
    public PiThread(int numLabel, ProgramParams programParams, int chunkLength) {
    	PiThread.programParams = programParams;
    	myLabel = numLabel;
    	ChSz    = chunkLength;
        prInt   = programParams.getPrecisionValue();
        this.numThreads = programParams.getThreadsCount();
        zero    = new Apint(0L);
        one     = new Apint( 1L);
        two     = new Apint( 2L);
        three   = new Apint( 3L);
        four    = new Apint( 4L);
        result  = new Apfloat(0L,programParams.getPrecisionValue()+50);
        One     = new Apfloat(1L,programParams.getPrecisionValue()+50 );
        c1103   = new Apint(1103);
        tmpNum  = one;
        tmpDen  = one;
        c26390  = new Apint(26390);
        c396_4  = new Apint(396*396*396*99L);
        ChSzAp  = new Apint(ChSz);
        ind =1;
    }
    
    @Override
    public void run() {
    	Utility.printLogsMessage("Thread: " + myLabel + " started", programParams);
    	while (!update()) {
            tmpNum = one;
            tmpDen = one;
            result = zero;
            for ( int i = 0; i < ChSz; i++) {
                curr4  = curr.multiply(four);
                tmpNum = tmpNum.multiply(curr)
                    .multiply(curr4.subtract(one))
                    .multiply(curr4.subtract(two))
                    .multiply(curr4.subtract(three));
                tmpDen = tmpDen.multiply(c396_4).multiply(ApintMath.pow(curr,4));   
                result =  result.add((One.multiply(curr.multiply(c26390).add(c1103)).multiply(tmpNum)).divide(tmpDen));
            
                curr   =  curr.add(one);
            }
        Data.result     [ind] = result;
        Data.numerator  [ind] = tmpNum;
        Data.denominator[ind] = tmpDen;
        //Data.passed[(ind-1)/numThreads]++;
        }
    	
    	while(!update2()){
            tmpNum = one;
            tmpDen = one;
            result = zero;               
            int start = (ind-1) * numThreads+1;
            for ( int i = 0; i < numThreads; i++) {
                tmpNum = tmpNum.multiply(Data.numerator[start-1]);   
                tmpDen = tmpDen.multiply(Data.denominator[start-1]);
                result = result.add(Data.result[start].multiply(tmpNum).divide(tmpDen));   start++;
            }
            Data.result2     [ind] = result;
            Data.numerator2  [ind] = tmpNum;
            Data.denominator2[ind] = tmpDen;
        }
    	
        Utility.printLogsMessage("Thread: " + myLabel + " finished" , programParams);
    }

    public boolean update() {
        Data.lock.lock();
        curr         = Data.progress;
        Data.progress = curr.add(ChSzAp);  
        Data.passed[2*((ind-1)/numThreads)]++;
        ind = Data.pInt;   
        Data.pInt     = Data.pInt+1;
        Data.lock.unlock();
        return ChSz*(ind-1)*7>prInt+10||ind>(Data.denominator.length-1);
    }

    public boolean update2(){
        boolean start;
            
        Data.lock.lock();
        ind = Data.pInt2;
        start = Data.passed[(ind-1)*2]!= Data.passed[(ind-1)*2+1];
        Data.pInt2 = Data.pInt2+(Data.passed[(ind-1)*2]/Data.passed[(ind-1)*2+1]);

        Data.lock.unlock();
        return start||ind>=Data.denominator2.length;//||Data.numerator[(ind-1) * numThreads+1].equals(one);
    }

}
