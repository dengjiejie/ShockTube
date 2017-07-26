package com.tju.edu.shockTube;

public class ListNode {
	
	int num;
	private double x;
	private double velocity;
	private double energy;
	private double pressure;
	private double rho;
	private double smoothLength;
	private int type;
	private double soundVelocity;
	private double mass;
	
	private double newX;
	private double newVelocity;
	private double newEnergy;
	private double newPressure;
	private double newRho;
	private double newSmoothLength;
	private double newSoundVelocity;
	
	ListNode preNode;
	ListNode next;

	private double cspmCOE;
	
	private double cspmCOE1;

	public double getCspmCOE() {
		return cspmCOE;
	}
	public void setCspmCOE(double cspmCOE) {
		this.cspmCOE = cspmCOE;
	}
	public double getCspmCOE1() {
		return cspmCOE1;
	}
	public void setCspmCOE1(double cspmCOE1) {
		this.cspmCOE1 = cspmCOE1;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getRho() {
		return rho;
	}

	public void setRho(double rho) {
		this.rho = rho;
	}

	public double getSmoothLength() {
		return smoothLength;
	}

	public void setSmoothLength(double smoothLength) {
		this.smoothLength = smoothLength;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getSoundVelocity() {
		return soundVelocity;
	}

	public void setSoundVelocity(double soundVelocity) {
		this.soundVelocity = soundVelocity;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public double getNewX() {
		return newX;
	}

	public void setNewX(double newX) {
		this.newX = newX;
	}

	public double getNewVelocity() {
		return newVelocity;
	}

	public void setNewVelocity(double newVelocity) {
		this.newVelocity = newVelocity;
	}

	public double getNewEnergy() {
		return newEnergy;
	}

	public void setNewEnergy(double newEnergy) {
		this.newEnergy = newEnergy;
	}

	public double getNewPressure() {
		return newPressure;
	}

	public void setNewPressure(double newPressure) {
		this.newPressure = newPressure;
	}
	public double getNewRho() {
		return newRho;
	}

	public void setNewRho(double newRho) {
		this.newRho = newRho;
	}
	
	public double getNewSmoothLength() {
		return newSmoothLength;
	}
	public void setNewSmoothLength(double newSmoothLength) {
		this.newSmoothLength = newSmoothLength;
	}

	public double getNewSoundVelocity() {
		return newSoundVelocity;
	}

	public void setNewSoundVelocity(double newSoundVelocity) {
		this.newSoundVelocity = newSoundVelocity;
	}

    /***************************************粒子信息复制和更新处理*******************************************/	
	public  void copyNode(ListNode node) {
	
		this.num = node.getNum();
		this.x = node.getX(); 
		this.velocity = node.getVelocity();
		this.energy = node.getEnergy();
		this.pressure = node.getPressure();
		this.rho = node.getRho();
		this.smoothLength = node.getSmoothLength();
		this.soundVelocity = node.getSoundVelocity();
		
		this.type = node.getType();
		this.mass = node.getMass();
		
	}
	
	public  void refreshX() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;
			if (head.getType() == 0)continue;
			head.setX(head.getNewX());
		}

	}
	
	
	public  void refreshVelocity() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;
			if (head.getType() == 0)continue;
			head.setVelocity(head.getNewVelocity());
		}
	}
	
	public  void refreshEnergy() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;			
			if (head.getType() == 0)continue;
			head.setEnergy(head.getNewEnergy());
		}
	}
	public  void refreshPressure() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;			
			if (head.getType() == 0)continue;
			head.setPressure(head.getNewPressure());
			
		}
	
	}
	
	public  void refreshRho() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;			
			if (head.getType() == 0)continue;
			head.setRho(head.getNewRho());
			
		}
	}
	
	public  void refreshSmoothLength() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;			
			if (head.getType() == 0)continue;
			head.setSmoothLength(head.getNewSmoothLength());
		}
	}
	
	public  void refreshSoundVelocity() {
		ListNode head = this;
		while (head.next != null) {
			head = head.next;			
			if (head.getType() == 0)continue;
			head.setSoundVelocity(head.getNewSoundVelocity());
		}
	}
	
}
