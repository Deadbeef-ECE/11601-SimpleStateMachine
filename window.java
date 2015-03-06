public class window {
	
	String head;
	String body;
	String tail;
	int printlag = 0;
	
	public window(String a, String b, String c){
		this.head = a;
		this.body = b;
		this.tail = c;
		//System.out.println(this.head + this.body + this.tail);
		//System.out.print(getLetterState() + " ");
	}
	
//	public void updateWindow(String newString){
//		this.head = this.body;
//		this.body = this.tail;
//		this.tail = newString;
//		System.out.println(getLetterState());
//	}
	
	public String getLetterState(){
		//System.out.println(this.head + this.body + this.tail);

		if(this.head.equals("P") && this.body.equals("P") && this.tail.equals("P"))
			return "R";
		if(this.head.equals("B") && this.body.equals("B") && this.tail.equals("B"))
			return "R";
		
		if(this.head.equals("P") && this.body.equals("B") && this.tail.equals("P"))
			return "C";
		if(this.head.equals("B") && this.body.equals("P") && this.tail.equals("B"))
			return "C";
		
		return "A";
	}
	
	public void printLetterState(){
		printlag++;
		if(printlag % 3 == 0)
			System.out.println("");
		System.out.print(getLetterState()+" ");
		
	}
}
