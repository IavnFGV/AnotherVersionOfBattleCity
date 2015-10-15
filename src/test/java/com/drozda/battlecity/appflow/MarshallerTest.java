package com.drozda.battlecity.appflow;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 15.10.2015.
 */
public class MarshallerTest {
    public static void main(String[] args) throws JAXBException {
        Map<Integer, String> map = new HashMap<Integer, String>();


        map.put(1, "emp1");
        map.put(2, "emp2");

        //Add employees in map
        EmployeeMap employeeMap = new EmployeeMap();
        employeeMap.setEmployeeMap(map);

        /******************** Marshalling example *****************************/

        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeMap.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(employeeMap, System.out);
//        jaxbMarshaller.marshal(employeeMap, new File("c:/temp/employees.xml"));
    }

    @XmlRootElement(name = "employees")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class EmployeeMap {

        private Map<Integer, String> employeeMap = new HashMap<Integer, String>();

        public Map<Integer, String> getEmployeeMap() {
            return employeeMap;
        }

        public void setEmployeeMap(Map<Integer, String> employeeMap) {
            this.employeeMap = employeeMap;
        }
    }

}
