package com.tju.edu.shockTube;

public class KernelFunction {

	public static double kernelFunction(ListNode calculateNode, ListNode nearbyListNode) {
		
		double smothLength = nearbyListNode.getSmoothLength();

		double distance = Math.abs(calculateNode.getX() - nearbyListNode.getX());
		
		double R = distance/smothLength;
		
		double factor = 1.0 / smothLength;
	
		double kernelValue = 0;
		
		if(R>=0 && R<1){
			kernelValue = factor*(2.0/3.0-R*R+0.5*R*R*R);	
		}else if(R>=1 && R<2){
			kernelValue = factor*((2.0-R)*(2.0-R)*(2.0-R)/6.0);
			
		}else if(R>=2){
			kernelValue = 0.0;
		}	 
		return kernelValue;
	}
	
	
	public static double kernelFunction_Of_First_Order_Derivative (ListNode calculateNode, ListNode nearbyListNode) {
		
		double smothLength = (calculateNode.getSmoothLength()+nearbyListNode.getSmoothLength())/2;//Hab
		
		double distance = Math.abs(calculateNode.getX() - nearbyListNode.getX());//Rab
		
		double x =  calculateNode.getX() - nearbyListNode.getX();
		
		double R = distance/smothLength;
		
		double factor = 1.0 / smothLength;
	
		double kernelValue = 0;
		
		if (R > 0 && R < 1){
			kernelValue = factor*(1.5*R*R - 2.0*R)*(x/distance/smothLength);	
		}else if (R >= 1 && R < 2){
			kernelValue = factor*(-0.5*(2.0 - R)*(2.0 - R))*(x/distance/smothLength);	
			//System.out.println(kernelValue+"----"+x+"----"+smothLength+"------------"+distance+"----"+R);
		}else {
			kernelValue = 0.0;
		}
		return kernelValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
