package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.partner.db.Title;

public class TitleRepository extends JpaRepository<Title> {

	public TitleRepository() {
		super(Title.class);
	}

	public Title findByCode(String code) {
		return Query.of(Title.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Title findByName(String name) {
		return Query.of(Title.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

