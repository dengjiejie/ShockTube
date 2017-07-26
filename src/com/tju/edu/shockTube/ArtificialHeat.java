 package com.tju.edu.shockTube;

public final class ArtificialHeat {

	
	public static double artificialHeat(ListNode calculateNode, ListNode nearbyListNode, double va)
	{
		double g1 = 0.5;
		double g2 = 0.5;
		double ha = 0;
		double hb = 0;
		double product;
		double pi;
		double mu;
		product = (calculateNode.getEnergy() - nearbyListNode.getEnergy())*(calculateNode.getX() - nearbyListNode.getX());
		if(product < 0){
			System.out.println("ghhghgh");

			ListNode near = Calculate.searchNearbyListNode(nearbyListNode);
			double vb = 0;
			while (near.next != null) {
				near = near.next;
				vb += near.getMass()*(near.getVelocity() - calculateNode.getVelocity())*KernelFunction.kernelFunction_Of_First_Order_Derivative(calculateNode, near);
			}
			vb = 1/calculateNode.getRho()*vb;
			
			if(va >=0 ){ 
				va= 0;
			}else {
				ha = g1*calculateNode.getSmoothLength()*calculateNode.getSoundVelocity() + 2*g2*Math.pow(calculateNode.getSmoothLength(),2)*va;
			}
			if(vb >=0 ){ 
				vb =0;
			}else {
				hb = g1*nearbyListNode.getSmoothLength()*nearbyListNode.getSoundVelocity() + 2*g2*Math.pow(nearbyListNode.getSmoothLength(),2)*vb;
			}
			
			mu = (calculateNode.getRho() + nearbyListNode.getRho())/2*Math.pow((calculateNode.getSmoothLength()+nearbyListNode.getSmoothLength())/2, 2)
					*(Math.pow(calculateNode.getX()-nearbyListNode.getX(), 2)/Math.pow((calculateNode.getSmoothLength()+nearbyListNode.getSmoothLength())/2, 2)+0.01);
			
			pi = (ha+hb)*(calculateNode.getEnergy() - nearbyListNode.getEnergy())/mu;
		}else {
			pi = 0;
		}
		

		
		
		return pi;
	}
	
	
	
	
	
}
