package com.axelor.contact.db;

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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@DynamicInsert
@DynamicUpdate
@Table(name = "CONTACT_CONTACT1", indexes = { @Index(columnList = "title"), @Index(columnList = "fullName"), @Index(columnList = "company") })
public class Contact1 extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_CONTACT1_SEQ")
	@SequenceGenerator(name = "CONTACT_CONTACT1_SEQ", sequenceName = "CONTACT_CONTACT1_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Title1 title;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	private LocalDate dateOfBirth;

	@Widget(image = true, title = "Photo", help = "Max size 4MB.")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String notes;

	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String email;

	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String phone;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Email1> emails;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Phone1> phones;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address1> addresses;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Circle1> circles;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company1 company;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Contact1() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Title1 getTitle() {
		return title;
	}

	public void setTitle(Title1 title) {
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Max size 4MB.
	 *
	 * @return the property value
	 */
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getEmail() {
		try {
			email = computeEmail();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getEmail()", e);
		}
		return email;
	}

	protected String computeEmail() {
		if (emails == null || emails.isEmpty()) return null;
		for (Email1 email : emails) if (email.getPrimary() == Boolean.TRUE) return email.getEmail();
		return emails.get(0).getEmail();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		try {
			phone = computePhone();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getPhone()", e);
		}
		return phone;
	}

	protected String computePhone() {
		if (phones == null || phones.isEmpty()) return null;
		for (Phone1 phone : phones) if (phone.getPrimary() == Boolean.TRUE) return phone.getPhone();
		return phones.get(0).getPhone();
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Email1> getEmails() {
		return emails;
	}

	public void setEmails(List<Email1> emails) {
		this.emails = emails;
	}

	/**
	 * Add the given {@link Email1} item to the {@code emails}.
	 *
	 * <p>
	 * It sets {@code item.contact = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addEmail(Email1 item) {
		if (getEmails() == null) {
			setEmails(new ArrayList<>());
		}
		getEmails().add(item);
		item.setContact(this);
	}

	/**
	 * Remove the given {@link Email1} item from the {@code emails}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeEmail(Email1 item) {
		if (getEmails() == null) {
			return;
		}
		getEmails().remove(item);
	}

	/**
	 * Clear the {@code emails} collection.
	 *
	 * <p>
	 * If you have to query {@link Email1} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearEmails() {
		if (getEmails() != null) {
			getEmails().clear();
		}
	}

	public List<Phone1> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone1> phones) {
		this.phones = phones;
	}

	/**
	 * Add the given {@link Phone1} item to the {@code phones}.
	 *
	 * <p>
	 * It sets {@code item.contact = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPhone(Phone1 item) {
		if (getPhones() == null) {
			setPhones(new ArrayList<>());
		}
		getPhones().add(item);
		item.setContact(this);
	}

	/**
	 * Remove the given {@link Phone1} item from the {@code phones}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePhone(Phone1 item) {
		if (getPhones() == null) {
			return;
		}
		getPhones().remove(item);
	}

	/**
	 * Clear the {@code phones} collection.
	 *
	 * <p>
	 * If you have to query {@link Phone1} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPhones() {
		if (getPhones() != null) {
			getPhones().clear();
		}
	}

	public List<Address1> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address1> addresses) {
		this.addresses = addresses;
	}

	/**
	 * Add the given {@link Address1} item to the {@code addresses}.
	 *
	 * <p>
	 * It sets {@code item.contact = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAddress(Address1 item) {
		if (getAddresses() == null) {
			setAddresses(new ArrayList<>());
		}
		getAddresses().add(item);
		item.setContact(this);
	}

	/**
	 * Remove the given {@link Address1} item from the {@code addresses}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAddress(Address1 item) {
		if (getAddresses() == null) {
			return;
		}
		getAddresses().remove(item);
	}

	/**
	 * Clear the {@code addresses} collection.
	 *
	 * <p>
	 * If you have to query {@link Address1} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAddresses() {
		if (getAddresses() != null) {
			getAddresses().clear();
		}
	}

	public Set<Circle1> getCircles() {
		return circles;
	}

	public void setCircles(Set<Circle1> circles) {
		this.circles = circles;
	}

	/**
	 * Add the given {@link Circle1} item to the {@code circles}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCircle(Circle1 item) {
		if (getCircles() == null) {
			setCircles(new HashSet<>());
		}
		getCircles().add(item);
	}

	/**
	 * Remove the given {@link Circle1} item from the {@code circles}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCircle(Circle1 item) {
		if (getCircles() == null) {
			return;
		}
		getCircles().remove(item);
	}

	/**
	 * Clear the {@code circles} collection.
	 *
	 */
	public void clearCircles() {
		if (getCircles() != null) {
			getCircles().clear();
		}
	}

	public Company1 getCompany() {
		return company;
	}

	public void setCompany(Company1 company) {
		this.company = company;
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
		if (!(obj instanceof Contact1)) return false;

		final Contact1 other = (Contact1) obj;
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
			.add("dateOfBirth", getDateOfBirth())
			.omitNullValues()
			.toString();
	}
}
