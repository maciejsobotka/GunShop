package ntbd.entities;

public class Firearm extends Weapon{
	private int barrelLength;

	public int getBarrelLength() {
		return barrelLength;
	}

	public void setBarrelLength(int barrelLength) {
		this.barrelLength = barrelLength;
	}
	
	public String toString() {
		return " [Weapon] " + name + " " + code + " " + type;
	}
}
