import org.apfloat.Apfloat;
import org.apfloat.Apint;
import org.apfloat.ApintMath;

public class PiThread extends Thread {

    public Long end;
    public int myLabel;
    public int chSdSub;
    public Long prInt;
    public int chSrSub;
    public int prd;
    public int suc;
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
    private static ProgramParams programParams;
    
    public PiThread(int numLabel, ProgramParams programParams, int chunkLength) {
    	PiThread.programParams = programParams;
        myLabel = numLabel+1;
        ChSz    = chunkLength;
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
        end     = programParams.getPrecisionValue()/7L +2;
        ChSzAp  = new Apint(ChSz);
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
        }
        Utility.printLogsMessage("Thread: " + myLabel + " finished" , programParams);
    }

    public boolean update() {
        Data.lock.lock();
        // handle data passed to you;
//        if(Data.last==myLabel && Data.pInt<end){
//            Data.pInt +=ChSz;
//            Data.progress.add(ChSzAp);
//            Data.lock.release();
//            return false;
//        }
//        System.out.println("===="+myLabel);
//        System.out.println(result.toString(true));
        result = result.add(Data.result[myLabel].multiply(tmpNum).divide(tmpDen));
        Data.result     [myLabel] = zero;
        
        tmpNum = tmpNum.multiply(Data.numerator[myLabel]);   
        Data.numerator  [myLabel] = one;

        tmpDen = tmpDen.multiply(Data.denominator[myLabel]);
        Data.denominator[myLabel] = one;
       
//        if( Data.succesor[Data.predecesor[myLabel]] != myLabel&& Data.succesor[Data.predecesor[myLabel]] != Data.succesor.length-1 ){
//            System.out.println("F\nU\nC\nK\n!\n!\n!\n!\n!");
//        }
// update relationships    
        prd   = Data.predecesor[myLabel];    
        Data.predecesor[myLabel] = Data.last;
        Data.predecesor[Data.succesor[myLabel]] = prd;

        suc   = Data.succesor[myLabel];
        Data.succesor[Data.last] = myLabel;
        Data.succesor[myLabel] = myLabel;
        Data.succesor[prd]   = suc;
        Data.last = myLabel;

        Data.result[prd]       =  Data.result[prd].add((result.multiply(Data.numerator[prd]))
                                                          .divide(Data.denominator[prd]));
        Data.numerator  [prd] =  Data.numerator[prd].multiply(tmpNum) ;                        
        Data.denominator[prd] = Data.denominator[prd].multiply(tmpDen) ;

        curr         = Data.progress;
        Data.progress = curr.add(ChSzAp);
        Data.pInt     = Data.pInt + ChSz;
        
        Data.lock.unlock();
        return Data.pInt>end+ChSz;
    }

}
