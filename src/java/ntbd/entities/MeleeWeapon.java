package ntbd.entities;

public class MeleeWeapon extends Weapon{
	private int bladeLength;

	public int getBladeLength() {
		return bladeLength;
	}

	public void setBladeLength(int bladeLength) {
		this.bladeLength = bladeLength;
	}
	
	public String toString() {
		return " [MeleeWeapon] " + name + " "
				+ code + " "
				+ type + " "
				+ bladeLength + " "
				+ addDate;
	}

}
