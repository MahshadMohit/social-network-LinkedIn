package com.example.linkedinproj.Controller;

import com.example.linkedinproj.model.Graph;
import com.example.linkedinproj.model.User;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class UserController {
    private static Graph.GraphWithEdgeList graph = new Graph.GraphWithEdgeList();
    private static Graph.GraphWithAdjacencyList adGraph = new Graph.GraphWithAdjacencyList();
    public static ArrayList<String> posts = new ArrayList<>();
    public static ArrayList<String> postUsers = new ArrayList<>();
    public static List<String> postComments = new ArrayList<>();
    public static List<String> userComments = new ArrayList<>();
    public static ArrayList<Image> images = new ArrayList<>();
    public List<String> postsOfConsole = new ArrayList<>();
    public List<List<String>> comment = new ArrayList<>();
    public List<Integer> likes = new ArrayList<>();


    public static final Map<String, User> map = new HashMap<>();//nodes or person
    public static final Map<User, List<String>> graphMap = new HashMap<>();//adjList

    public static final Map<String, String> options = new HashMap<>(); // use for options
    static Scanner scanner = new Scanner(System.in);
    private static List<String> priorities = new ArrayList<>();

    public UserController() throws IOException, ParseException, org.json.simple.parser.ParseException {
        readUsersFromJSONFile();
        setGraphMap();
    }

    public static void readUsersFromJSONFile() throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("src/main/java/users.json"));
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

    // login
    public static User login(String id) {
        return map.get(id);

    }


    public void follow(User user, User user1) {
        user.getConnectionId().add(user1.getId());
        user1.getConnectionId().add(user.getId());
        //graph.addEdge(user.getId(), user1.getId(), 1);
    }

    public List<String> suggestionList(String id) {//make it return list later

        Map<Integer, List<String>> scores = calculateScore(id);
        PriorityQueue<Integer> sortedScores = new PriorityQueue<>(Comparator.reverseOrder());
        sortedScores.addAll(scores.keySet());
        List<String> suggestions = new ArrayList<>();
        int score;
        while (!sortedScores.isEmpty()) {
            score = sortedScores.poll();
            suggestions.addAll(scores.get(score));
        }
        return suggestions;
    }

    public List<String> finalResult(String id, String input) {
        prioritization(input);
        return suggestionList(id);
    }

    public Set<String> findTheClosest(String id) {
        Queue<String> neighbours = new LinkedList<>(graphMap.get(map.get(id)));
        Set<String> result = new HashSet<>();
        findTheClosest(result, neighbours, 2);
        return result;
    }

    private void findTheClosest(Set<String> result, Queue<String> queue, int degree) {

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
        queue.addAll(Dlist);
        Dlist.clear();
        findTheClosest(result, queue, degree + 1);
    }


    public Map<Integer, List<String>> calculateScore(String id) {
        Set<String> resultId = findTheClosest(id);
        var person = map.get(id);
        Map<Integer, List<String>> scores = new HashMap<>();
        calculate(resultId, person, scores);
        return scores;
    }

    private void calculate(Set<String> idList, User mainPerson, Map<Integer, List<String>> scores) {
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
            return 2 + score;
        } else if (user.getSpecialties().size() == mainPerson.getSpecialties().size()) {
            return 1 + score;
        }
        return score;
    }

    public boolean isSpecialSelection() {
        return !priorities.isEmpty();
    }

    // 1 2 3
    public void prioritization(String input) {

        fillTheOption();
        priorities.addAll(List.of(input.split(" ")));

    }

    public Map<String, Integer> selectedOne() {

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


    public static void buildGraph() {

        for (String label : map.keySet()) {
            User user = map.get(label);
            graph.addNode(label);
            adGraph.addNode(label);

            for (String id : graphMap.get(user)) {
                    User neighbour = map.get(id);
                    graph.addNode(neighbour.getId());
                    graph.addEdge(user.getId(), map.get(id).getId(), 1);
                    adGraph.addNode(neighbour.getId());
                    adGraph.addEdge(user.getId(), neighbour.getId(), 1);




            }
        }


    }

    public User seeUser(String id) {
        for (String s : map.keySet()) {
            if (s.equals(id)) {
                return map.get(s);
            }
        }
        return null;
    }


    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

        Scanner sc = new Scanner(System.in);
        UserController controller = new UserController();
        User user = login("1");
        List<String> list = controller.finalResult("1","1 2");
        for (String s : list){
            System.out.print(s + " ");
        }
        //menu(sc);


    }

    public static void menu(Scanner sc) throws IOException, ParseException, org.json.simple.parser.ParseException {
        sc = new Scanner(System.in);
        UserController controller = new UserController();
        System.out.println("Your id : ");
        String id = sc.nextLine();
        System.out.println("Your name : ");
        String name = sc.nextLine();
        User user = login(id);
        boolean checkMenu = true;


        do {

            System.out.println("""
                    [1] About me\s
                    [2] see connections\s
                    [3] see suggestion list\s
                    [4] choose preference\s
                    [5] search user\s
                    [6] follow\s
                    [7] post\s
                    [8] explore\s
                    [9] exit""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println(user.toString());
                    break;
                }
                case 2: {
                    for (String s : user.getConnectionId()) {
                        System.out.println("Id : " + s);
                        System.out.println("Name : " + controller.seeUser(s).getName());
                    }
                    boolean checkInfo = true;
                    do {
                        System.out.println("Wanna see their info ? [1] y [2] n");
                        int choiceInfo = sc.nextInt();

                        switch (choiceInfo) {
                            case 1: {
                                for (String s : user.getConnectionId()) {
                                    System.out.println(controller.seeUser(s).toString());
                                }
                                break;

                            }
                            case 2: {
                                checkInfo = false;
                                break;
                            }

                        }
                    } while (checkInfo);
                    break;
                }
                case 3: {
                    if (user != null) {
                        List<String> list = controller.suggestionList(user.getId());
                        System.out.println("This is the top 20 people we suggest you to connect : ");
                        for (int i = 1; i <= 20; i++) {
                            System.out.println(i + ": " + controller.seeUser(list.get(i)).getName() + " " + "Id : " + controller.seeUser(list.get(i)).getId());
                        }
                        boolean checkConnect = true;
                        do {
                            System.out.println("wanna follow ? [1] y [2] n");
                            sc.nextLine();
                            int choiceFollow = sc.nextInt();
                            switch (choiceFollow) {
                                case 1: {
                                    System.out.println("Enter the id : ");
                                    int idFollow = sc.nextInt();
                                    controller.follow(user, map.get(String.valueOf(idFollow)));
                                    System.out.println("Now the size of connection list is : " + user.getConnectionId().size());
                                    break;
                                }
                                case 2: {
                                    checkConnect = false;
                                    break;
                                }
                            }
                        } while (checkConnect);
                    }
                    break;
                }
                case 4: {
                    System.out.println("enter your priorities with  number in order");
                    System.out.println("1:UniversityPlace");
                    System.out.println("2:WorkPlace");
                    System.out.println("3:Field");
                    System.out.println("4:Specialties");
                    sc.nextLine();
                    String input = sc.nextLine();
                    int counter = 1;
                    for (String s : controller.finalResult(user.getId(), input)) {
                        System.out.println(counter + ": " + map.get(s).getName());
                        counter++;
                    }
                    boolean checkPriority = true;
                    do {
                        System.out.println("wanna follow someone? [1] y [2] n");
                        int choicePref = sc.nextInt();
                        switch (choicePref) {
                            case 1: {
                                sc.nextLine();
                                String idofSomeone = sc.next();
                                controller.follow(user, map.get(idofSomeone));
                                System.out.println("now size of your connection list : " + user.getConnectionId().size());
                                break;
                            }
                            case 2: {
                                checkPriority = false;
                                break;
                            }
                        }
                    } while (checkPriority);
                    break;

                }
                case 5: {
                    System.out.println("enter the name you wanna search : ");
                    sc.nextLine();
                    String nameSearch = sc.nextLine();
                    for (User u : map.values()) {
                        if (u.getName().equals(nameSearch)) {
                            System.out.println(u.toString());
                        }
                    }
                    break;
                }
                case 6: {
                    System.out.println("Enter the id you wanna follow : ");
                    sc.nextLine();
                    String idSixFollow = sc.next();
                    User user1 = map.get(idSixFollow);

                    controller.follow(user, user1);
                    System.out.println(user.getConnectionId().size());
                    break;
                }
                case 7: {
                    System.out.println("Write any post and share with others : ");
                    sc.nextLine();
                    String post = sc.nextLine();
                    controller.postsOfConsole.add(post);
                    List<String> commentList = new ArrayList<>();
                    controller.comment.add(controller.postsOfConsole.indexOf(post),commentList);
                    int likes = 0;
                    controller.likes.add(controller.postsOfConsole.indexOf(post),likes);
                    break;
                }
                case 8: {
                    for (String s : controller.postsOfConsole) {
                        System.out.println("Post " + controller.postsOfConsole.indexOf(s) + " : " + s);
                        System.out.println("**************************************************");
                        if (controller.comment.size()!=0) {
                            for (String comment : controller.comment.get(controller.postsOfConsole.indexOf(s))) {
                                System.out.println(comment);
                            }
                            System.out.println();
                        }

                    }
                    boolean checkComment = true;
                    do{
                        System.out.println("wanna comment ? [1] y [2] n");

                        int choicecomment = sc.nextInt();
                        switch (choicecomment){
                            case 1:{
                                System.out.println("comment any post you want with the index of post : ");

                                int indexPost = sc.nextInt();
                                sc.nextLine();
                                String comment = sc.nextLine();
                                controller.comment.get(indexPost).add(comment);
                                break;
                            }
                            case 2:{
                                checkComment = false;
                                break;
                            }
                        }

                    }while (checkComment);


                    break;
                }
                case 9: {
                    checkMenu = false;
                    break;
                }

            }


        } while (checkMenu);
    }


}