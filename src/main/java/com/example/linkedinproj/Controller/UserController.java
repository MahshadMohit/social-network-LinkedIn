package com.example.linkedinproj.Controller;

import com.example.linkedinproj.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class UserController {

    public static final Map<String, User> map = new HashMap<>();//nodes or person
    public static final Map<User, List<String>> graphMap = new HashMap<>();//adjList

    public static void readUsersFromJSONFile() throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\users.json"));
        User user;
        for (Object o : a) {
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
            for (Object s : specialties) {
                user.getSpecialties().add((String) s);
            }
            JSONArray connectionId = (JSONArray) person.get("connectionId");
            for (Object p : connectionId) {
                user.getConnectionId().add((String) p);
            }
            map.put(id, user);
        }
    }

    public static void setGraphMap() {
        for (User u : map.values()) {
            graphMap.put(u, u.getConnectionId());
        }
    }

    public void suggestionList(String id) {//make it return list later
        Map<Integer, List<String>> scores = calculateScore(id);
        PriorityQueue<Integer> sortedScores = new PriorityQueue<>(Comparator.reverseOrder());
        sortedScores.addAll(scores.keySet());
        List<String> suggestions = new ArrayList<>();
        int counter=1;
        while (counter<=20) {
            suggestions.add(String.valueOf(sortedScores.poll()));
            counter++;
        }
        System.out.println("WE SUGGEST THE PEOPLE BELOW FOR YOU");
        System.out.println(suggestions);
    }


    public List<String> findTheClosest(String id) {
        Queue<String> neighbours = new LinkedList<>(graphMap.get(map.get(id)));
        List<String> result = new ArrayList<>();
        findTheClosest(result, neighbours, 2);
        return result;
    }

    private void findTheClosest(List<String> result, Queue<String> queue, int degree) {

        if (degree > 5)
            return;

        List<String> Dlist = new ArrayList<>();//for every degree
        int limitation = 20 / degree + 2;

        if (degree == 4 || degree == 5)
            limitation = limitation - 1;

        while (Dlist.size() <= limitation) {
            String current = queue.remove();
            Dlist.addAll(graphMap.get(map.get(current)));
            Collections.shuffle((List<?>) queue);
        }
        queue.clear();
        result.addAll(Dlist);

        findTheClosest(result, queue, degree + 1);
    }


    public Map<Integer, List<String>> calculateScore(String mainId) {
        List<String> resultId = findTheClosest(mainId);
        var person = map.get(mainId);
        Map<Integer, List<String>> scores = new HashMap<>();
        calculate(resultId, person, scores);
        return scores;
    }

    private void calculate(List<String> idList, User mainPerson, Map<Integer, List<String>> scores) {
        int score;
        for (String id : idList) {
            var user = map.get(id);
            score = (sameJob(user, mainPerson) + sameMajor(user, mainPerson)
                    + sameUniversity(user, mainPerson) + specialist(user, mainPerson));
            if (!scores.containsKey(score)) {
                List<String> list = new ArrayList<>();
                list.add(id);
                scores.put(score, list);
            } else {
                scores.get(score).add(id);
            }
        }
    }


    private int sameUniversity(User user, User mainPerson) {
        return (mainPerson.getUniversityLocation().equals(user.getUniversityLocation()) ? 1 : 0);
    }

    private int sameJob(User user, User mainPerson) {
        return (mainPerson.getWorkplace().equals(user.getWorkplace()) ? 2 : 0);
    }

    private int sameMajor(User user, User mainPerson) {
        return (mainPerson.getField().equals(user.getField()) ? 2 : 0);
    }


    private int specialist(User user, User mainPerson) {

        if (user.getSpecialties().size() > mainPerson.getSpecialties().size()) {
            return 2;
        } else if (user.getSpecialties().size() == mainPerson.getSpecialties().size()) {
            return 1;
        }
        return 0;
    }
}
