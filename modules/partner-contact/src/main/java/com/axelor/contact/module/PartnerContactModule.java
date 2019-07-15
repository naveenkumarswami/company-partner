package com.axelor.contact.module;

import com.axelor.app.AxelorModule;
import com.axelor.contact.db.repo.Contact1Repository;
import com.axelor.contact.db.repo.cardview.CardViewContact;
import com.axelor.contact.service.HelloServiceContact;
import com.axelor.contact.service.HelloServiceImplContact;

public class PartnerContactModule extends AxelorModule {

  @Override
  protected void configure() {
   bind(HelloServiceContact.class).to(HelloServiceImplContact.class);
   bind(Contact1Repository.class).to(CardViewContact.class);
  }}
