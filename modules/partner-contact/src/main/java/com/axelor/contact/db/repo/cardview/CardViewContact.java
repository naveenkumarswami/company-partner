package com.axelor.contact.db.repo.cardview;

import java.util.Map;
import com.axelor.contact.db.Contact1;
import com.axelor.contact.db.repo.Contact1Repository;

public class CardViewContact extends Contact1Repository {

  @Override
  public Map<String, Object> populate(Map<String, Object> json, Map<String, Object> context) {
   
    if(!context.containsKey("json-enhance"))
    {
      return json;
    }
    try {
      Long id=(Long)json.get("id");
      Contact1 contact = find(id);
      //json.put("address", contact.getAddresses().get(0));
      json.put("hasImage", contact.getImage()!=null);
     System.err.println("sucess" ); 
    } catch (Exception e) {
    System.err.println(e.getStackTrace()); 
    }
    return json;
  }
  
  @Override
  public Contact1 save(Contact1 entity) {
    System.err.println("save" ); 
    return super.save(entity);
  }
}
