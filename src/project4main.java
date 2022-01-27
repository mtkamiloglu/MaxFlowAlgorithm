import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class project4main {

	public static void main(String args[]) throws FileNotFoundException {

		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));


		int numGreenTrains = Integer.parseInt(in.nextLine().strip());
		String greenTrains="";
		if(numGreenTrains!=0) 
			greenTrains = in.nextLine();


		int numRedTrains = Integer.parseInt(in.nextLine().strip());
		String redTrains = "";
		if(numRedTrains!=0) 
			redTrains = in.nextLine();

		int numGreenDeers = Integer.parseInt(in.nextLine().strip());
		String greenDeers = "";
		if(numGreenDeers!=0)
			greenDeers = in.nextLine();

		int numRedDeers =  Integer.parseInt(in.nextLine().strip());
		String redDeers = "";
		if(numRedDeers!=0)
			redDeers = in.nextLine();

		int numOfBags =  Integer.parseInt(in.nextLine());
	
		int totalNodes = numGreenTrains+numRedTrains+numGreenDeers+numRedDeers+numOfBags+2;
		int s = totalNodes - 1;
		int t = totalNodes - 2;
		
		MaxFlowAlgo noel = new MaxFlowAlgo(totalNodes, s, t);

		//Creating green region trains
		if(!greenTrains.equals("")) {
			String[] datas = greenTrains.split(" ");
			for(int i=0; i<numGreenTrains; i++) {
				int capacity = Integer.parseInt(datas[i]);
				if(capacity!=0)
					noel.addEdge(i, t, capacity);
			}
		}
		
		

		//Creating red region trains
		if(!redTrains.equals("")) {
			String[] datas = redTrains.split(" ");
			for(int i=0; i<numRedTrains; i++) {
				int capacity = Integer.parseInt(datas[i]);
				if(capacity!=0)	
					noel.addEdge(i+numGreenTrains, t, capacity);
			}
		}
		

		//Creating green region deers
		if(!greenDeers.equals("")) {
			String[] datas = greenDeers.split(" ");
			for(int i=0; i<numGreenDeers; i++) {
				int capacity = Integer.parseInt(datas[i]);
				if(capacity!=0)
					noel.addEdge(i+numGreenTrains+numRedTrains, t, capacity);
			}
		}

		
		//Creating red region deers
		if(!redDeers.equals("")) {
			String[] datas = redDeers.split(" ");
			for(int i=0; i<numRedDeers; i++) {
				int capacity = Integer.parseInt(datas[i]);
				if(capacity!=0)
					noel.addEdge(i+numGreenTrains+numRedTrains+numGreenDeers, t, capacity);
			}
		}
		
		int numOfVehicles = numGreenTrains+numRedTrains+numGreenDeers+numRedDeers; 

		int firstGT = -1;
		
		if(numGreenTrains!=0) {
			firstGT=0;				  
		}
		
		int lastGT  = numGreenTrains-1;
		int firstRT = numGreenTrains;
		int lastRT  = numGreenTrains+numRedTrains-1;
		int firstGD = numGreenTrains+numRedTrains;
		int lastGD  = numGreenTrains+numRedTrains+numGreenDeers-1;
		int firstRD = numGreenTrains+numRedTrains+numGreenDeers;
		int lastRD  = numGreenTrains+numRedTrains+numGreenDeers+numRedDeers-1;
		
		int totalGifts=0;

		for(int i=0; i<numOfBags; i++) {
			String type = in.next(); int gifts = in.nextInt();
			int currNum = i+numOfVehicles;
			noel.addEdge(s, currNum, gifts);
			totalGifts += gifts;

			if(gifts==0)
				continue;

			if(type.equals("a")) {						//1 capacity edge to each vehicle
				for(int j=0; j<numOfVehicles; j++)
					noel.addEdge(currNum, j, 1);	
			}

			if(type.equals("b")) {						//To only green regions
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<lastGT+1;j++) 
						noel.addEdge(currNum, j, gifts);
				}
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("c")) {						//To only red regions
				if(numRedTrains!=0) {
					for(int j=firstRT; j<lastRT+1;j++)
						noel.addEdge(currNum, j, gifts);
				}
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++) 
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("d")) {						//With only train
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<lastGT+1;j++)
						noel.addEdge(currNum, j, gifts);
				}
				if(numRedTrains!=0) {
					for(int j=firstRT; j<lastRT+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("e")) {						//With only deer
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1;j++)
						noel.addEdge(currNum, j, gifts);
				}
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("ab")) {						//Green regions with 1 capacity
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<lastGT+1;j++)
						noel.addEdge(currNum, j, 1);
				}
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("ac")) {						//Red regions with 1 capacity
				if(numRedTrains!=0) {
					for(int j=firstRT; j<firstGD;j++)
						noel.addEdge(currNum, j, 1);
				}
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("ad")) {						//Only train with 1 capacity
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<firstRT;j++)
						noel.addEdge(currNum, j, 1);
				}
				if(numRedTrains!=0) {
					for(int j=firstRT; j<lastRT+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("ae")) {						//Only deers with 1 capacity
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1;j++)
						noel.addEdge(currNum, j, 1);
				}
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("bd")) {						//With only green trains
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<lastGT+1;j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("be")) {						//With only green deers
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("cd")) {						//Only with red trains
				if(numRedTrains!=0) {
					for(int j=firstRT; j<lastRT+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("ce")) {						//With only red deers
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++)
						noel.addEdge(currNum, j, gifts);
				}
			}

			if(type.equals("abd")) {					//Green trains with 1 capacity
				if(numGreenTrains!=0) {
					for(int j=firstGT; j<lastGT+1;j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("abe")) {					//Green deers with 1 capacity
				if(numGreenDeers!=0) {
					for(int j=firstGD; j<lastGD+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("acd")) {					//Red trains with 1 capacity
				if(numRedTrains!=0) {
					for(int j=firstRT; j<lastRT+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}

			if(type.equals("ace")) {					//Red deers with 1 capacity
				if(numRedDeers!=0) {
					for(int j=firstRD; j<lastRD+1; j++)
						noel.addEdge(currNum, j, 1);
				}
			}
		}

		out.print(totalGifts - noel.getMaxFlow());

	}
}
