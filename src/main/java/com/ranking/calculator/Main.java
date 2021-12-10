package com.ranking.calculator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main{
	public static void main (String args[]){
		String [] results = null;
		Map<String,Integer> map = new HashMap<String,Integer>();
		System.out.println("Calculation of Ranking started!");
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Provide file path: ");
			String filepath = sc.nextLine();
			Path path = Paths.get(filepath);
			ReadFile rf= new ReadFile();
			CalcuateRank cr = new CalcuateRank();
			SortRankings sr = new SortRankings();
			long count = Files.lines(path).count();
			//System.out.println("lines count: "+count);
			results = rf.readfile(filepath,count);
			map = cr.calculate_rank(results);
			sr.sortMap(map);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}