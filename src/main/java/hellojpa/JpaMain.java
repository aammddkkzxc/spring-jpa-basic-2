package hellojpa;

import hellojpa.jpqlpractice.Address;
import hellojpa.jpqlpractice.Member;
import hellojpa.jpqlpractice.MemberDTO;
import hellojpa.jpqlpractice.Team;
import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 0; i < 10; i++) {
                Team team = new Team();
                team.setName("team" + i);

                for (int j = 0; j < 10; j++) {

                    Member member = new Member();
                    member.setName("member" + i + j);
                    member.setAge(j);
                    member.createRelation(team);
                    em.persist(member);

                }
            }

            em.flush();
            em.clear();

            List<Team> teams = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            List<Member> membersInner = em.createQuery("select m from Member m join m.team t", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .getResultList();

            List<Member> membersLeft = em.createQuery("select m from Member m left join m.team t", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .getResultList();

            List<Member> theta = em.createQuery("select m from Member m, Team t where m.name = t.name", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .getResultList();

            List<Member> filter = em.createQuery("select m from Member m left join m.team t on t.name = 'team5'", Member.class)
                    .getResultList();

            System.out.println(teams);
            System.out.println(membersInner);
            System.out.println(membersLeft);
            System.out.println(theta);
            System.out.println(filter);

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
