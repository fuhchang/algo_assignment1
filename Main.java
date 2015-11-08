import java.io.*;
import java.util.*;
/**
 program to find shortest path using Dijkstra's algorithm
 */
public class Main {
	static int firstLineIndex = 0;
	static int lastLineIndex = 0;
	static int currentVertex = 0;
	static ArrayList <Vertex> vertexArray = new ArrayList<Vertex>();;
	static ArrayList<Integer> VisitedList = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		//ArrayList<Integer> unvisited = new ArrayList<Integer>();
		int size =0;

		ArrayList <String> strArray = new ArrayList();
		long start = System.currentTimeMillis();
		String inputFileName = args[0]; // input file name
		
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data here
		while(in.hasNext()){
			if(size ==0){
				size = Integer.parseInt(in.nextLine());
			}
			strArray.add(in.nextLine());
		}
		String[] startend = strArray.get(strArray.size()-1).split(" ");
		firstLineIndex = Integer.parseInt(startend[0]);
		currentVertex = firstLineIndex;
		lastLineIndex = Integer.parseInt(startend[1]);
		strArray.remove(strArray.size()-1);
		for(int i=0;i<strArray.size();i++){
			String[] strValue = strArray.get(i).split(" ");
			Vertex vertex = null;
			//unvisited.add(i);
			vertex = new Vertex(i);
			for(int a=0;a<strValue.length;a++){
				if(Integer.parseInt(strValue[a]) != 0){
					vertex.addToAdjList(a,Integer.parseInt(strValue[a]));
				}
			}
			vertexArray.add(vertex);
		}
		reader.close();
		for(int i=0;i<vertexArray.size();i++){
			vertexArray.get(i).setAdjList(sortvalueNsortindex(vertexArray.get(i).getAdjList()));
		}
		
		VisitedList.add(currentVertex);
		int minIndex =0;
		while(currentVertex != lastLineIndex){
			minIndex= getSamllest();
			VisitedList.add(minIndex);
			for(int i=0;i<vertexArray.get(currentVertex).getAdjList().size();i++){
				if(vertexArray.get(currentVertex).getAdjList().get(i).getVertexNumber() == minIndex){
					vertexArray.get(currentVertex).getAdjList().get(i).setVisited(true);
				}
			}
			for(int i=0;i<vertexArray.get(minIndex).getAdjList().size();i++){
				if(vertexArray.get(minIndex).getAdjList().get(i).getVertexNumber() == currentVertex){
					vertexArray.get(minIndex).getAdjList().get(i).setVisited(true);
				}
			}
			currentVertex = minIndex;
			//System.out.println(VisitedList);
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean found = false;
		for(int i=0;i<VisitedList.size();i++){
			if(list.size() == 0){
				list.add(VisitedList.get(0));
				continue;
			}
			//System.out.println("current Vertex " + VisitedList.get(i));
			for(int j=0;j<vertexArray.get(list.get(list.size()-1)).getAdjList().size();j++){
				if(vertexArray.get(list.get(list.size()-1)).getAdjList().get(j).getVertexNumber() == VisitedList.get(i)){
					list.add(VisitedList.get(i));
					found = true;
					break;
				}
			}
			if(found != true){
				list.add(lastLineIndex);
				list.add(firstLineIndex);
				for(int a=0;a<vertexArray.get(firstLineIndex).getAdjList().size();a++){
					if(vertexArray.get(list.get(list.size()-1)).getAdjList().get(a).getVertexNumber() == VisitedList.get(i)){
						list.add(VisitedList.get(i));
						found = true;
						break;
					}
				}
			}
			found = false;
			
			//System.out.println(list);
		}
		String finalPath = null;
		String path = null;
		int tempPath = 0;
		int currentIndex =0;
		int finalDist =0;
		for(int a=0;a<list.size();a++){
			//System.out.println(list.get(a));
			if(list.get(a) == firstLineIndex){
				path = Integer.toString(list.get(a));
				tempPath =0;
				currentIndex = list.get(a);
				continue;
			}
			if(list.get(a) == lastLineIndex){
				//System.out.println("ending 1 path");
				path = path + " " + list.get(a);
				for(int i=0;i<vertexArray.get(currentIndex).getAdjList().size();i++){
					if(vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber() == lastLineIndex){
						tempPath = tempPath + vertexArray.get(currentIndex).getAdjList().get(i).getVertexValue();
						//System.out.println("Path " + path);
						//System.out.println("Dist "  + tempPath + " " + finalDist);
						if(finalDist ==0){
							finalDist = tempPath;
							finalPath = path;
						}else if(finalDist > tempPath){
							finalDist = tempPath;
							finalPath = path;
						}
						continue;
					}
				}
			}
			if(list.get(a) != lastLineIndex && list.get(a) != firstLineIndex){
				for(int i=0;i<vertexArray.get(currentIndex).getAdjList().size();i++){
					if(vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber() == list.get(a)){
						path = path + " " + vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber();
						//System.out.println("path 2 " +path);
						//System.out.println("before dist " +tempPath);
						tempPath = tempPath + vertexArray.get(currentIndex).getAdjList().get(i).getVertexValue();
						//System.out.println("dist 2 " +tempPath);
						currentIndex = vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber();
						continue;
					}
				}
			}
			//System.out.println("out come " + finalPath+" " + finalDist);
		}
		System.out.println("shortest path " + finalPath);
		System.out.println("Shortest Dist " + finalDist);
		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
private static int getSamllest(){
	int min=0;
	int index =0;
	for(int i=0;i<VisitedList.size();i++){
		for(int j=0;j<vertexArray.get(VisitedList.get(i)).getAdjList().size();j++){
			if(!vertexArray.get(VisitedList.get(i)).getAdjList().get(j).isVisited()){
				if(min == 0){
					min = vertexArray.get(VisitedList.get(i)).getAdjList().get(j).getVertexValue();
					index = vertexArray.get(VisitedList.get(i)).getAdjList().get(j).getVertexNumber();
					currentVertex = VisitedList.get(i);
				}else if(min > vertexArray.get(VisitedList.get(i)).getAdjList().get(j).getVertexValue()){
					min = vertexArray.get(VisitedList.get(i)).getAdjList().get(j).getVertexValue();
					index = vertexArray.get(VisitedList.get(i)).getAdjList().get(j).getVertexNumber();
					currentVertex = VisitedList.get(i);
				}
			}
		}
	}
	return index;
}
private static LinkedList<AdjListNode> sortvalueNsortindex(LinkedList<AdjListNode> list){
		
		LinkedList<Integer> indexofstartnumbersaftersort = new LinkedList<Integer>();
		LinkedList<AdjListNode> adjList = list;
		Integer[] listofstartnumbers = new Integer[adjList.size()];
		for(int i=0;i < adjList.size(); i++){
			listofstartnumbers[i] = adjList.get(i).getVertexValue();
		}
		Arrays.sort(listofstartnumbers);
		for(int i = 0; i < adjList.size(); i++){
			for(int j =0 ; j < adjList.size(); j++){
			if(listofstartnumbers[i] == adjList.get(j).getVertexValue()){
				indexofstartnumbersaftersort.add(adjList.get(j).getVertexNumber());
			}
		}
	}
		adjList.clear();
		AdjListNode node;
		for(int i = 0 ; i < listofstartnumbers.length; i ++){
			
			node = new AdjListNode(indexofstartnumbersaftersort.get(i),listofstartnumbers[i]);
			adjList.add(node);
		}
		return adjList;

	}	
}

