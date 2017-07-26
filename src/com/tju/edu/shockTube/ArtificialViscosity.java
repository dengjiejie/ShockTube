 package com.tju.edu.shockTube;

public final class ArtificialViscosity {

	
	public static double artificialViscosity(ListNode calculateNode, ListNode nearbyListNode,boolean print)
	{
		double alpha = 1;
		double beta = 2;
		double mu;
		double pi;
		double product;
		double h = (calculateNode.getSmoothLength() + nearbyListNode.getSmoothLength()) / 2;
		double dis = Math.abs(calculateNode.getX() - nearbyListNode.getX());
		product = (calculateNode.getVelocity() - nearbyListNode.getVelocity())*(calculateNode.getX() - nearbyListNode.getX());
		if (product<0){
			mu = h*product / (Math.pow(dis, 2) + 0.01*Math.pow(h, 2));
			pi = (-alpha*(calculateNode.getSoundVelocity()+nearbyListNode.getSoundVelocity())/2*mu + beta*Math.pow(mu, 2)) / ((calculateNode.getRho() + nearbyListNode.getRho()) / 2);
		}else{
			pi = 0.0;
		}
		
		if(print){
			System.out.println(product);
		}
		
		
		return pi;
	}
	
	
	
	
	
}
