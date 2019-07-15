package com.axelor.partner.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "PARTNER_COMPANY")
public class Company extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTNER_COMPANY_SEQ")
	@SequenceGenerator(name = "PARTNER_COMPANY_SEQ", sequenceName = "PARTNER_COMPANY_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@NameColumn
	@Column(unique = true)
	private String companyName;

	@Widget(title = "Partner")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Partner> partner;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Company() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Partner> getPartner() {
		return partner;
	}

	public void setPartner(List<Partner> partner) {
		this.partner = partner;
	}

	/**
	 * Add the given {@link Partner} item to the {@code partner}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPartner(Partner item) {
		if (getPartner() == null) {
			setPartner(new ArrayList<>());
		}
		getPartner().add(item);
	}

	/**
	 * Remove the given {@link Partner} item from the {@code partner}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePartner(Partner item) {
		if (getPartner() == null) {
			return;
		}
		getPartner().remove(item);
	}

	/**
	 * Clear the {@code partner} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearPartner() {
		if (getPartner() != null) {
			getPartner().clear();
		}
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Company)) return false;

		final Company other = (Company) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getCompanyName(), other.getCompanyName())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(-1679829923, this.getCompanyName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("companyName", getCompanyName())
			.omitNullValues()
			.toString();
	}
}
