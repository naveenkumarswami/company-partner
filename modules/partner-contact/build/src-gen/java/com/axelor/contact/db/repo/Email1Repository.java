package com.axelor.contact.db.repo;

import com.axelor.contact.db.Email1;
import com.axelor.db.JpaRepository;

public class Email1Repository extends JpaRepository<Email1> {

	public Email1Repository() {
		super(Email1.class);
	}

}

