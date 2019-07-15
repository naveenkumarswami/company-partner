package com.axelor.contact.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "CONTACT_COMPANY1")
public class Company1 extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_COMPANY1_SEQ")
	@SequenceGenerator(name = "CONTACT_COMPANY1_SEQ", sequenceName = "CONTACT_COMPANY1_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String code;

	@HashKey
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contact1> employees;

	@Widget(multiline = true)
	private String notes;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Company1() {
	}

	public Company1(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Contact1> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Contact1> employees) {
		this.employees = employees;
	}

	/**
	 * Add the given {@link Contact1} item to the {@code employees}.
	 *
	 * <p>
	 * It sets {@code item.company = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addEmployee(Contact1 item) {
		if (getEmployees() == null) {
			setEmployees(new ArrayList<>());
		}
		getEmployees().add(item);
		item.setCompany(this);
	}

	/**
	 * Remove the given {@link Contact1} item from the {@code employees}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeEmployee(Contact1 item) {
		if (getEmployees() == null) {
			return;
		}
		getEmployees().remove(item);
	}

	/**
	 * Clear the {@code employees} collection.
	 *
	 * <p>
	 * If you have to query {@link Contact1} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearEmployees() {
		if (getEmployees() != null) {
			getEmployees().clear();
		}
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
		if (!(obj instanceof Company1)) return false;

		final Company1 other = (Company1) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getCode(), other.getCode())) return false;
		if (!Objects.equals(getName(), other.getName())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(-535120012, this.getCode(), this.getName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("name", getName())
			.add("notes", getNotes())
			.omitNullValues()
			.toString();
	}
}
