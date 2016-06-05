package ntbd.utils;

public class BladeInCity {
	private String city;
	private int blade;
	
	public BladeInCity(){}
	
	public BladeInCity(String city, int blade) {
		super();
		this.city = city;
		this.blade = blade;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getBlade() {
		return blade;
	}
	public void setBlade(int blade) {
		this.blade = blade;
	}
	
	public String toStirng(){
		return " [City] " + city + " [Blade] " + blade + " cm";
	}
	

}
