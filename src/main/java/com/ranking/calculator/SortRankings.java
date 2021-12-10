package com.ranking.calculator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class SortRankings{
	LinkedHashMap<String,Integer> lkedmap = new LinkedHashMap<>();
	public void sortMap(Map<String,Integer> hshmap) {
		/* hshmap.entrySet()
		.stream() 
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEachOrdered(a -> lkedmap.put(a.getKey(), a.getValue())); */
		Comparator<Entry<String, Integer>> comp = Entry
		        .<String, Integer>comparingByValue().reversed()
		        .thenComparing(Entry.comparingByKey());
		lkedmap = hshmap.entrySet().stream().sorted(comp)
		                .collect(Collectors.toMap(Entry::getKey,
		                        Entry::getValue,
		                        (a, b) -> a,      // merge function
		                        LinkedHashMap::new));

		//System.out.println("After sorting descindeng order......");
		//System.out.println(lkedmap);
		print_ranks();
	}
	
	public void print_ranks() {
		int i = 1;
		for(Entry<String, Integer> s : lkedmap.entrySet()) {
			System.out.println(i+". "+s.getKey()+", "+s.getValue()+" pts");
			i++;
		}
	}
}