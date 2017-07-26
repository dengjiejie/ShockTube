package com.tju.edu.shockTube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Initialization {
	
	static double gama = 1.4;
	static double sigma = 1.4;
	
	 //һ��ܵ��ĳ���
	static double tubelength = 2;

	/*��ʼ�������ܶ� */
	static double initRhoL ;
	static double initRhoR ;
	
	/*��ʼ������ѹǿ */
	static double initPressureL ;
	static double initPressureR ;
	
	/*��ʼ�������ٶ� */
	static double initVL ;
	static double initVR ;
	
	static double leftlizi ;
	static double rightlizi ;
	
	
	
	public static ListNode ini(ListNode head) {
		/****************************************��һ���ڵ�Ϊ�� ******************************************/
		
		ListNode listNode = new ListNode();
		head = listNode;
		head.setType(-1);
		
		shockTube();
		
//		twoExpansions();
		
//		strongShock();
//		weakShock();
		double dxl = tubelength / leftlizi;
		double dxr = tubelength / rightlizi;

		/*��ʼ���������� */
		double mass = (initRhoL+ initRhoR)*tubelength/(leftlizi+rightlizi);
		
		/*��ʼ���������� */
		double initEnergyL = initPressureL/(initRhoL*(gama-1));
		double initEnergyR = initPressureR/(initRhoR*(gama-1));
		
		/*��ʼ���������� */
		double initSoundVelocityL = Math.sqrt(initEnergyL*gama*(gama-1));
		double initSoundVelocityR = Math.sqrt(initEnergyR*gama*(gama - 1));
		
		/*��ʼ���⻬���� */
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
			fs = new FileOutputStream(new File("D:/Project/Experiment/src/result/���ӳ�ʼ������.txt"));
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
		
		/*��ʼ������ѹǿ */
		initPressureL = 1;
		initPressureR = 0.1;
		
		/*��ʼ�������ٶ� */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 800;
		rightlizi = 100;
	}
	
	public static void twoExpansions() {
		initRhoL = 1;
		initRhoR = 1;
		
		/*��ʼ������ѹǿ */
		initPressureL = 0.4;
		initPressureR = 0.4;
		
		/*��ʼ�������ٶ� */
		initVL = -2;
		initVR = 2;
		
		leftlizi = 450;
		rightlizi = 450;
	}
	
	public static void strongShock() {
		initRhoL = 3;
		initRhoR = 2;
		
		/*��ʼ������ѹǿ */
		initPressureL = 1000;
		initPressureR = 0.1;
		
		/*��ʼ�������ٶ� */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 600;
		rightlizi = 400;
	}
	public static void weakShock() {
		initRhoL = 1;
		initRhoR = 1;
		
		/*��ʼ������ѹǿ */
		initPressureL = 7;
		initPressureR = 10;
		
		/*��ʼ�������ٶ� */
		initVL = 0;
		initVR = 0;
		
		leftlizi = 500;
		rightlizi = 500;
	}
}
