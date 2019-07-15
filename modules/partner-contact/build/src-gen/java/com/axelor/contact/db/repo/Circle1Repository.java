package com.axelor.contact.db.repo;

import com.axelor.contact.db.Circle1;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class Circle1Repository extends JpaRepository<Circle1> {

	public Circle1Repository() {
		super(Circle1.class);
	}

	public Circle1 findByCode(String code) {
		return Query.of(Circle1.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Circle1 findByName(String name) {
		return Query.of(Circle1.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

