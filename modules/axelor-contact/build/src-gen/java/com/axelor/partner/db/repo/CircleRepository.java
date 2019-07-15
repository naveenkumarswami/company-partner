package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.partner.db.Circle;

public class CircleRepository extends JpaRepository<Circle> {

	public CircleRepository() {
		super(Circle.class);
	}

	public Circle findByCode(String code) {
		return Query.of(Circle.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Circle findByName(String name) {
		return Query.of(Circle.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

