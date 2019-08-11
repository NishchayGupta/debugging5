/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package debugging5;

import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author 1895314
 */
public class Debugging5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String json = FileReader.loadFileIntoString("json/Manager.json", "UTF-8");
        //System.out.println(json);
        JSONObject manager = JSONObject.fromObject(json);
        JSONObject mainObj = new JSONObject();
        String id, year, department = "", currentResult;
        int yearHire, totalEmployees=0;
        double averageMonths=0.0;
        JSONArray employees;
        JSONObject totEmp;
        JSONArray allDepartments = new JSONArray();
        boolean current;
        id = manager.getString("manager_number");
        id += " - " + manager.getString("first_name");
        mainObj.accumulate("ID", id);
        
        year = manager.getString("date_hire");
        yearHire = Integer.parseInt(year.substring(10));
        mainObj.accumulate("year_hire", yearHire);
        employees = manager.getJSONArray("departments");
        
        for(int i=0; i<employees.size(); i++)
        {
            totEmp = employees.getJSONObject(i);
            totalEmployees += totEmp.getInt("numberEmployees");
            averageMonths += totEmp.getDouble("months");
            department = totEmp.getString("department_id");
            department += " - ";
            department += totEmp.getString("department_name");
            current = totEmp.getBoolean("current");
            if(current)
            {
                currentResult = "Is current";
            }
            else
                currentResult = "Is Not current";
            
            JSONObject departments = new JSONObject();
            departments.accumulate("department", department);
            departments.accumulate("current", currentResult);
            allDepartments.add(departments);
            
            
        }
        averageMonths = averageMonths/employees.size();
        mainObj.accumulate("totalEmployee", totalEmployees);
        mainObj.accumulate("averageMonths", averageMonths);
        mainObj.accumulate("departments", allDepartments);
        
        
        
        FileWriter.saveStringIntoFile("json/managerResultNew.json", mainObj.toString());
    }
    
}