package com.axelor.contact.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.axelor.inject.Beans;
import com.axelor.partner.db.Partner;
import com.axelor.partner.db.Phone;
import com.axelor.partner.db.repo.PartnerRepository;
import com.google.inject.persist.Transactional;

public class HelloServiceImplContact implements HelloServiceContact {

  protected Logger log = LoggerFactory.getLogger(getClass());

  @Override
  @Transactional
  public void say() {

    // PartnerRepository contactRepo = Beans.get(PartnerRepository.class);
    // contactRepo.persist(partner);
    // partner = contactRepo.find(partner.getId());

    /*if (partner.getDateOfBirth().compareTo(LocalDate.of(2015, Month.SEPTEMBER, 05)) < 0) {
      // contactRepo.save(partner);
      return String.format("You are eligible '%s' ", partner.getDateOfBirth());
    }

    return String.format(
        "You are are not eligible '%s' must be before 05/09/2015", partner.getDateOfBirth());*/
    System.err.println("service called" ); 
  }

  @Override
  public String hello() {
    return "hello world";
  }

  @Override
  public Object importPhone(String phones) {
    String phoneNo[] = phones.split("|");
    List<Phone> phone = new ArrayList<Phone>();

    for (String no : phoneNo) {
      Phone phon = new Phone();
      phon.setPhone(no);
      phone.add(phon);
    }
    System.out.println(phone ); 
    
    return phone;
  }
}
