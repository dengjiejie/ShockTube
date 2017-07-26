package com.tju.edu.shockTube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public final class Calculate {
	
	int timestep = 100000;
	int sou = 10000;
	double timeInterval = 0.00001;
	
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
	
	double va;
	
	double sigma = Initialization.sigma;
	double gama = 1.4;
	ListNode nearbyListNode;
	ListNode near;
	
	public void solve(ListNode head) {
		ListNode calculateNode;
		
		for(int i = 1; i <= timestep; i++){
			//System.out.println("��" + i + "��ʱ�䲽" );
			
			/****************************��һ������******3*************************/
			calculateNode = head;
			while (calculateNode.next != null) {
				
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				newRho = 0;
				rhoDerivative=0;
				while (nearbyListNode.next != null) {

					nearbyListNode = nearbyListNode.next;
					newRho +=  nearbyListNode.getMass()*KernelFunction.kernelFunction(calculateNode, nearbyListNode);

//					rhoDerivative += nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
//							*(calculateNode.getVelocity() - nearbyListNode.getVelocity());
				}
				
//				newRho = calculateNode.getRho() + timeInterval * rhoDerivative;
		
				calculateNode.setNewRho(newRho);
			}
			
			/************************��һ���������*******************/
			/******�����е�������Ϣ��������ڸ���*********/
			head.refreshRho();


			/****************************�ڶ�������*******************************/
			calculateNode = head;
			while (calculateNode.next != null) {
				calculateNode = calculateNode.next;
				if(calculateNode.getType() == 0)continue;
				nearbyListNode = searchNearbyListNode(calculateNode);
				
				near = nearbyListNode;
				va = 0;
				while (near.next != null) {
					near = near.next;
					va += near.getMass()*(near.getVelocity() - calculateNode.getVelocity())*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, near);
				}
				va = 1/calculateNode.getRho()*va;
				
				
				energyDerivative = 0;
				smoothLengthDerivative = 0;
				velocityDerivative = 0;
				while (nearbyListNode.next != null) {
					nearbyListNode = nearbyListNode.next;
					
					velocityDerivative +=  -nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*( calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+ nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
					
//					smoothLengthDerivative += -calculateNode.getSmoothLength()/calculateNode.getRho()
//							*(nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
//									*(calculateNode.getVelocity() - nearbyListNode.getVelocity()));
					
//					energyDerivative += nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
//							*(calculateNode.getVelocity() - nearbyListNode.getVelocity())
//							*(calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
//							+ 1.0/2.0 * ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false));
					
					energyDerivative += 1.0/2.0 * nearbyListNode.getMass()*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
							*((calculateNode.getVelocity() - nearbyListNode.getVelocity())
							*(calculateNode.getPressure()/Math.pow(calculateNode.getRho(), 2)
							+nearbyListNode.getPressure()/Math.pow(nearbyListNode.getRho(), 2)
							+ ArtificialViscosity.artificialViscosity(calculateNode, nearbyListNode,false))
							+2*ArtificialHeat.artificialHeat(calculateNode, calculateNode, va)
							*(calculateNode.getX()-nearbyListNode.getX()));
////					
//					energyDerivative += nearbyListNode.getMass()/nearbyListNode.getRho()*(calculateNode.getPressure()-nearbyListNode.getPressure())
//							*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, nearbyListNode)
//							*calculateNode.getPressure()/calculateNode.getRho();
										
				}
							
				newVelocity = calculateNode.getVelocity() + timeInterval * velocityDerivative;

				newX = calculateNode.getX() + timeInterval * newVelocity;
				
				newPressure = calculateNode.getRho()*calculateNode.getEnergy()*(gama-1);
				
				newEnergy = calculateNode.getEnergy() + timeInterval * energyDerivative;
				
				newSoundVelocity = Math.sqrt(newEnergy*gama*(gama-1));
				
//				newSmoothLength = calculateNode.getSmoothLength() + timeInterval * smoothLengthDerivative;
				
				newSmoothLength = sigma*calculateNode.getMass()/calculateNode.getRho();
				
				calculateNode.setNewPressure(newPressure);
				calculateNode.setNewVelocity(newVelocity);
				calculateNode.setNewX(newX);
				calculateNode.setNewSmoothLength(newSmoothLength);
				calculateNode.setNewEnergy(newEnergy);
				calculateNode.setNewSoundVelocity(newSoundVelocity);
				
				
			}
			/************************�ڶ����������*******************/
			/******�����е�������Ϣ��������ڸ���*********/
			head.refreshEnergy();
			head.refreshVelocity();
			head.refreshX();
			head.refreshSmoothLength();
			head.refreshSoundVelocity();
			head.refreshPressure();
			
			/**************************������***********************************/
			if(i %sou ==0){
				System.out.println("��"+i / sou + "�����");
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(new File("D:/Project/Experiment/src/result/��" + i/sou  + "��������.txt"));
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
	
	/********************************������������Ҫ�������**********************************************/
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
