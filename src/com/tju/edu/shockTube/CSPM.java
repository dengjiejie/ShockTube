package com.tju.edu.shockTube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public final class CSPM {
	
	int timestep = 1000;
	int sou = 100;
	double timeInterval = 0.0001;
	
	double newRho = 0;
	double rhoDerivative;
	
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
		
	double cspmCOE;
	
	public void solve(ListNode head) {
		ListNode calculateNode;
		
		
		for(int i = 1; i <= timestep; i++){
			//System.out.println("第" + i + "个时间步" );
			
			
			/****************************第一步计算******3*************************/
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				cspmCOE = 0;
				newRho = 0;
				rhoDerivative=0;
				while (nearbyListNode.next != null) {
					nearbyListNode = nearbyListNode.next;
					newRho +=  nearbyListNode.getMass()*KernelFunction.kernelFunction(calculateNode, nearbyListNode);
//					rhoDerivative += nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)*(calculateNode.getVelocity() - nearbyListNode.getVelocity());
//			
//					cspmCOE += nearbyListNode.getMass() / nearbyListNode.getRho()*(nearbyListNode.getX() - calculateNode.getX())
//							*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode);
					cspmCOE += nearbyListNode.getMass() / nearbyListNode.getRho()*KernelFunction.kernelFunction(calculateNode, nearbyListNode);
					
					
				}
//				calculateNode.setCspmCOE(cspmCOE);

//				rhoDerivative /= cspmCOE;
//				
//				newRho = calculateNode.getRho() + timeInterval * rhoDerivative;

				newRho /= cspmCOE;

				calculateNode.setNewRho(newRho);

			}
			
			/************************第一步计算完毕*******************/
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshRho();


			/****************************第二步计算*******************************/
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				cspmCOE = 0;
				energyDerivative = 0;
				smoothLengthDerivative = 0;
				velocityDerivative = 0;
				while (nearbyListNode.next != null) {
					nearbyListNode = nearbyListNode.next;
					
					velocityDerivative +=  -nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*( calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+ nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
					
					
					energyDerivative += 1.0/2.0 * nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*(calculateNode.getVelocity() - nearbyListNode.getVelocity())
							*(calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
					
//					smoothLengthDerivative += -calculateNode.getSmoothLength()/calculateNode.getRho()
//							*(nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
//									*(calculateNode.getVelocity() - nearbyListNode.getVelocity()));
					
					cspmCOE += nearbyListNode.getMass() / nearbyListNode.getRho()*(nearbyListNode.getX() - calculateNode.getX())
							*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode);
				
				}
				calculateNode.setCspmCOE1(cspmCOE);

				velocityDerivative /= cspmCOE;
				
				energyDerivative /= cspmCOE;

				newVelocity = calculateNode.getVelocity() + timeInterval * velocityDerivative;

				newX = calculateNode.getX() + timeInterval * newVelocity;
				
				newPressure = calculateNode.getRho()*calculateNode.getEnergy()*(gama-1);
				
				newEnergy = calculateNode.getEnergy() + timeInterval * energyDerivative;
				
				newSoundVelocity = Math.sqrt(newEnergy*gama*(gama-1));
				
				newSmoothLength = sigma*calculateNode.getMass()/calculateNode.getRho();
				
//				newSmoothLength = calculateNode.getSmoothLength() + timeInterval * smoothLengthDerivative;


				calculateNode.setNewPressure(newPressure);
				calculateNode.setNewVelocity(newVelocity);
				calculateNode.setNewX(newX);
				calculateNode.setNewSmoothLength(newSmoothLength);
				calculateNode.setNewEnergy(newEnergy);
				calculateNode.setNewSoundVelocity(newSoundVelocity);
				
				
			}
			/************************第二步计算完毕*******************/
			/******当所有的粒子信息计算完后在更新*********/
			head.refreshEnergy();
			head.refreshVelocity();
			head.refreshX();
			head.refreshSmoothLength();
			head.refreshSoundVelocity();
			head.refreshPressure();

			
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
							node.getRho(),node.getVelocity(),node.getEnergy(),node.getSmoothLength(),node.getCspmCOE1());
					
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
