package com.example.linkedinproj.Controller;

import com.example.linkedinproj.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    public static final Map<String, User> map = new HashMap<>();
    public static final Map<User, List<String>> graphMap = new HashMap<>();

    public static void readUsersFromJSONFile() throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\users.json"));
        User user;
        for (Object o : a){
            JSONObject person = (JSONObject) o;
            String id = (String) person.get("id");
            user = new User(id);
            String name = (String) person.get("name");
            user.setName(name);
            String dateOfBirth = (String) person.get("dateOfBirth");
            user.setDateOfBirth(dateOfBirth);
            String universityLocation = (String) person.get("universityLocation");
            user.setUniversityLocation(universityLocation);
            String field = (String) person.get("field");
            user.setField(field);
            String workplace = (String) person.get("workplace");
            user.setWorkplace(workplace);
            JSONArray specialties = (JSONArray) person.get("specialties");
            for(Object s : specialties){
                user.getSpecialties().add((String) s);
            }
            JSONArray connectionId = (JSONArray) person.get("connectionId");
            for(Object p : connectionId){
                user.getConnectionId().add((String) p);
            }
            map.put(id,user);
        }
    }

    public static void setGraphMap(){
        for (User u : map.values()){
            graphMap.put(u,u.getConnectionId());
        }
    }



}
