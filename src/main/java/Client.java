import entity.Post;
import entity.Thread;
import entity.Topic;
import entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class Client {
    private Session s;
    private User me;
    private Thread currentThread;
    private Topic currentTopic;
    private Post currentPost; //will be used for post editing
    private boolean loggedIn;
    private Scanner sc;

    Client(Session s) {
        this.s = s;
        me = new User();
        currentThread = null;
        loggedIn = false;
        sc = new Scanner(System.in);
    }

    public boolean login() {
        if (loggedIn) {
            System.out.println("Already logged in!");
            return false;
        }

        String username;
        String password;
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();


        User tempUser = new User(username, password);
        User queriedUser = (User) s.getNamedQuery("get_user_by_username")
                .setParameter("user_name", username)
                .uniqueResult();

        if (tempUser.equals(queriedUser)) {
            this.loggedIn = true;
            System.out.println("Login successful!");
            me = queriedUser;
            return true;
        }
        System.out.println("Wrong credentials!");
        return false;

    }
    //user session methods
    public boolean register() {
        if (loggedIn) {
            System.out.println("Cannot register when logged in!");
            return false;
        }
        String username;
        String password;
        String retypePassword;
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();
        System.out.print("Re-type password: ");
        retypePassword = sc.nextLine();
        //match passwords
        while (!password.equals(retypePassword)) {
            System.out.println("Passwords mismatch! Please try again!");
            System.out.print("Password: ");
            password = sc.nextLine();
            System.out.print("Re-type password: ");
            retypePassword = sc.nextLine();
        }
        //insert new user

        s.beginTransaction();
        s.save(new User(username, password));
        try {
            s.getTransaction().commit();
        } catch (Exception exception) {
            System.out.println("User-name " + username + " is taken! Please try again!");
            return false;
        }
        System.out.println(username + " added successfully!");
        return true;
    }

    public boolean logout() {
        if (!loggedIn) {
            return false;
        }
        me = null;
        currentPost = null;
        currentTopic = null;
        currentThread = null;
        loggedIn = false;
        return true;
    }
    //create methods
    public boolean createThread() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to create a new thread!");
            return false;
        }
        System.out.print("Title: ");
        String title = sc.nextLine();
        s.beginTransaction();
        Thread newThread = new Thread(title, me);
        s.save(newThread);
        s.getTransaction().commit();

        currentThread = newThread;
        System.out.println("Thread created. You can create a new topic now! :) ");
        return true;
    }

    public boolean createTopic() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to create a new topic!");
            return false;
        }
        if (currentThread == null) {
            System.out.println("Please select a valid/ existent thread before creating a new topic! "
                    + "(to select a thread type enterThread followed by Id)");
            return false;
        }
        System.out.print("Subject: ");
        String subject = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();
        Topic newTopic = new Topic(subject, description, me, currentThread);

        s.beginTransaction();
        s.save(newTopic);
        s.getTransaction().commit();
        currentTopic = newTopic;
        System.out.println("Topic created. You can add a post now! :) ");
        return true;
    }

    public boolean createPost() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to create a new post!");
            return false;
        }
        if (currentTopic == null) {
            System.out.println("Please select a valid/ existent topic before creating a new topic! "
                    + "(to select a topic type enterTopic followed by Id)");
            return false;
        }
        System.out.println("Type your content below: ");
        String content = sc.nextLine();
        Post newPost = new Post(content, me, currentTopic);
        s.beginTransaction();
        s.save(newPost);
        s.getTransaction().commit();
        System.out.println("Post created!");
        return true;
    }
    //help options
    public void help() {
        System.out.println("login -> to login");
        System.out.println("register -> to create a new user");
        System.out.println("logout -> to close current session");
        System.out.println("-");
        System.out.println("createPost -> to create a new post (must be logged in)");
        System.out.println("createThread -> to create a new thread (must be logged in)");
        System.out.println("createTopic -> to create a new topic (must be logged in)");
        System.out.println("-");
        System.out.println("showCurrThread -> displays current thread (must be logged in)");
        System.out.println("showCurrTopic -> displays current topic (must be logged in)");
        System.out.println("showCurrPost -> displays current post (must be logged in)");
        System.out.println("showAllThreads -> returns all threads of the logged user");
        System.out.println("showAllTopics -> returns all topics of the logged user");
        System.out.println("-");
        System.out.println("enterThread [id] -> enters the specified (id) thread");
        System.out.println("enterTopic [id] -> enters the specified (id) topic");
        System.out.println("enterPost [id] -> enters the specified (id) post");
        System.out.println("-");
        System.out.println("help -> show all available commands");
    }
    //show methods
    public boolean showCurrTopic() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a topic!");
            return false;
        }
        if (currentTopic == null) {
            System.out.println("Please select a valid/ existent topic! "
                    + "(to select a topic type enterTopic followed by Id)");
            return false;
        }
        System.out.println(currentTopic);
        return true;
    }

    public boolean showCurrThread() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a thread!");
            return false;
        }

        if (currentThread == null) {
            System.out.println("Please select a valid/ existent thread! "
                    + "(to select a thread type enterThread followed by Id)");
            return false;
        }

        System.out.println(currentThread);
        return true;
    }

    public boolean showCurrPost() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a post!");
            return false;
        }

        if (currentPost == null) {
            System.out.println("Please select a valid/ existent post! "
                    + "(to select a thread type enterPost followed by Id)");
            return false;
        }

        System.out.println(currentPost);
        return true;
    }

    public boolean showAllTopics() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view all topics!");
            return false;
        }

        if (currentThread == null) {
            System.out.println("Please select a valid/ existent thread! "
                    + "(to select a thread type enterThread followed by Id)");
            return false;
        }

        currentThread = s.get(Thread.class, currentThread.getId());
        System.out.println(currentThread.toStringTopics());
        return true;
    }

    public boolean showAllPosts() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view all posts!");
            return false;
        }
        if (currentTopic == null) {
            System.out.println("Please select a valid/ existent topic! "
                    + "(to select a thread type enterTopic followed by Id)");
            return false;
        }
        currentTopic = s.get(currentTopic.getClass(), currentTopic.getId());
        System.out.println(currentTopic.toStringPosts());
        return true;
    }

    public boolean showAllThreads() {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view all threads!");
            return false;
        }

        List<Thread> threadList = s.createCriteria(Thread.class).list();
        System.out.println(threadList);

        return true;
    }
    //enter methods
    public boolean enterThread(Integer id) {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a thread!");
            return false;
        }

        if (id == null) {
            System.out.println("Error! " +
                    "\nInvalid id number!");
            return false;
        }
        Thread destThread = (Thread) s.getNamedQuery("get_thread_by_id")
                .setParameter("thread_id",id)
                .uniqueResult();

        if (destThread == null) {
            System.out.println("Error! " +
                    "\nThe thread #" + id + " doesn't exist!");
            return false;
        }

        currentThread = destThread;
        System.out.println("Thread selected!");
        return true;
    }

    public boolean enterTopic(Integer id) {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a topic!");
            return false;
        }

        if (currentThread == null) {
            System.out.println("Error! " +
                    "\nMust select a thread first!");
            return false;
        }

        Topic destTopic = (Topic) s.getNamedQuery("get_topic_by_id")
                .setParameter("topic_id",id)
                .uniqueResult();

        if (destTopic == null) {
            System.out.println("Error! " +
                    "\nThe topic #" + id + " doesn't exist!");
            return false;
        }

        currentTopic = destTopic;
        System.out.println("Topic selected!");
        return true;
    }

    public boolean enterPost(Integer id) {
        if (!loggedIn) {
            System.out.println("Error! " +
                    "\nMust be logged in in order to view a post!");
            return false;
        }

        if (currentTopic == null) {
            System.out.println("Error! " +
                    "\nMust select a topic first!");
            return false;
        }

        Post destPost = (Post) s.getNamedQuery("get_post_by_id")
                .setParameter("post_id",id)
                .uniqueResult();

        if (destPost == null) {
            System.out.println("Error! " +
                    "\nThe post #" + id + " doesn't exist!");
            return false;
        }

        currentPost = destPost;
        System.out.println("Post selected!");
        return true;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            //notify user in prompt when logged in
            if (me.getUsername() == null) {
                System.out.print(">");
            } else {
                System.out.print(me.getUsername() + ">");
            }

            String cmd = sc.next();
            //sc.nextLine();
            Integer id = null;
            switch (cmd) {
                case "login":
                    sc.nextLine();
                    login();
                    break;

                case "register":
                    sc.nextLine();
                    register();
                    break;

                case "createThread":
                    sc.nextLine();
                    createThread();
                    break;

                case "createTopic":
                    sc.nextLine();
                    createTopic();
                    break;

                case "createPost":
                    sc.nextLine();
                    createPost();
                    break;

                case "help":
                    sc.nextLine();
                    help();
                    break;

                case "showCurrPost":
                    sc.nextLine();
                    showCurrPost();
                    break;

                case "showCurrTopic":
                    sc.nextLine();
                    showCurrTopic();
                    break;

                case "showCurrThread":
                    sc.nextLine();
                    showCurrThread();
                    break;

                case "showAllTopics":
                    sc.nextLine();
                    showAllTopics();
                    break;

                case "showAllPosts":
                    sc.nextLine();
                    showAllPosts();
                    break;

                case "showAllThreads":
                    sc.nextLine();
                    showAllThreads();
                    break;

                case "enterThread":
                    if(sc.hasNext()) {
                        id = sc.nextInt();
                    } else {
                        sc.nextLine();
                    }
                    enterThread(id);
                    break;

                case "enterTopic":
                    if(sc.hasNext()) {
                        id = sc.nextInt();
                    } else {
                        sc.nextLine();
                    }
                    enterTopic(id);
                    break;

                case "enterPost":
                    if(sc.hasNext()) {
                        id = sc.nextInt();
                    } else {
                        sc.nextLine();
                    }
                    enterPost(id);
                    break;

                case "logout":
                    sc.nextLine();
                    logout();
                    break;

                case "exit":
                    sc.nextLine();
                    exit = true;
                    break;

                default:
                    System.out.println("unknown command --type help to view all available commands");
            }
        }
    }
}