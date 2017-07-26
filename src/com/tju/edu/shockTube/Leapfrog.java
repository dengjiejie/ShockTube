package com.tju.edu.shockTube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public final class Leapfrog {
	
	int timestep = 100000;
	int sou = 10000;
	double timeInterval = 0.00001;
	
	double newRho = 0;
	
	double newPressure;
	
	double smoothLengthDerivative;
	double newSmoothLength;
	
	double velocityDerivative;
	double newVelocity;
	double newX;
	
	double energyDerivative;
	double newEnergy;
	
	
	double newSoundVelocity;
	
	
	double sigma = Initialization.sigma;
	double gama = 1.4;
	ListNode nearbyListNode;
	

	
	
	
	public void solve(ListNode head) {
		ListNode calculateNode;
		
		for(int i = 1; i <= timestep; i++){			
			
			/****************************第一步计算*******************************/
			
			
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				velocityDerivative = 0;
				energyDerivative = 0;
				smoothLengthDerivative = 0;
				newRho = 0;
				while (nearbyListNode.next != null) {
					nearbyListNode = nearbyListNode.next;
					
					newRho +=  nearbyListNode.getMass()*KernelFunction.kernelFunction(calculateNode, nearbyListNode);

					velocityDerivative +=  -nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*( calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+ nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
					
					energyDerivative += 1.0/2.0 * nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*(calculateNode.getVelocity() - nearbyListNode.getVelocity())
							*(calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
				}
				newX = calculateNode.getX() + 1.0/2.0*timeInterval * calculateNode.getVelocity();

				newVelocity = calculateNode.getVelocity() + 1.0/2.0*timeInterval * velocityDerivative;
				
				newEnergy = calculateNode.getEnergy() +1.0/2.0* timeInterval * energyDerivative;

				newPressure = newRho*newEnergy*(gama-1);
				
				newSoundVelocity = Math.sqrt(newEnergy*gama*(gama-1));
				
				newSmoothLength = sigma*calculateNode.getMass()/newRho;
				
				calculateNode.setNewX(newX);
				calculateNode.setNewVelocity(newVelocity);
				calculateNode.setNewRho(newRho);
				calculateNode.setNewPressure(newPressure);
				calculateNode.setNewSmoothLength(newSmoothLength);
				calculateNode.setNewEnergy(newEnergy);
				calculateNode.setNewSoundVelocity(newSoundVelocity);
				
			}
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshEnergy();
			head.refreshSmoothLength();
			head.refreshSoundVelocity();
			head.refreshPressure();
			head.refreshRho();
			head.refreshVelocity();
			head.refreshX();
			/************************第一步计算完毕*******************/
			
			
			
			/****************************第二步计算*******************************/

			
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				newRho = 0;
				while (nearbyListNode.next != null) {
					
					nearbyListNode = nearbyListNode.next;
					
					newRho +=  nearbyListNode.getMass()*KernelFunction.kernelFunction(calculateNode, nearbyListNode);
					
				}
				newSmoothLength = sigma*calculateNode.getMass()/newRho;
				newPressure = newRho*calculateNode.getEnergy()*(gama-1);
				
				calculateNode.setNewPressure(newPressure);
				calculateNode.setNewRho(newRho);
				calculateNode.setNewSmoothLength(newSmoothLength);
			}
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshRho();
			head.refreshSmoothLength();			
			head.refreshPressure();

			/************************第二步计算完毕*******************/
			
			
			////////////////////////////////////////////////////
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				velocityDerivative = 0;
				while (nearbyListNode.next != null) {
					
					nearbyListNode = nearbyListNode.next;
					
					velocityDerivative +=  -nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*( calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+ nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));					
				}
				newVelocity = calculateNode.getVelocity() + 1.0/2.0*timeInterval * velocityDerivative;
				newX = calculateNode.getX() + 1.0/2.0*timeInterval * newVelocity;

				calculateNode.setNewX(newX);
				calculateNode.setNewVelocity(newVelocity);
			}
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshVelocity();
			head.refreshX();
			///////////////////////////////////////////////////////////////
			
			/****************************第三步计算*******************************/

			
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				energyDerivative = 0;
				smoothLengthDerivative = 0;
				while (nearbyListNode.next != null) {
					nearbyListNode = nearbyListNode.next;

					energyDerivative += 1.0/2.0 * nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*(calculateNode.getVelocity() - nearbyListNode.getVelocity())
							*(calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
				}
				
				newEnergy = calculateNode.getEnergy() +1.0/2.0* timeInterval * energyDerivative;
				
				newSoundVelocity = Math.sqrt(newEnergy*gama*(gama-1));
				
				calculateNode.setNewEnergy(newEnergy);
				calculateNode.setNewSoundVelocity(newSoundVelocity);
				
			}
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshEnergy();
			head.refreshSoundVelocity();
			
			/************************第三步计算完毕*******************/
			
			
			
			/**************************输出结果***********************************/
			if(i %sou ==0){
				System.out.println("第"+i / sou + "个输出");
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(new File("D:/Project/Experiment/src/result/第" + i/sou  + "个输出结果.txt"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				PrintStream p = new PrintStream(fs);
				ListNode node = head;

				while(node.next != null){
					node = node.next;
					p.format("%-15.9f %-15.7f %-15.7f %-15.7f %-15.7f %-15.7f %-15.7f\n " , node.getX() , node.getPressure() ,
							node.getRho(),node.getVelocity(),node.getEnergy(),node.getSmoothLength(),node.getSoundVelocity());
				}
				p.close();
			}
		}
	}
	
	/********************************搜索附近满足要求的粒子**********************************************/
	public static ListNode searchNearbyListNode(ListNode calculateNode) {
		ListNode preListNode = calculateNode;
		ListNode nextListNode = calculateNode.next;
		
		ListNode nearbyListNode = new ListNode();
	
		
		while (preListNode.getType() != -1 ) {
			if(Math.abs(preListNode.getX() - calculateNode.getX()) <= (preListNode.getSmoothLength()+calculateNode.getSmoothLength())){
				ListNode node = new ListNode();
				node.copyNode(preListNode);
				node.next = nearbyListNode.next;
				node.preNode = nearbyListNode;
				nearbyListNode.next = node;
			}else break;
			preListNode = preListNode.preNode;
			
		}
		while (nextListNode != null ) {
			if(Math.abs(nextListNode.getX() - calculateNode.getX()) <=  (nextListNode.getSmoothLength()+ calculateNode.getSmoothLength())){
				ListNode node = new ListNode();
				node.copyNode(nextListNode);
				node.next = nearbyListNode.next;
				node.preNode = nearbyListNode;
				nearbyListNode.next = node;
			}else break;
			nextListNode = nextListNode.next;
	
		}

		return nearbyListNode;
	}
	
	
	
	
	
	
}
