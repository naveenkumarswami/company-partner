package com.axelor.contact.service;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.axelor.app.AppSettings;
import com.axelor.data.Importer;
import com.axelor.data.Listener;
import com.axelor.data.csv.CSVImporter;
import com.axelor.db.Model;
import com.axelor.inject.Beans;
import com.axelor.partner.db.Circle;
import com.axelor.partner.db.Partner;
import com.axelor.partner.db.Phone;
import com.axelor.partner.db.repo.PartnerRepository;
import com.google.inject.persist.Transactional;
import com.opencsv.CSVWriter;

public class HelloServiceImpl implements HelloService {

  protected Logger log = LoggerFactory.getLogger(getClass());

  @Override
  @Transactional
  public String say(Partner partner) {

    // PartnerRepository contactRepo = Beans.get(PartnerRepository.class);
    // contactRepo.persist(partner);
    // partner = contactRepo.find(partner.getId());

    if (partner.getDateOfBirth().compareTo(LocalDate.of(2015, Month.SEPTEMBER, 05)) < 0) {
      // contactRepo.save(partner);
      return String.format("You are eligible '%s' ", partner.getDateOfBirth());
    }
   

    return String.format(
        "You are are not eligible '%s' must be before 05/09/2015", partner.getDateOfBirth());
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
    System.out.println(phone);

    return phone;
  }

  @Override
  public void exportCSVFile(Partner partner) {

    PartnerRepository contactRepo = Beans.get(PartnerRepository.class);
    partner = contactRepo.find(partner.getId());


    // first create file object for file placed at location
    // specified by filepath
    File file = new File("/home/axelor/Downloads/csvexport/"+partner.getFirstName()+".csv");
    try {
      // create filewriter object with file as parameter
      FileWriter outputfile = new FileWriter(file);

      // create CSVWriter object filewriter object as parameter
      CSVWriter writer = new CSVWriter(outputfile);

      // adding header to csv
      String[] header = {"title", "firstName", "lastName", "PhoneNumber", "circle"};
      writer.writeNext(header);

      // for the phone list get
      String csvPhoneList = "", prifix = "", seperator = "|";
      List<Phone> phoneNumberList = partner.getPhoneList();
      for (Phone phone : phoneNumberList) {
        csvPhoneList += prifix + phone.getPhone();
        prifix = seperator;
      }

      // for the circle list get
      String csvCircleSet = "";
      prifix ="";
      Set<Circle> circleSet = partner.getCircleSet();
      for (Circle circle : circleSet) {
        csvCircleSet +=prifix+ circle.getName();
        prifix = seperator;
      }

      // add data to csv
      String[] data1 = {partner.getTitle().getName(),partner.getFirstName(),partner.getLastName(),csvPhoneList,csvCircleSet};
      writer.writeNext(data1);
      writer.close(); 
      System.out.println("done!!!" ); 


    } catch (Exception e) {

      e.printStackTrace();
    }
  }

  @Override
  public void importCSVfile(Partner partner) {

    Importer importer = new CSVImporter("/home/axelor/Data/input-config.xml","/home/axelor/Data/input/");

       Listener listener = new Listener(){

       @Override
       public void imported(Integer total, Integer success) {
         System.out.println(total );
       }

       @Override
       public void imported(Model bean) {
         System.out.println(bean );
       }

       @Override
       public void handle(Model bean, Exception e) {
         System.out.println("error:" +bean +  "   "  + e);
       }
     };

    importer.addListener(listener); 
    System.err.println( "import started"); 
    importer.run();
  }
}
