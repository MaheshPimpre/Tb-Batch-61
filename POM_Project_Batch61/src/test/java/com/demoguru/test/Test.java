package com.demoguru.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

//	A = 8   
//			B = [3, 5, 1, 2, 1, 2]
	
	public static boolean hasPrintWithSum(int []b, int a) {
		Set<Integer> s=new HashSet<Integer>();
		Set<String> us=new HashSet<String>();
		List<List<Integer>> result=new ArrayList<List<Integer>>();
		
		
		for(int n:b) {
			int c=a-n;
			if(s.contains(c)) {
				int min=Math.min(n,c);
				int max=Math.max(n,c);
				String key=min+":"+max;
				if(!us.contains(key)) {
					result.add(Arrays.short(key));
				}
				System.out.println("Pair found: "+n+ " + "+c+" = "+a);
				return true;
			}
			s.add(n);
		}
		return false;
	}
	public static void main(String[] args) {
		int a = 8;
		int []b= {3, 5, 1, 2, 1, 2};
		boolean hasPrintWithSum = hasPrintWithSum(b, a);
		System.out.println("pair with sum "+ a + " - "+hasPrintWithSum );
	}
}

