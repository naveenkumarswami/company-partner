package com.axelor.contact.db.repo;

import com.axelor.contact.db.Contact1;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class Contact1Repository extends JpaRepository<Contact1> {

	public Contact1Repository() {
		super(Contact1.class);
	}

	public Contact1 findByName(String fullName) {
		return Query.of(Contact1.class)
				.filter("self.fullName = :fullName")
				.bind("fullName", fullName)
				.fetchOne();
	}

	public Contact1 findByEmail(String email) {
		return Query.of(Contact1.class)
				.filter("self.emails[].email = :email")
				.bind("email", email)
				.fetchOne();
	}

}

