package ipl_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) 
	{
		matchsPlayedPerYear();  // Number of matches played per year of all the years in IPL.
		System.out.println();
		NumberOfMatchsWonOfAllTeams(); // Number of matches won of all teams over all the years of IPL.
		System.out.println();
		extraRunsConcededPerTeam2016();//For the year 2016 get the extra runs conceded per team.
		System.out.println();
		topEconomicalBowler();//For the year 2015 get the top economical bowlers.

	}
	
	

	private static void matchsPlayedPerYear() 
	{
		HashMap<String,Integer>map=new HashMap<>();
		ArrayList<String>helper=new ArrayList<>();
		String path="/home/manish/Desktop/matches.csv";
		try {
			Scanner s=new Scanner(new File(path));
			while(s.hasNext())
			{
				String[] str=s.nextLine().split(",");
				if(str[1].equals("season"))continue;
				if(map.containsKey(str[1])) {
					map.put(str[1], map.get(str[1])+1);
					
				}else {
					helper.add(str[1]);
					map.put(str[1],1);
				}
			}
			Collections.sort(helper);
			for(int i=0;i<helper.size();i++) {
				System.out.println(helper.get(i)+":"+map.get(helper.get(i)));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private static void NumberOfMatchsWonOfAllTeams() 
	{
		String path="/home/manish/Desktop/matches.csv";
		HashMap<String,Integer>map=new HashMap<>();
		ArrayList<String>helper=new ArrayList<>();
		try {
			Scanner s=new Scanner(new File(path));
			while(s.hasNext())
			{
				String[] str=s.nextLine().split(",");
				if(str[10].equals("winner"))continue;
				if(map.containsKey(str[10])) {
					map.put(str[10], map.get(str[10])+1);
					
				}else {
					helper.add(str[10]);
					map.put(str[10],1);
				}	
			}
			for(int i=0;i<helper.size();i++) {
				System.out.println(helper.get(i)+":"+map.get(helper.get(i)));
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	private static void extraRunsConcededPerTeam2016() 
	{
		HashMap<String,Integer>map=new HashMap<>();
		ArrayList<String>helper=new ArrayList<>();
		ArrayList<String>helper2=new ArrayList<>();
		HashMap<String,Integer>map2=new HashMap<>();
		String path1="/home/manish/Desktop/matches.csv";
		String path2="/home/manish/Desktop/deliveries.csv";
		try {
			Scanner s1=new Scanner(new File(path1));
			Scanner s2=new Scanner(new File(path2));
			while(s1.hasNext()) {
				String matches[]=s1.nextLine().split(",");
				if(matches[1].equals("2016"))
				{
					map.put(matches[0],1);
				}	
			}
			while(s2.hasNext()) {
				String deliveries[]=s2.nextLine().split(",");
				if(map.containsKey(deliveries[0])) {
					if(map2.containsKey(deliveries[3])) {
						map2.put(deliveries[3],map2.get(deliveries[3])+Integer.parseInt(deliveries[16]));
					}else {
						helper2.add(deliveries[3]);
						map2.put(deliveries[3],Integer.parseInt(deliveries[16]));
					}
				}
			}
			
		for(int i=0;i<helper2.size();i++)System.out.println(helper2.get(i)+": "+map2.get(helper2.get(i)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static void topEconomicalBowler() 
	{
		ArrayList<Economy>list=new ArrayList<>();
		ArrayList<String>name=new ArrayList<>();
		HashMap<String,Integer>map1=new HashMap<>();
		HashMap<String,Integer>map2=new HashMap<>();
		HashMap<String,Integer>mapID=new HashMap<>();
		String path="/home/manish/Desktop/deliveries.csv";
		String path2="/home/manish/Desktop/matches.csv";
		try {
			Scanner s1=new Scanner(new File(path));
			Scanner s2=new Scanner(new File(path2));
			while(s2.hasNext()) {
				String arr2[]=s2.nextLine().split(",");
				if(arr2[1].equals("2015")) {
					if(mapID.containsKey(arr2[0])) {
						
					}else {
						if(arr2[0].equals("id"))continue;
						mapID.put(arr2[0], 1);
					}
				}
			}
			while(s1.hasNext()) {
				String arr[]=s1.nextLine().split(",");
				if(mapID.containsKey(arr[0]))
				{
					if(map1.containsKey(arr[8])) {
						map1.put(arr[8], map1.get(arr[8])+Integer.parseInt(arr[17]));
						map2.put(arr[8], map2.get(arr[8])+1);
						
					}else {
						if(arr[8].equals("bowler"))continue;
						name.add(arr[8]);
						map2.put(arr[8], 1);
						map1.put(arr[8],Integer.parseInt(arr[17]));
					}
				}
			}
		
			for(int i=0;i<map1.size();i++)
			{
				float size=map2.get(name.get(i))/6.0f;
				list.add(new Economy(name.get(i),map1.get(name.get(i))/size));
			}
			Collections.sort(list);
			for(int i=0;i<10;i++)System.out.println(list.get(i).name+":"+list.get(i).economy);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}// end of class

class Economy implements Comparable<Economy>
{
	public String name;
	public float economy;
	Economy(String name,float economy){
	this.name=name;
	this.economy=economy;
	}
	@Override
	public int compareTo(Economy obj) {
		if(this.economy>obj.economy)return 1;
		else return -1;
	
		}
}
