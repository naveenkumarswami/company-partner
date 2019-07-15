package com.axelor.contact.db.repo;

import com.axelor.contact.db.Title1;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class Title1Repository extends JpaRepository<Title1> {

	public Title1Repository() {
		super(Title1.class);
	}

	public Title1 findByCode(String code) {
		return Query.of(Title1.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Title1 findByName(String name) {
		return Query.of(Title1.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

