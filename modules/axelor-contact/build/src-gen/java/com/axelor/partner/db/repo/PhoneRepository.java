package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.partner.db.Phone;

public class PhoneRepository extends JpaRepository<Phone> {

	public PhoneRepository() {
		super(Phone.class);
	}

}

