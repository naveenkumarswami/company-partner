package com.axelor.contact.db.repo;

import com.axelor.contact.db.Address1;
import com.axelor.db.JpaRepository;

public class Address1Repository extends JpaRepository<Address1> {

	public Address1Repository() {
		super(Address1.class);
	}

}

