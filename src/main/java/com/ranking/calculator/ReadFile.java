package com.ranking.calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class ReadFile{
	public String[] readfile(String filepath, long count) {
		String [] arr = new String [(int) count];
		try {
			String line = "";
			int i=0;
			BufferedReader read = new BufferedReader(new FileReader(filepath));
			try {
				while((line = read.readLine()) != null){//count>0 &&
					arr [i] = line;
					//System.out.println("line: "+line+" :::: arr["+i+"]: "+arr[i].toString());
					i++;
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("arr: "+Arrays.toString(arr));
		return arr;
	}
}