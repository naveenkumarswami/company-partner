package com.axelor.contact.web;

import java.util.Map;
import com.axelor.contact.db.Address1;
import com.axelor.contact.db.Contact1;
import com.axelor.contact.service.HelloServiceContact;
import com.axelor.partner.db.Partner;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Response;
import com.google.inject.Inject;

public class Contact1Controller {

  @Inject private HelloServiceContact service;

  public void say(ActionRequest request, ActionResponse response) {
    Contact1 contact1 = request.getContext().asType(Contact1.class);

    service.say();
    
//    System.err.println(partner.getFirstName()); 

    //response.setNotify(meassage);
//    response.setError(meassage);
    //response.setReload(true);
  }

  public Response validate(String email) {

    Response response = new ActionResponse();

    System.err.println("-"+email+"-"); 
    if (email == "") {
      response.addError("email", "email required");

    } 
    return response;
  }
  public Object updateOrder(Object bean, Map<String, Object> values) {

    values.forEach((key,value)->{
      System.err.println(key+"  "+value); 
    });
    System.err.println(((Partner)bean).getCircleSet()); 
    System.err.println(((Partner)bean).getPhoneList()); 
    return bean;
  }
  
  public void about(ActionRequest request, ActionResponse response) {

    Address1 address = request.getContext().asType(Address1.class);
    Contact1 contact = request.getContext().getParent().asType(Contact1.class);

    String name = contact.getFullName();

    if (name == null) {
      name = contact.getFirstName();
    }

    String message = "Where are you from ?";
    if (address.getCountry() != null)
      message = String.format("'%s' is a beautiful country!", address.getCountry().getName());

    if (name != null) {
      message = String.format("Welcome '%s'...</br>", name) + message;
    }

    response.setFlash(message);
  }
}
