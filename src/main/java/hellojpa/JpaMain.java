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
            Member member2 = new Member();
            member2.setName("회원2");
            Member member3 = new Member();
            member3.setName("회원3");

            member1.setTeam(teamA);
            member2.setTeam(teamA);
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m";
//            String query = "select m from Member m join m.team t";
//            String query = "select m from Member m join fetch m.team t";
//            String query = "select m, t from Member m join fetch m.team t";
//            String query = "select m from Member m left join m.team t";
//            String query = "select t from Team t join fetch t.memberList";
            List resultList = em.createQuery(query).getResultList();
//            for (Team t : resultList) {
//                for (Member member : t.getMemberList()) {
//                    System.out.println(t. getName() + "|" + t.getMemberList().size() + "|" + member.getName());
//                }
//            }
            System.out.println(resultList.size());


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
