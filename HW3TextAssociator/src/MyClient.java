import java.io.*;
import java.util.*;

public class MyClient {
	public static void main(String[] args) {
		TextAssociator guide = new TextAssociator();
		String[] city = {"seattle","new york","los angeles","san francisco","washington dc","miami"};
		String[][] place = { {"Space Needle","Pike Market","University of Washington","Japanese Garden"},
							{"Museum of Modern Art","The 5th AVE"},{"Universal Studio","Chinatown","K town","Littele Tokyo","Hollywood"},
							{"Golden Gate Bridge","Alcatraz Island"},{"National Gallery of Art","Washington Monument","White House"},
							{"Vizcaya Museum and Gardens","Wynwood Walls","Kennedy Park"}};
		
		for (int i = 0; i < city.length; i++) {
			guide.addNewWord(city[i]);
			for (String s : place[i]) {				
				guide.addAssociation(city[i], s);
			}
		}
		Scanner scan = new Scanner(System.in);
		String result = "";
		Random r = new Random();
		while(true){
			System.out.println("This is a tour guide ");
			System.out.print("Input a city's name so that you can get a destination: (enter \"exit\" to exit) ");		
			String input = scan.nextLine();
			if(input.equals("exit")) {
				break;
			}
			
			if(!input.equals(city[0]) && !input.equals(city[1])&&!input.equals(city[2])&&
					!input.equals(city[3])&&!input.equals(city[4])&&!input.equals(city[5])){
				System.out.println("Sorry, I cannot understand, please enter another city's name!");							
			}else{			
				Set<String> places = guide.getAssociations(input.toLowerCase());
				result = ""+places.toArray()[r.nextInt(places.size())];
				System.out.println(result.trim());
				System.out.println();
			}
		}
	}
}