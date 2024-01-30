package com.example.linkedinproj.Controller;

import com.example.linkedinproj.model.Graph;
import com.example.linkedinproj.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class UserController {
    private Graph.GraphWithEdgeList graph=new Graph.GraphWithEdgeList();


    public static final Map<String, User> map = new HashMap<>();//nodes or person
    public static final Map<User, List<String>> graphMap = new HashMap<>();//adjList

    public static final Map<String, String> options = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    private static List<String> priorities = new ArrayList<>();

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
        int counter = 1;
        while (counter <= 20) {
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
            score = (sameWorkPlace(user, mainPerson) + sameMajor(user, mainPerson)
                    + sameUniversity(user, mainPerson) + specialties(user, mainPerson));
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
        int score = 1;
        score = getScore("UniversityPlace", score);
        return (mainPerson.getUniversityLocation().equals(user.getUniversityLocation()) ? score : 0);
    }

    private int getScore(String str, int score) {

        if (isSpecialSelection()) {
            Map<String, Integer> map = selectedOne();
            if (map.containsKey(str)) {
                score = (4 - map.get(str)) * score;
            }
        }
        return score;
    }

    private int sameWorkPlace(User user, User mainPerson) {
        int score = 2;
        score = getScore("WorkPlace", score);
        return (mainPerson.getWorkplace().equals(user.getWorkplace()) ? score : 0);
    }

    private int sameMajor(User user, User mainPerson) {
        int score = 2;
        score = getScore("Field", score);
        return (mainPerson.getField().equals(user.getField()) ? score : 0);
    }


    private int specialties(User user, User mainPerson) {
        int score = getScore("Specialities", 1);

        if (user.getSpecialties().size() > mainPerson.getSpecialties().size()) {
            return 2+score;
        } else if (user.getSpecialties().size() == mainPerson.getSpecialties().size()) {
            return 1+score;
        }
        return score;
    }

    public boolean isSpecialSelection() {
        return !priorities.isEmpty();
    }

    public void prioritization() {
        System.out.println("enter your priorities with  number in order");
        System.out.println("1:UniversityPlace");
        System.out.println("2:WorkPlace");
        System.out.println("3:Field");
        System.out.println("4:Specialties");
        fillTheOption();
        String input = scanner.next();
        priorities.addAll(List.of(input.split(" ")));

    }

    public Map<String, Integer> selectedOne() {
        prioritization();
        Map<String, Integer> selected = new HashMap<>();
        int i = 0;
        while (!priorities.isEmpty()) {
            selected.put(options.get(priorities.remove(0)), i);
            i++;

        }
        return selected;
    }

    public void fillTheOption() {
        options.put("1", "UniversityPlace");
        options.put("2", "WorkPlace");
        options.put("3", "Field");
        options.put("4", "Specialties");
    }




    public void buildGraph(){
        for (var label:map.keySet()) {
            var user=map.get(label);
            graph.addNode(label);

            for (var id:graphMap.get(user)) {
                graph.addEdge(user.getId(), map.get(id).getId(),1);
            }
        }
    }
}
