package com.axelor.partner.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Sequence;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@DynamicInsert
@DynamicUpdate
@Table(name = "PARTNER_PARTNER", indexes = { @Index(columnList = "title"), @Index(columnList = "email"), @Index(columnList = "company"), @Index(columnList = "code"), @Index(columnList = "image") })
public class Partner extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTNER_PARTNER_SEQ")
	@SequenceGenerator(name = "PARTNER_PARTNER_SEQ", sequenceName = "PARTNER_PARTNER_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Title title;

	@Widget(title = "First Name")
	@NotNull
	private String firstName;

	private String lastName;

	@Widget(title = "fixed Date Of Birth", hidden = true)
	private LocalDate fixedDate = LocalDate.parse("2000-11-22");

	@Widget(search = { "firstName", "lastName" })
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Email")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Email email;

	@Widget(title = "Address")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> address;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Date Of Birth")
	private LocalDate dateOfBirth;

	@Widget(title = "Phone")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Phone> phoneList;

	@Widget(title = "Code")
	@NotNull
	@Sequence("sale.order.seq")
	private String code;

	@Widget(title = "Circle")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Circle> circleSet;

	@Widget(title = "Image")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile image;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Partner() {
	}

	public Partner(String code) {
		this.code = code;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getFixedDate() {
		return fixedDate;
	}

	public void setFixedDate(LocalDate fixedDate) {
		this.fixedDate = fixedDate;
	}

	public String getFullName() {
		try {
			fullName = computeFullName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getFullName()", e);
		}
		return fullName;
	}

	protected String computeFullName() {
		if (firstName == null && lastName == null)
		  return null;
		if (title == null)
			return firstName + " " + lastName;
		return title.getName() + " " + firstName + " " + lastName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	/**
	 * Add the given {@link Address} item to the {@code address}.
	 *
	 * <p>
	 * It sets {@code item.partner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAddress(Address item) {
		if (getAddress() == null) {
			setAddress(new ArrayList<>());
		}
		getAddress().add(item);
		item.setPartner(this);
	}

	/**
	 * Remove the given {@link Address} item from the {@code address}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAddress(Address item) {
		if (getAddress() == null) {
			return;
		}
		getAddress().remove(item);
	}

	/**
	 * Clear the {@code address} collection.
	 *
	 * <p>
	 * If you have to query {@link Address} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAddress() {
		if (getAddress() != null) {
			getAddress().clear();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

	/**
	 * Add the given {@link Phone} item to the {@code phoneList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPhoneListItem(Phone item) {
		if (getPhoneList() == null) {
			setPhoneList(new ArrayList<>());
		}
		getPhoneList().add(item);
	}

	/**
	 * Remove the given {@link Phone} item from the {@code phoneList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePhoneListItem(Phone item) {
		if (getPhoneList() == null) {
			return;
		}
		getPhoneList().remove(item);
	}

	/**
	 * Clear the {@code phoneList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearPhoneList() {
		if (getPhoneList() != null) {
			getPhoneList().clear();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Circle> getCircleSet() {
		return circleSet;
	}

	public void setCircleSet(Set<Circle> circleSet) {
		this.circleSet = circleSet;
	}

	/**
	 * Add the given {@link Circle} item to the {@code circleSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCircleSetItem(Circle item) {
		if (getCircleSet() == null) {
			setCircleSet(new HashSet<>());
		}
		getCircleSet().add(item);
	}

	/**
	 * Remove the given {@link Circle} item from the {@code circleSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCircleSetItem(Circle item) {
		if (getCircleSet() == null) {
			return;
		}
		getCircleSet().remove(item);
	}

	/**
	 * Clear the {@code circleSet} collection.
	 *
	 */
	public void clearCircleSet() {
		if (getCircleSet() != null) {
			getCircleSet().clear();
		}
	}

	public MetaFile getImage() {
		return image;
	}

	public void setImage(MetaFile image) {
		this.image = image;
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
		if (!(obj instanceof Partner)) return false;

		final Partner other = (Partner) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("firstName", getFirstName())
			.add("lastName", getLastName())
			.add("fixedDate", getFixedDate())
			.add("dateOfBirth", getDateOfBirth())
			.add("code", getCode())
			.omitNullValues()
			.toString();
	}
}
