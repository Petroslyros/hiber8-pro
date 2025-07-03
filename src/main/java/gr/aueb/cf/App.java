package gr.aueb.cf;


import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

public class App {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school8PU");
    private final static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

//        Teacher teacher = new Teacher(null, "Αθανάσιος", "Ανδρούτσος");
//        Course course = new Course(null, "Java");
//        teacher.addCourse(course);


        // JPQL (Java Persistence Query Language)
        // Criteria - API

        em.getTransaction().begin();

//        Course course = em.find(Course.class, 1L);
//        em.remove(course);

//        Teacher teacher = em.find(Teacher.class, 1L);
//        em.remove(teacher);
//        teacher.setFirstname("Αθ.");
//        em.merge(teacher);

//        em.persist(teacher);
//        em.persist(course);


        // Select all teachers
//        String sql = "SELECT t FROM Teacher t";
//        List<Teacher> teachers = em.createQuery(sql, Teacher.class).getResultList();
//        teachers.forEach(System.out::println);


        //SELECT courses του teacher με επώνυμο 'Ανδρούτσος'

        // SQL Injection is an attack where a user injects a string into a query
        // If we inject the string immediately in the sql query, this could be
        // very dangerous.

        // We should always use PARAMETERS in query strings or in any CRUD method that
        // uses input params from the user. Όλες οι γλώσσες έχουν μηχανισμούς να περνάμε
        // Strings ως παραμέτρους σε ένα Query.

        // Παρακάτω είμαστε απρόσεκτοι και περνάμε το String 'Ανδρούτσος' κατευθείαν.
        // Θα μπορούσε να είναι μία μεταβλητή που έχει το Ανδρούτσος

//        String andrLastname = "Ανδρούτσος";     // έρχεται από τον client
//        String sql2 = "SELECT c FROM Course c WHERE c.teacher.lastname = " + andrLastname; // Dangerous
//        Το ίδιο με απο πάνω
        // String sql2 = "SELECT c FROM Course c WHERE c.teacher.lastname = 'Ανδρούτσος'";

        // Η μόνη λύση για SQL Injection attack είναι να χρησιμοποιούμε aliases (ψευδώνυμα)
        // όπως το :lastname (στην JPQL τα named params ξεκινάνε με :)
        // και στη συνέχεια να κάνουμε setParameter αφού έχει δημιουργηθεί το query (TypedQuery<>)
        // όπως παρακάτω
//        String sql2 = "SELECT c FROM Course c WHERE c.teacher.lastname = :lastname";
//        TypedQuery<Course> query = em.createQuery(sql2, Course.class);
//        List<Course> courses = query
//                .setParameter("lastname", andrLastname)
//                .getResultList();
//        courses.forEach(System.out::println);

//        // Select teachers & courses they teach
//        String sql3 = "SELECT t, c FROM Teacher t JOIN t.courses c";
//        List<Object[]> teachersCourses = em.createQuery(sql3, Object[].class).getResultList();
//        for (Object[] objectArr : teachersCourses) {
//            System.out.println("Teacher: " + objectArr[0] + "\nCourse: " + objectArr[1]);
//        }

        // Teachers & το πλήθος των courses που διδάσκουν
//        String sql4 = "SELECT t.lastname, count(c) FROM Teacher t JOIN t.courses c GROUP BY t";
//        List<Object[]> teachersCoursesCount = em.createQuery(sql4, Object[].class).getResultList();
//
//        for (Object[] objectArr : teachersCoursesCount) {
//            System.out.println("Teacher: " + objectArr[0] + ", Course count: " + objectArr[1]);
//        }

        // Teachers & το πλήθος των courses που διδάσκουν όταν είναι πάνω από 1 course
//        String sql4 = "SELECT t.lastname, count(c) FROM Teacher t JOIN t.courses c GROUP BY t HAVING count(c) > 1";
//        List<Object[]> teachersCoursesCount = em.createQuery(sql4, Object[].class).getResultList();
//
//        for (Object[] objectArr : teachersCoursesCount) {
//            System.out.println("Teacher: " + objectArr[0] + ", Course count: " + objectArr[1]);
//        }

        // Teachers & courses που διδάσκουν order by teacher lastname ASC & course title DESC
//        String sql4 = "SELECT t, c FROM Teacher t JOIN t.courses c ORDER BY t.lastname ASC, c.title DESC";
//        List<Object[]> teachersCourses = em.createQuery(sql4, Object[].class).getResultList();
//
//        for (Object[] objectArr : teachersCourses) {
//            System.out.println("Teacher: " + objectArr[0] + ", Course count: " + objectArr[1]);
//        }


//       // Criteria API

        //  Select all courses
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Course> query = cb.createQuery(Course.class);     // Τι επιστρέφει
//        Root<Course> root = query.from(Course.class);                   // Root Entity
//        query.select(root);
//
//        List<Course> courses = em.createQuery(query).getResultList();
//        courses.forEach(System.out::println);


        //  Select all teachers με επώνυμο Ανδρούτσος
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
//        Root<Teacher> teacherRoot = query.from(Teacher.class);
//
//        query.select(teacherRoot).where(cb.equal(teacherRoot.get("lastname"), "Ανδρούτσος"));   // SQL Injection !!!
//
//        List<Teacher> teachers = em.createQuery(query).getResultList();
//        teachers.forEach(System.out::println);


        //  Select all teachers με επώνυμο Λύρος - SQL Injection safe
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = query.from(Teacher.class);

        ParameterExpression<String> lastnameParam = cb.parameter(String.class);
        query.select(teacherRoot).where(cb.equal(teacherRoot.get("lastname"), lastnameParam));   // SQL Injection !!!

        List<Teacher> teachers = em.createQuery(query).setParameter(lastnameParam, "Λύρος").getResultList();
        teachers.forEach(System.out::println);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}

