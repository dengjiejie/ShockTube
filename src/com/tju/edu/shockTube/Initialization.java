package com.tju.edu.shockTube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Initialization {
	
	static double gama = 1.4;
	static double sigma = 1.4;
	
	 //一半管道的长度
	static double tubelength = 2;

	/*初始化左右密度 */
	static double initRhoL ;
	static double initRhoR ;
	
	/*初始化左右压强 */
	static double initPressureL ;
	static double initPressureR ;
	
	/*初始化左右速度 */
	static double initVL ;
	static double initVR ;
	
	static double leftlizi ;
	static double rightlizi ;
	
	
	
	public static ListNode ini(ListNode head) {
		/****************************************第一个节点为空 ******************************************/
		
		ListNode listNode = new ListNode();
		head = listNode;
		head.setType(-1);
		
		shockTube();
		
//		twoExpansions();
		
//		strongShock();
//		weakShock();
		double dxl = tubelength / leftlizi;
		double dxr = tubelength / rightlizi;

		/*初始化粒子质量 */
		double mass = (initRhoL+ initRhoR)*tubelength/(leftlizi+rightlizi);
		
		/*初始化左右能量 */
		double initEnergyL = initPressureL/(initRhoL*(gama-1));
		double initEnergyR = initPressureR/(initRhoR*(gama-1));
		
		/*初始化左右声速 */
		double initSoundVelocityL = Math.sqrt(initEnergyL*gama*(gama-1));
		double initSoundVelocityR = Math.sqrt(initEnergyR*gama*(gama - 1));
		
		/*初始化光滑长度 */
		double initSmoothLengthL = sigma*mass/initRhoL;
		double initSmoothLengthR = sigma*mass/initRhoR;
		
		int num =0;
		
		
		double i;
		
		for( i = -2*dxl-tubelength; i < 0 ; i += dxl){
			
			ListNode node = new ListNode();
			node.setX(i);
			node.setNum(++num);
			node.setRho(initRhoL);
			node.setVelocity(initVL);
			node.setEnergy(initEnergyL);
			node.setPressure(initPressureL);
			node.setMass(mass);
			node.setSmoothLength(initSmoothLengthL);
			node.setSoundVelocity(initSoundVelocityL);
			
			if(i < -tubelength){
				node.setType(0);
				//node.setVelocity(initVR);
			}else {
				node.setType(1);
			}
			node.preNode = listNode;
			listNode.next = node;
			listNode = node;
		}
		
//		i = i - dxl +dxr;
		
		for( ; i <= tubelength + 3*dxr ; i += dxr){
			
			ListNode node = new ListNode();
			node.setX(i);
			node.setNum(++num);
			node.setRho(initRhoR);
			node.setVelocity(initVR);
			node.setEnergy(initEnergyR);
			node.setPressure(initPressureR);
			node.setMass(mass);
			node.setSmoothLength(initSmoothLengthR);
			node.setSoundVelocity(initSoundVelocityR);
			
			if(i > tubelength){
				node.setType(0);
				//node.setVelocity(initVL);

			}else {
				node.setType(1);
			}
			node.preNode = listNode;
			listNode.next = node;
			listNode = node;
		}
		
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(new File("D:/Project/Experiment/src/result/粒子初始化数据.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintStream p = new PrintStream(fs);
		ListNode node = head;
		while(node.next != null){
			node = node.next;
			p.format("%-15.9f %-15.7f %-15.7f %-15.7f %-15.7f %-15.7f %-15.7f\n " , node.getX() , node.getPressure() ,node.getRho(),node.getVelocity(),node.getEnergy(),node.getSmoothLength(),node.getSoundVelocity());
		}
		p.close();
		return head;
	}
	
	public static void shockTube() {
		initRhoL = 1;
		initRhoR = 0.125;
		
		/*初始化左右压强 */
		initPressureL = 1;
		initPressureR = 0.1;
		
		/*初始化左右速度 */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 800;
		rightlizi = 100;
	}
	
	public static void twoExpansions() {
		initRhoL = 1;
		initRhoR = 1;
		
		/*初始化左右压强 */
		initPressureL = 0.4;
		initPressureR = 0.4;
		
		/*初始化左右速度 */
		initVL = -2;
		initVR = 2;
		
		leftlizi = 450;
		rightlizi = 450;
	}
	
	public static void strongShock() {
		initRhoL = 3;
		initRhoR = 2;
		
		/*初始化左右压强 */
		initPressureL = 1000;
		initPressureR = 0.1;
		
		/*初始化左右速度 */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 600;
		rightlizi = 400;
	}
	public static void weakShock() {
		initRhoL = 1;
		initRhoR = 1;
		
		/*初始化左右压强 */
		initPressureL = 7;
		initPressureR = 10;
		
		/*初始化左右速度 */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 500;
		rightlizi = 500;
	}
}
