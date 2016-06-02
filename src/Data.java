import EDU.oswego.cs.dl.util.concurrent.Mutex;
import org.apfloat.Apfloat;
import org.apfloat.Apint;

public class Data {
    public static Apint progress;
    public static Long  pInt;
    public static int  last;
    public static int  predecesor [];
    public static int  succesor   [];
    public static Apint numerator  [];
    public static Apint denominator[];
    public static Apfloat result      [];
    public static Mutex lock;
}
