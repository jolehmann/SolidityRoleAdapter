package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.List;
import java.util.Set;

public class TupleRoles implements Roles {

	private SingleRoles modificationRoles;
	private SingleRoles influenceRoles;

	public TupleRoles(SingleRoles modificationRoles, SingleRoles influenceRoles) {
		this.modificationRoles = modificationRoles;
		this.influenceRoles = influenceRoles;
	}

	public SingleRoles getModificationRoles() {
		return modificationRoles;
	}

	public SingleRoles getInfluenceRoles() {
		return influenceRoles;
	}

	/**
	 * Adds the otherRoles to both: modificationRoles and influenceRoles.
	 */
	@Override
	public Roles add(Roles otherRoles) {
		this.modificationRoles.add(otherRoles);
		this.influenceRoles.add(otherRoles);
		return this;
	}

	/**
	 * Adds the modificationRoles of otherRoles to this.modificationRoles and
	 * influenceRoles of otherRoles to this.influenceRoles.
	 * 
	 * @return this for convenience
	 */
	public Roles add(TupleRoles otherRoles) {
		this.modificationRoles.add(otherRoles.getModificationRoles());
		this.influenceRoles.add(otherRoles.getInfluenceRoles());
		return this;
	}

	/**
	 * Returns union of all roles of modificationRoles and influenceRoles.
	 */
	@Override
	public Set<Role> toSet() {
		Set<Role> rolesCopy = this.modificationRoles.toSet();
		rolesCopy.addAll(this.influenceRoles.toSet());
		return rolesCopy;
	}

	@Override
	public Roles copy() {
		return new TupleRoles((SingleRoles) this.modificationRoles.copy(), (SingleRoles) this.influenceRoles.copy());
	}

	@Override
	public Roles copyAndAdd(Roles otherRoles) {
		return this.copy().add(otherRoles);
	}

	/**
	 * Returns true if modificationRoles and influenceRoles both contains the role.
	 */
	@Override
	public boolean contains(Role role) {
		return this.modificationRoles.contains(role) && this.influenceRoles.contains(role);
	}

	@Override
	public boolean contains(Roles otherRoles) {
		return this.modificationRoles.contains(otherRoles) && this.influenceRoles.contains(otherRoles);
	}

	public boolean contains(TupleRoles otherRoles) {
		return this.modificationRoles.contains(otherRoles.getModificationRoles())
				&& this.influenceRoles.contains(otherRoles.getInfluenceRoles());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		final TupleRoles other = (TupleRoles) obj;
		return this.contains(other) && other.contains(this);
	}
	
	public String toString() {
		return this.modificationRoles.toString() + " " + this.influenceRoles.toString();
	}
	
	public List<String> getModRolesMinus(TupleRoles otherRoles) {
		return this.getModificationRoles().getMinus(otherRoles.getModificationRoles());
	}
	
	public List<String> getInflRolesMinus(TupleRoles otherRoles) {
		return this.getInfluenceRoles().getMinus(otherRoles.getInfluenceRoles());
	}
}
