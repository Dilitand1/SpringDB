package ru.litvinov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Component
public class Main2 {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public Main2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void initMethod(){
        System.out.println("init");
    }

    void destroyMethod(){
        System.out.println("destroy");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ru.litvinov.ConfigClass.class);
        Main2 main = context.getBean("main2", Main2.class);
        //Main2 main = context.getBean("proxyFactoryBean", Main2.class);
        main.logEvent(new Random().nextInt(),"new Event");
        main.soutEvent();
        main.soutEvents();
        context.close();

    }
    public void soutEvent(){
        Event2 event = jdbcTemplate.queryForObject("Select TOP ? * FROM t_event", new Object[]{1}, new RowMapper<Event2>() {
            public Event2 mapRow(ResultSet resultSet, int i) throws SQLException {
                Event2 event1 = new Event2();
                event1.setId(resultSet.getInt("id"));
                event1.setMsg(resultSet.getString("msg"));
                return event1;
            }
        });
        System.out.println(event.id + " " + event.msg);
    }

    public void soutEvents(){
        List<Event2> events = jdbcTemplate.query("Select * FROM t_event", new RowMapper<Event2>() {
            public Event2 mapRow(ResultSet resultSet, int i) throws SQLException {
                Event2 event1 = new Event2();
                event1.setId(resultSet.getInt("id"));
                event1.setMsg(resultSet.getString("msg"));
                return event1;
            }
        });
        System.out.println(events);
    }

    public void logEvent(int id, String event) {
        System.out.println("inserting");
        jdbcTemplate.update("INSERT INTO t_EVENT (id,msg) VALUES (?,?)",id,event);
    }

    public int deleteAll(){
        return jdbcTemplate.update("DELETE FROM T_EVENT");
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
