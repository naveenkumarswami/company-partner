package com.axelor.contact.web;

import java.util.Map;
import com.axelor.app.AppSettings;
import com.axelor.contact.service.HelloService;
import com.axelor.partner.db.Partner;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Response;
import com.google.inject.Inject;

public class HelloController {

  @Inject private HelloService service;

  public void say(ActionRequest request, ActionResponse response) {
    Partner partner = request.getContext().asType(Partner.class);

    String meassage = service.say(partner);

    //    System.err.println(partner.getFirstName());

    response.setNotify(meassage);
    //    response.setError(meassage);
    // response.setReload(true);
  }

  public Response validate(String email) {

    Response response = new ActionResponse();

    System.err.println("-" + email + "-");
    if (email == "") {
      response.addError("email", "email required");
    }
    return response;
  }

  public Object updateOrder(Object bean, Map<String, Object> values) {

    values.forEach(
        (key, value) -> {
          System.err.println(key + "  " + value);
        });
    System.err.println(((Partner) bean).getCircleSet());
    System.err.println(((Partner) bean).getPhoneList());
    return bean;
  }

  public void export(ActionRequest request, ActionResponse response) {
    Partner partner = request.getContext().asType(Partner.class);

    //    String meassage = service.say(partner);

    //    System.err.println(partner.getFirstName());

    //    response.setNotify(meassage);
    //    response.setError(meassage);
    // response.setReload(true);

    service.exportCSVFile(partner);
    response.setFlash("downloaded");
    response.setReload(true);
  }

  public void csvimporter(ActionRequest request, ActionResponse response) {
    Partner partner = request.getContext().asType(Partner.class);

    //    String meassage = service.say(partner);

    //    System.err.println(partner.getFirstName());

    //    response.setNotify(meassage);
    //    response.setError(meassage);
    // response.setReload(true);

    service.importCSVfile(partner);
    response.setFlash("imported");
    response.setReload(true);
  }

  //    String imagePath = AppSettings.get().get("file.upload.dir") ;

}
