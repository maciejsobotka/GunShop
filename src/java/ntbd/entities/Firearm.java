package ntbd.entities;

import java.util.Set;

public class Firearm extends Weapon{
	private int barrelLength;
	private Set<Accessory> accessories;

	public int getBarrelLength() {
		return barrelLength;
	}
	public void setBarrelLength(int barrelLength) {
		this.barrelLength = barrelLength;
	}
	public Set<Accessory> getAccessories() {
		return accessories;
	}
	public void setAccessories(Set<Accessory> accessories) {
		this.accessories = accessories;
	}
	
	public String toString() {
		return " [Firearm] " + name + " "
				+ code + " "
				+ type + " "
				+ barrelLength + " "
				+ addDate;
	}
}
