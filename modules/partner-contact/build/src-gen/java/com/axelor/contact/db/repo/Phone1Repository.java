package com.axelor.contact.db.repo;

import com.axelor.contact.db.Phone1;
import com.axelor.db.JpaRepository;

public class Phone1Repository extends JpaRepository<Phone1> {

	public Phone1Repository() {
		super(Phone1.class);
	}

}

