package com.axelor.contact.service;

import com.axelor.partner.db.Partner;

public interface HelloService {

  String say(Partner partner);

  String hello();
  
  Object importPhone(String phones);
  
  void exportCSVFile(Partner partner); 
  
  void importCSVfile(Partner partner);

}

