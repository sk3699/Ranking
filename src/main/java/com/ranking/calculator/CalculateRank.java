package com.ranking.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class CalcuateRank{
	Map<String,Integer> map = new HashMap<String,Integer>();
	public Map<String,Integer> calculate_rank(String [] arr) {
		for(String res : arr) {
			String s1 = "";
			String s2 = "";
			String s3 = "";
			String s4 = "";
			String [] arr2 = new String [2];
			arr2 = res.split(","); //[, ]+
			//System.out.println("Splitted arr2: "+Arrays.toString(arr2));
			s1 = arr2[0].substring(0,arr2[0].length()-2);
			s2 = arr2[1].substring(1, arr2[1].length()-2);
			s3 = arr2[0].substring(arr2[0].length()-1, arr2[0].length());
			s4 = arr2[1].substring(arr2[1].length()-1, arr2[1].length());
			//System.out.println("Strings in order: "+s1+" "+s2+" "+s3+" "+s4);
			comparer(s1,s2,s3,s4);
		}
		/*System.out.println("Map prinnting:-\n");
		for(String s:map.keySet()) {
			System.out.println(s+":"+map.get(s));
		}
		*/
		return map;
	}
	
	public void comparer(String s1, String s2, String s3, String s4) {
		int int3 = Integer.parseInt(s3);
		int int4 = Integer.parseInt(s4);
		if(int3 == int4) {
			if(int3 !=0 && int4 !=0) {
				if(map.containsKey(s1)) {
					int val = map.get(s1)+1;
					map.put(s1, val);
				}else {
					map.put(s1, 1);
				}
				if(map.containsKey(s2)) {
					int val = map.get(s2)+1;
					map.put(s2, val);
				}else {
					map.put(s2, 1);
				}
			}else {
				if(map.containsKey(s1)) {
					int val = map.get(s1)+0;
					map.put(s1, val);
				}else {
					map.put(s1, 0);
				}
				if(map.containsKey(s2)) {
					int val = map.get(s2)+0;
					map.put(s2, val);
				}else {
					map.put(s2, 0);
				}
			}
		}else if(int3 > int4) {
			if(map.containsKey(s1)) {
				int val = map.get(s1)+3;
				map.put(s1, val);
			}else {
				map.put(s1, 3);
			}
			if(map.containsKey(s2)) {
				int val = map.get(s2)+0;
				map.put(s2, val);
			}else {
				map.put(s2, 0);
			}
		}else if(int4 > int3) {
			if(map.containsKey(s2)) {
				int val = map.get(s2)+3;
				map.put(s2, val);
			}else {
				map.put(s2, 3);
			}
			if(map.containsKey(s1)) {
				int val = map.get(s1)+0;
				map.put(s1, val);
			}else {
				map.put(s1, 0);
			}
		}
	}
}