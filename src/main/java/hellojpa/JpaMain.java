package hellojpa;

import hellojpa.jpqlpractice.*;
import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("teamA");

            Team teamB = new Team();
            teamB.setName("teamB");


            Member member1 = new Member();
            member1.setName("회원1");
            member1.setAge(12);
            Member member2 = new Member();
            member2.setName("회원2");
            member2.setAge(18);
            Member member3 = new Member();
            member3.setName("회원3");
            member3.setAge(25);

            member1.setTeam(teamA);
            member2.setTeam(teamA);
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

//            em.flush();

            //벌크 연산, jpql임으로 플러쉬 자동 호출
            String query = "update Member m set m.age = 20 where m.age >= 15";
            int updatedMemberNumber = em.createQuery(query).executeUpdate();
            System.out.println(updatedMemberNumber);

            //db에 바로 반영 되어서 영속성 컨텍스트에는 그대로
            System.out.println("member1 persistance context = " + member1.getAge());
            System.out.println("member2 persistance context = " + member2.getAge());
            System.out.println("member3 persistance context = " + member3.getAge());

            //find해도 영속성 컨텍스트 값 가져옴
            System.out.println("member1 real value = " + em.find(Member.class, member1.getId()).getAge());
            System.out.println("member2 real value = " + em.find(Member.class, member2.getId()).getAge());
            System.out.println("member3 real value = " + em.find(Member.class, member3.getId()).getAge());

            em.clear();

            System.out.println("member1 real value = " + em.find(Member.class, member1.getId()).getAge());
            System.out.println("member2 real value = " + em.find(Member.class, member2.getId()).getAge());
            System.out.println("member3 real value = " + em.find(Member.class, member3.getId()).getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
