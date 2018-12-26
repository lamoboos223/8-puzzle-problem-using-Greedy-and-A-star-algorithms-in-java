import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {


	static Scanner read = new Scanner(System.in);
	public static void main(String[]args) throws IOException {



//		BufferedReader reader = new BufferedReader(new FileReader("numbers.txt"));
//		String number = reader.readLine();
		String number = "102345678";
		int choice ;

		do {
			System.out.println("1- Greedy\n2- A*\n3- Exit");
			choice = read.nextInt();
			switch (choice) {
				case 1:
					System.out.println("\n\ngreedy");
					node puzzle1 = new node(number, null);
					puzzle1.setKey(CalculateWrongTiles(number));
					greedy(puzzle1);
					break;
				case 2:
					System.out.println("\n\n\nA* ");
					node puzzle = new node(number, null);
					puzzle.setKey(countF(puzzle));
					A(puzzle);

					break;

				case 3:
					int n = 5;
					for (int i = -3*n/2; i <= n; i++) {
						for (int j = -3 * n / 2; j <= 3 * n / 2; j++) {

							// inside either diamond or two circles
							if ((Math.abs(i) + Math.abs(j) < n)
									|| ((-n / 2 - i) * (-n / 2 - i) + (n / 2 - j) * (n / 2 - j) <= n * n / 2)
									|| ((-n / 2 - i) * (-n / 2 - i) + (-n / 2 - j) * (-n / 2 - j) <= n * n / 2)) {
								System.out.print("* ");
							} else {
								System.out.print(". ");
							}
						}
						System.out.println();
					}
					System.out.println("good bye :)");
					System.exit(1);

			}

		} while (true);
	}




	///////A*///////
	public static void A(node n) {
		double startTime=System.currentTimeMillis();
		PriorityQueue <node> Afringe = new PriorityQueue<node>();
		Afringe.add(n);

		LinkedList<node> closedSet = new LinkedList<node>();
		String future;
		node toExpand;
		node futureDirection;


		// swapping
		do {
			toExpand=Afringe.poll();
			if (closedSet.contains(toExpand) )
				continue;
			closedSet.add(toExpand);
			if (UpAvailablity(toExpand)){
				future=seeTheFutureUp(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(countF(futureDirection)+CalculateWrongTiles (futureDirection.getString()));
				Afringe.add( futureDirection);}
			if (RightAvailablity(toExpand)){
				future=seeTheFutureRight(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(countF(futureDirection)+CalculateWrongTiles (futureDirection.getString()));
				Afringe.add(futureDirection); }
			if (DownAvailablity(toExpand)){
				future=seeTheFutureDown(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(countF(futureDirection)+CalculateWrongTiles (futureDirection.getString()));
				Afringe.add(futureDirection); }
			if (LeftAvailablity(toExpand)){
				future=seeTheFutureLeft(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(countF(futureDirection)+CalculateWrongTiles (futureDirection.getString()));
				Afringe.add(futureDirection); }
		} while (!(toExpand.getString().equals("123456780")));

		double endTime=System.currentTimeMillis();
		System.out.println("\nThe sequence of states: ");
		printNodesPath(toExpand);
		System.out.println("The cost of the solution: "+pathCost(toExpand));
		double time=endTime-startTime;
		System.out.println("The time it consumed (in ms): " + time+"\tThe time it consumed (in sec): "+(time/1000));
	}// end of A




    ////greedy////
	public static void greedy(node n){
		double startTime=System.currentTimeMillis();
		PriorityQueue <node> greadyfringe = new PriorityQueue<node>();
		greadyfringe.add(n);
		LinkedList<node> closedSet = new LinkedList<node>();
		node toExpand;
		String future ;
		node futureDirection;
		do {
			toExpand=greadyfringe.poll();
			if (closedSet.contains(toExpand) )
				continue;
			else
				closedSet.add(toExpand);

			if (UpAvailablity(toExpand)){
				future=seeTheFutureUp(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(CalculateWrongTiles(futureDirection.getString()));
				greadyfringe.add( futureDirection);}
			if (RightAvailablity(toExpand)){
				future=seeTheFutureRight(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(CalculateWrongTiles(futureDirection.getString()));
				greadyfringe.add(futureDirection); }
			if (DownAvailablity(toExpand)){
				future=seeTheFutureDown(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(CalculateWrongTiles(futureDirection.getString()));
				greadyfringe.add(futureDirection);}
			if (LeftAvailablity(toExpand)){
				future=seeTheFutureLeft(toExpand.getString());
				futureDirection= new node (future,toExpand);
				futureDirection.setKey(CalculateWrongTiles(futureDirection.getString()));
				greadyfringe.add(futureDirection); }
		} while (!(toExpand.getString().equals("123456780")));
		double endTime=System.currentTimeMillis();
		System.out.println("\nThe sequence of states: ");
		printNodesPath(toExpand);
		System.out.println("The cost of the solution: "+pathCost(toExpand));
		double time=endTime-startTime;
		System.out.println("The time it consumed (in ms): " + time+"\tThe time it consumed (in sec): "+(time/1000));
	}







	public static String seeTheFutureLeft(String str){
		int indexZero = str.indexOf('0');
		char b40 = str.charAt(indexZero-1);
		str = str.substring(0,indexZero-1)+'0'+b40+str.substring(indexZero+1);
		return str; }

	public static String seeTheFutureDown(String str){
		int indexZero = str.indexOf('0');
		char under0 = str.charAt(indexZero+3);
		int indexUnder = str.indexOf(under0);
		str = str.substring(0,indexZero)+under0+str.substring(indexZero+1, indexUnder)+'0'+str.substring(indexUnder+1);
		return str; }

	public static String seeTheFutureRight(String str){
		int indexZero = str.indexOf('0');
		char after0 = str.charAt(indexZero+1);
		str = str.substring(0,indexZero)+after0+'0'+str.substring(indexZero+2);
		return str; }

	public static String seeTheFutureUp(String str){
		int indexZero = str.indexOf('0');
		char over0 = str.charAt(indexZero-3);
		int indexOver = str.indexOf(over0);
		str= str.substring(0,indexOver)+'0'+str.substring(indexOver+1, indexZero)+over0+str.substring(indexZero+1);
		return str; }



	///////////////////////////check availablity to move ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean LeftAvailablity (node n){
		String str = n.getString();
		if (str.charAt(0)=='0' || str.charAt(3)=='0' ||str.charAt(6)=='0')
			return false;
		return true; } // end of LeftAvailablity

	public static boolean RightAvailablity (node n){
		String str = n.getString();
		if (str.charAt(2)=='0' || str.charAt(5)=='0' ||str.charAt(8)=='0')
			return false;
		return true; } // end of RightAvailablity

	public static boolean DownAvailablity (node n){
		String str = n.getString();
		if (str.charAt(6)=='0' || str.charAt(7)=='0' ||str.charAt(8)=='0')
			return false;
		return true; } // end of DownAvailablity

	public static boolean UpAvailablity (node n){
		String str = n.getString();
		if (str.charAt(0)=='0' || str.charAt(1)=='0' ||str.charAt(2)=='0')
			return false;
		return true; } // end of UpAvailablity




/*
	///////////////////////////Sum of manhaten distance ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int CalculateManhaten(String str ){
		int  sum=0 ;
		String[] ManhatenForAll = new String [9];

		ManhatenForAll[0] ="001212323";
		ManhatenForAll[1]= "010121232";
		ManhatenForAll[2] ="021032143";
		ManhatenForAll[3] ="012301212";
		ManhatenForAll[4]= "021210121";
		ManhatenForAll[5]= "032121032";
		ManhatenForAll[6] ="023412301";
		ManhatenForAll[7] ="032321210";
		ManhatenForAll[8] ="043232121";

		for(int i=0 ; i<9 ;i++){
			sum = sum +(Character.getNumericValue( ManhatenForAll[i].charAt( Character.getNumericValue(str.charAt(i))) )) ;
		}

		return sum ;
	}
	*/


	/////////////////////////// CalculateWrongTiles()////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int CalculateWrongTiles(String str){
		int raws=0, cols=0;
		char num;

		for (int i=0; i<str.length(); i++){
			num = str.charAt(i);

			if (  num==('1') || num==('2') || num==('3') )
				if (!(i==0 || i==1 || i==2) )
					raws++;

			if (  num==('4') || num==('5') || num==('6') )
				if (!(i==3 || i==4 || i==5) )
					raws++;

			if (  num==('7') || num==('8') || num==('0') )
				if (!(i==6 || i==7 || i==8) )
					raws++;
		}

		for (int i=0; i<str.length(); i++){
			num = str.charAt(i);

			if (  num==('1') || num==('4') || num==('7') )
				if (!(i==0 || i==3 || i==6) )
					cols++;

			if (  num==('2') || num==('5') || num==('8') )
				if (!(i==1 || i==4 || i==7) )
					cols++;

			if (  num==('3') || num==('6') || num==('0') )
				if (!(i==2 || i==5 || i==8) )
					cols++;
		}


		return raws+cols;
	}
	///////////////////////////printPath(node current )////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////countF////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int countF(node c){
		int 	g=0 ;
		node temp  =c;
		while (temp != null ) {
			g++;
			temp=temp.getparent() ;}

		return g ;}

	///////////////////////////pathCost////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int pathCost(node c){
		int 	g=0 ;
		node temp  =c;
		while (temp != null ) {
			g++;
			temp=temp.getparent() ;}

		return g;}
	///////////////////////////printNodesPath////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void printNodesPath(node current ){
		if (current==null) return ;
		printNodesPath(current.getparent());
		String toExpand=current.getString();
		System.out.println("-------------");
		System.out.printf("| %c | %c | %c |",toExpand.charAt(0), toExpand.charAt(1), toExpand.charAt(2));
		System.out.println("\n-------------");
		System.out.printf("| %c | %c | %c |",toExpand.charAt(3), toExpand.charAt(4), toExpand.charAt(5));
		System.out.println("\n-------------");
		System.out.printf("| %c | %c | %c |",toExpand.charAt(6), toExpand.charAt(7), toExpand.charAt(8));
		System.out.println("\n-------------\n");
		//System.out.print(current.getString()+" . ");
	}


}






class node implements Comparable<node> {
	private int key;
	private String str;
	private node parent;
	public node(String value,node p  ) {
		this.str = value;
		parent=p;
	}



	public boolean equals(Object other){
		if (other != null && other instanceof node){
			if ( str.equals(((node)other).str ))
				return true ;}

		return false ;


	}



	// getters

	public String getString(){
		return str ;
	}

	public node getparent(){
		return parent;
	}
	//setters

	public void setKey(int n ) {
		key=n;
	}

	public int compareTo(node other) {
		if (key<other.key)return  -1 ;
		if (key==other.key)return 0 ;
		else return 1 ;
	}
}






