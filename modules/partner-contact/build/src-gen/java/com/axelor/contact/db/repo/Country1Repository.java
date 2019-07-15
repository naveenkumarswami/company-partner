package com.axelor.contact.db.repo;

import com.axelor.contact.db.Country1;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class Country1Repository extends JpaRepository<Country1> {

	public Country1Repository() {
		super(Country1.class);
	}

	public Country1 findByCode(String code) {
		return Query.of(Country1.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Country1 findByName(String name) {
		return Query.of(Country1.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

