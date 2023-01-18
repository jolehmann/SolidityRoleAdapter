package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Role {
	final String name;
	public Role (String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		final Role other = (Role) obj;
		return this.name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
