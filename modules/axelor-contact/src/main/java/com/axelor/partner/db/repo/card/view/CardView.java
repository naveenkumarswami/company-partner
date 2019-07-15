package com.axelor.partner.db.repo.card.view;

import java.util.Map;
import com.axelor.partner.db.Partner;
import com.axelor.partner.db.repo.PartnerRepository;

public class CardView extends PartnerRepository {

  @Override
  public Map<String, Object> populate(Map<String, Object> json, Map<String, Object> context) {
    if (!context.containsKey("json-enhance")) {
      return json;
    }
    try {
      Long id = (Long) json.get("id");
      Partner partner = find(id);
      //json.put("address", partner.getAddress().get(0));  // here thoese variable used which used in template tag
      json.put("hasImage", partner.getImage() != null);
      json.put("phone", partner.getPhoneList().get(0));
      
    } catch (Exception e) {
      System.err.println(e.getStackTrace()); 
    }

    return json;
  }
}
