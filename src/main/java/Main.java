import org.h2.result.Row;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class Main {

    JdbcTemplate jdbcTemplate;

    public Main(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        Main main = (Main) context.getBean("main");
        main.logEvent(new Random().nextInt(),"new Event");

        Event event = main.jdbcTemplate.queryForObject("Select TOP ? * FROM t_event", new Object[]{1}, new RowMapper<Event>() {

            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event1 = new Event();
                event1.setId(resultSet.getInt("id"));
                event1.setMsg(resultSet.getString("msg"));
                return event1;
            }
        });

        System.out.println(event.id + " " + event.msg);

        List<Event> events = main.jdbcTemplate.query("Select * FROM t_event", new RowMapper<Event>() {

            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event1 = new Event();
                event1.setId(resultSet.getInt("id"));
                event1.setMsg(resultSet.getString("msg"));
                return event1;
            }
        });

        System.out.println(events);

    }

    public void logEvent(int id, String event) {
        jdbcTemplate.update("INSERT INTO t_EVENT (id,msg) VALUES (?,?)",id,event);
    }

    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM T_EVENT");
    }

}
