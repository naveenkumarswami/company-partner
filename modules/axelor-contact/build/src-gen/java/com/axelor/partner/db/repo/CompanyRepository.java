package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.partner.db.Company;

public class CompanyRepository extends JpaRepository<Company> {

	public CompanyRepository() {
		super(Company.class);
	}

}

