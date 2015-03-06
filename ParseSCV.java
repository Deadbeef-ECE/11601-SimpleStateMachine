import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ParseSCV {
	static window pre = null;
	static Queue<Integer> queue = new LinkedList<Integer>();
	static Queue<Integer> statequeue = new LinkedList<Integer>();

	static int totalMiss = 0;
	static int totalHit = 0;
	
	static int tempMiss = 0;
	static int tempHit = 0;

	static int MaxMiss = 0;
	static int MaxHit = 0;
	static int round = 0;
	static int roundForAvg = 0;
	static int lineNum = 0;
	
	static int avgMiss = 0;
	static int avgHit = 0;
	
	public static void initArgsForNewLine(){
    	totalMiss = 0;
    	totalHit = 0;
    	
    	tempMiss = 0;
    	tempHit = 0;

    	MaxMiss = 0;
    	MaxHit = 0;
    	round = 0;
    	roundForAvg = 0;
    	
    	avgMiss = 0;
    	avgHit = 0;
	}
	
	
	public static int getMissOrHit(Queue<Integer> q){
		Queue<Integer> temp = q;
		
		int a = temp.poll();
		int b = temp.poll();
		int c = temp.poll();
		round++;
		int ret = 0;
		if((a == b && b == c) || (a != b && b != c)){
			//System.out.print("#Hit  ");
			ret = 1;
			if(tempMiss != 0){
				MaxMiss = Math.max(MaxMiss, tempMiss);
				tempMiss = 0;
			}
			totalHit++;
			tempHit++;
		}
		if((a == b && b != c) || (a != b && b == c)){
			//System.out.print("#Miss ");
			ret = 0;
			if(tempHit != 0){
				MaxHit = Math.max(MaxHit, tempHit);
				tempHit = 0;
			}
			totalMiss++;
			tempMiss++;
		}
		//System.out.print("round: "+ round +"; queue:"+ a + b + c + "; ");
		//System.out.print("totalMiss: "+ totalMiss +"; totalHit: "+ totalHit + " ");
		q.add(a);
		q.add(b);
		q.add(c);
		return ret;
	}
	
	public static void computeAvg(Queue<Integer> sq, int round){
		int a = sq.poll();
		int b = sq.poll();
		int c = sq.poll();
		int thisHit = a + b + c;
		int thisMiss = 3 - thisHit;
		//avgHit = ((round-1) * avgHit + thisHit)/round;
		//avgMiss = ((round-1) * avgMiss + thisMiss)/round;
		avgHit += thisHit;
		avgMiss += thisMiss;
		sq.add(a);
		sq.add(b);
		sq.add(c);	
		//System.out.println();
		//System.out.println("sq(round[" + round +"]): "
		//+a+b+c+" AvgHit:"+ avgHit+"/"+3*round +"; AvgMiss:"+ avgMiss+"/"+3*round+ "; ");
	}
	
	public static void main(String[] args)
    {
        //Input file which needs to be parsed
        String fileToParse = "1m1EU.csv";
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            //Read the file line by line
            while((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
            	initArgsForNewLine();
                lineNum++;
            	String[] tokens = line.split(DELIMITER);
                int len = tokens.length;
                for(int i = 2; i < len; i++)
                {
                    //Print all tokens
                	window w = new window(tokens[i-2], tokens[i-1], tokens[i]);
                	//w.printLetterState();
                	if(pre != null && pre.getLetterState() != w.getLetterState()){
                		//System.out.print("Off ");
                		if(queue.size() == 3) queue.poll();
                		queue.add(0);
                	}else if(pre != null && pre.getLetterState() == w.getLetterState()){
                		//System.out.print("On ");
                		if(queue.size() == 3) queue.poll();
                		queue.add(1);
                	}
                	//System.out.println();
             
                	if(queue.size() == 3) {
                		int state = getMissOrHit(queue);
                		if(statequeue.size() == 3) statequeue.poll();
                		statequeue.add(state);
                		if(statequeue.size() == 3){
                			roundForAvg++;
                			computeAvg(statequeue, roundForAvg);
                		}
                	}
                	pre = w;
                }
                //System.out.println();
                System.out.println("********** line: "+ lineNum + " *********");
                System.out.println("* Total hits: "+totalHit +";");
                System.out.println("* Total miss: "+totalMiss +";");
                System.out.println("* Avg hits: "+ avgHit +"/"+ 3*roundForAvg +";");
                System.out.println("* Avg miss: "+ avgMiss +"/"+ 3*roundForAvg +";");
                System.out.println("* Longest chain of hits: "+MaxHit +";");
                System.out.println("* Longest chain of miss: "+MaxMiss +";");
                System.out.println("****************************");
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
