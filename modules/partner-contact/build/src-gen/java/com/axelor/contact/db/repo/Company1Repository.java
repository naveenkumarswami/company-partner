package com.axelor.contact.db.repo;

import com.axelor.contact.db.Company1;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class Company1Repository extends JpaRepository<Company1> {

	public Company1Repository() {
		super(Company1.class);
	}

	public Company1 findByCode(String code) {
		return Query.of(Company1.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Company1 findByName(String name) {
		return Query.of(Company1.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

