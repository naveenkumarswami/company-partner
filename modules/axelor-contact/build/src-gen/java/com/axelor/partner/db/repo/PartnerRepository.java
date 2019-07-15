package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.partner.db.Partner;

public class PartnerRepository extends JpaRepository<Partner> {

	public PartnerRepository() {
		super(Partner.class);
	}

	public Partner findByCode(String code) {
		return Query.of(Partner.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	final static int id=1;
	final static String name="naveen";
	final static String phoneNo="9587403395";
}

