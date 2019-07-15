package com.axelor.contact.db;

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
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "CONTACT_EMAIL1", indexes = { @Index(columnList = "contact") })
public class Email1 extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_EMAIL1_SEQ")
	@SequenceGenerator(name = "CONTACT_EMAIL1_SEQ", sequenceName = "CONTACT_EMAIL1_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Contact1 contact;

	@HashKey
	@NameColumn
	@NotNull
	@Column(unique = true)
	private String email;

	@Column(name = "is_primary")
	private Boolean primary = Boolean.FALSE;

	@Widget(title = "Opted out")
	private Boolean optOut = Boolean.FALSE;

	private Boolean invalid = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Email1() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Contact1 getContact() {
		return contact;
	}

	public void setContact(Contact1 contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPrimary() {
		return primary == null ? Boolean.FALSE : primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public Boolean getOptOut() {
		return optOut == null ? Boolean.FALSE : optOut;
	}

	public void setOptOut(Boolean optOut) {
		this.optOut = optOut;
	}

	public Boolean getInvalid() {
		return invalid == null ? Boolean.FALSE : invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
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
		if (!(obj instanceof Email1)) return false;

		final Email1 other = (Email1) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getEmail(), other.getEmail())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(2079069237, this.getEmail());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("email", getEmail())
			.add("primary", getPrimary())
			.add("optOut", getOptOut())
			.add("invalid", getInvalid())
			.omitNullValues()
			.toString();
	}
}
