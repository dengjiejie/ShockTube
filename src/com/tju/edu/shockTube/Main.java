package com.tju.edu.shockTube;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**************** ��ʼ�����������****************************/
		
		
		
		ListNode head = Initialization.ini(new ListNode());;
		
		/****************���̼���****************************/

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
