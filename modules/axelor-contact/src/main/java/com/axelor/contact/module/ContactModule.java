package com.axelor.contact.module;

import com.axelor.app.AxelorModule;
import com.axelor.contact.service.HelloService;
import com.axelor.contact.service.HelloServiceImpl;
import com.axelor.partner.db.repo.PartnerRepository;
import com.axelor.partner.db.repo.card.view.CardView;

public class ContactModule extends AxelorModule {

  @Override
  protected void configure() {
   bind(HelloService.class).to(HelloServiceImpl.class);
    bind(PartnerRepository.class).to(CardView.class);
  }}
