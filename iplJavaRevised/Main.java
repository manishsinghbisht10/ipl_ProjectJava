package iplJavaRevised;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Main {
	public static final int MATCH_SEASONID=1;
	public static final int MATCH_WINNERID=10;
	public static final int MATCH_ID=0;
	public static final int DELIVERIES_TEAMNAME=3;
	public static final int EXTRA_RUNS=16;
	public static final int BOWLER_NAMEID=8;
	public static final int TOTAL_RUNS=17;
	
	public static void main(String[] args) {
		
		matchsPlayedPerYear(); 
		NumberOfMatchsWonOfAllTeams(); 
		extraRunsConcededPerTeam2016();
		topEconomicalBowler();
	}

	private static void matchsPlayedPerYear() {
		List<matches>listOfMatchObject=new ArrayList<>();
		HashMap<String,Integer> mapOfSeasonsAsKeys=new HashMap<>();
		List<String>listToStoreSeasonOnce=new ArrayList<>();
		String path="/home/manish/Desktop/matches.csv";
		try {
			Scanner s=new Scanner(new File(path));
			while(s.hasNext()) {
				String[] line=s.nextLine().split(",");
				matches match=new matches();
				match.setSeason(line[MATCH_SEASONID]);
				listOfMatchObject.add(match);
			 	}
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		for(int i=1;i<listOfMatchObject.size();i++) {
			if(mapOfSeasonsAsKeys.containsKey(listOfMatchObject.get(i).getSeason())) {
				mapOfSeasonsAsKeys.put(listOfMatchObject.get(i).getSeason(),mapOfSeasonsAsKeys.get(listOfMatchObject.get(i).getSeason())+1);
			}else {
				listToStoreSeasonOnce.add(listOfMatchObject.get(i).getSeason());
				mapOfSeasonsAsKeys.put(listOfMatchObject.get(i).getSeason(),1);
			}
		}
		Collections.sort(listToStoreSeasonOnce);
		for(int i=0;i<listToStoreSeasonOnce.size();i++) {
			System.out.println(listToStoreSeasonOnce.get(i)+":"+mapOfSeasonsAsKeys.get(listToStoreSeasonOnce.get(i)));
		}
	}
	
	private static void NumberOfMatchsWonOfAllTeams() {
		String path="/home/manish/Desktop/matches.csv";
		List<matches>listOfMatchObject=new ArrayList<>();
		HashMap<String,Integer> mapOfWinnersAsKeys=new HashMap<>();
		try {
			Scanner s=new Scanner(new File(path));
			while(s.hasNext()) {
				String[] line=s.nextLine().split(",");
				matches matchObject=new matches();
				matchObject.setWinner(line[MATCH_WINNERID]);
				listOfMatchObject.add(matchObject);
			 	}
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		for(int i=1;i<listOfMatchObject.size();i++) {
			if(mapOfWinnersAsKeys.containsKey(listOfMatchObject.get(i).getWinner())) {
				mapOfWinnersAsKeys.put(listOfMatchObject.get(i).getWinner(),mapOfWinnersAsKeys.get(listOfMatchObject.get(i).getWinner())+1);
			}else {
				if(listOfMatchObject.get(i).getWinner().length()!=0)mapOfWinnersAsKeys.put(listOfMatchObject.get(i).getWinner(),1);
			}
		}
		Set<String> keys=mapOfWinnersAsKeys.keySet();
		for(String teamName:keys) {
			System.out.println(teamName+":"+mapOfWinnersAsKeys.get(teamName));
		}
	}

	private static void extraRunsConcededPerTeam2016() {
		String pathOfMatchs="/home/manish/Desktop/matches.csv";
		String pathOfDeliveries="/home/manish/Desktop/deliveries.csv";
		List<matches>listOfMatchObject=new ArrayList<>();
		HashMap<String,Integer> mapOfTeamNamesAsKeys=new HashMap<>();
		try {
			Scanner s=new Scanner(new File(pathOfMatchs));
			while(s.hasNext()) {
				String[] dataOfMatches=s.nextLine().split(",");
				matches matchObject=new matches();
				matchObject.setId(dataOfMatches[MATCH_ID]);
				matchObject.setSeason(dataOfMatches[MATCH_SEASONID]);
				if(matchObject.getSeason().equals("2016"))listOfMatchObject.add(matchObject);
			 	}
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		int minId=Integer.parseInt(listOfMatchObject.get(0).getId());
		int maxId=Integer.parseInt(listOfMatchObject.get(listOfMatchObject.size()-1).getId());
		try {
			Scanner s=new Scanner(new File(pathOfDeliveries));
			s.nextLine();
			while(s.hasNext()) {
				String[] dataOfDeliveriese=s.nextLine().split(",");
				int searchId=Integer.parseInt(dataOfDeliveriese[MATCH_ID]);
				if(searchId>=minId&&searchId<=maxId) {
					if(mapOfTeamNamesAsKeys.containsKey(dataOfDeliveriese[DELIVERIES_TEAMNAME])) {
						mapOfTeamNamesAsKeys.put(dataOfDeliveriese[DELIVERIES_TEAMNAME], mapOfTeamNamesAsKeys.get(dataOfDeliveriese[DELIVERIES_TEAMNAME])+Integer.parseInt(dataOfDeliveriese[EXTRA_RUNS]));
					}else {
						mapOfTeamNamesAsKeys.put(dataOfDeliveriese[DELIVERIES_TEAMNAME], Integer.parseInt(dataOfDeliveriese[EXTRA_RUNS]));
					}
				}
			 	}
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		Set<String> keys=mapOfTeamNamesAsKeys.keySet();
		for(String teamName:keys) {
			System.out.println(teamName+":"+mapOfTeamNamesAsKeys.get(teamName));
		}
		}

	private static void topEconomicalBowler() {
		String pathOfMatches="/home/manish/Desktop/matches.csv";
		String pathOfDeliveries="/home/manish/Desktop/deliveries.csv";
		HashMap<String,Integer>mapOfMatchObject=new HashMap<>();
		HashMap<String,Integer>mapOfPlayersAsKeys=new HashMap<>();
		HashMap<String,Integer>mapOfNoOfBallsByIndividualPlayer=new HashMap<>();
		List<deleveries>listOfDeleveriesObject=new ArrayList<>();
		List<String>listOfUniqueBowlerName=new ArrayList<>();
		Scanner s;
		try {
			s = new Scanner(new File(pathOfMatches));
			while(s.hasNext()) {
				String[] dataOfMatches=s.nextLine().split(",");
				if(dataOfMatches[MATCH_SEASONID].equals("2015")) {
					matches matchObject=new matches();
					matchObject.setId(dataOfMatches[MATCH_ID]);
					mapOfMatchObject.put(matchObject.getId(),1);
				}
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			s = new Scanner(new File(pathOfDeliveries));
			while(s.hasNext()){
				String[] dataOfDeleveries=s.nextLine().split(",");
				if(mapOfMatchObject.containsKey(dataOfDeleveries[MATCH_ID])) {
					deleveries deleveriesObject=new deleveries();
					deleveriesObject.setBowler(dataOfDeleveries[BOWLER_NAMEID]);
					deleveriesObject.setTotalRuns(dataOfDeleveries[TOTAL_RUNS]);
					listOfDeleveriesObject.add(deleveriesObject);
				}
			}//mapOfPlayersAsKeys
		    }catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i=0;i<listOfDeleveriesObject.size();i++) {
			if(mapOfPlayersAsKeys.containsKey(listOfDeleveriesObject.get(i).getBowler())) {
				mapOfPlayersAsKeys.put(listOfDeleveriesObject.get(i).getBowler(), mapOfPlayersAsKeys.get(listOfDeleveriesObject.get(i).getBowler())+Integer.parseInt(listOfDeleveriesObject.get(i).getTotalRuns()));
				mapOfNoOfBallsByIndividualPlayer.put(listOfDeleveriesObject.get(i).getBowler(),mapOfNoOfBallsByIndividualPlayer.get(listOfDeleveriesObject.get(i).getBowler())+1);
			}else {
				mapOfPlayersAsKeys.put(listOfDeleveriesObject.get(i).getBowler(),Integer.parseInt(listOfDeleveriesObject.get(i).getTotalRuns()));
				mapOfNoOfBallsByIndividualPlayer.put(listOfDeleveriesObject.get(i).getBowler(),1);
				listOfUniqueBowlerName.add(listOfDeleveriesObject.get(i).getBowler());
			}
		}
		List<Economy>listEconomy=new ArrayList<>();
		for(int i=0;i<listOfUniqueBowlerName.size();i++){
			float noOfOvers=mapOfNoOfBallsByIndividualPlayer.get(listOfUniqueBowlerName.get(i))/6.0f;
			listEconomy.add(new Economy(listOfUniqueBowlerName.get(i),mapOfPlayersAsKeys.get(listOfUniqueBowlerName.get(i))/noOfOvers));
		}
		Collections.sort(listEconomy);
		for(int i=0;i<10;i++)System.out.println(listEconomy.get(i).name+":"+listEconomy.get(i).bowlerEconomy);
	}
}
class Economy implements Comparable<Economy>
{
	public String name;
	public float bowlerEconomy;
	Economy(String name,float bowlerEconomy){
	this.name=name;
	this.bowlerEconomy=bowlerEconomy;
	}
	@Override
	public int compareTo(Economy obj) {
		if(this.bowlerEconomy>obj.bowlerEconomy)return 1;
		else return -1;
	
		}
}
