package com.tju.edu.shockTube;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**************** 初始化并输出数据****************************/
		
		
		
		ListNode head = Initialization.ini(new ListNode());;
		
		/****************过程计算****************************/

		Calculate calculate = new Calculate();
		calculate.solve(head);
//		
//		Leapfrog leapfrog = new Leapfrog();
//		leapfrog.solve(head);
		
//		CSPM cspm = new CSPM();
//		cspm.solve(head);
		
		System.out.println("Over");
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
