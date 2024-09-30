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

            for (int i = 0; i < 10; i++) {
                Team team = new Team();
                team.setName("team" + i);

                for (int j = 0; j < 10; j++) {

                    Member member = new Member();
                    member.setName("member" + i + j);
                    member.setAge(j);
                    member.createRelation(team);
                    member.setType(MemberType.USER);
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

            String queryCase = "select " +
                        "case when m.age <= 10 then '학생요금' " +
                        "     when m.age >= 60 then '일반요금' " +
                        "     else '기본요금' end " +
                        "from Member m";
            List<String> CaseResult = em.createQuery(queryCase, String.class).getResultList();
            System.out.println(CaseResult);

            String queryCoalesce = "select coalesce(m.name, '이름 없는 회원') from Member m";
            List<String> coalesceResult = em.createQuery(queryCoalesce, String.class).getResultList();
            System.out.println(coalesceResult);

            String queryNullIf = "select nullif(m.name, 'member15') from Member m";
            List<String> nullIfResult = em.createQuery(queryNullIf, String.class).getResultList();
            System.out.println(nullIfResult);

            String function = "select concat('hello', 'world') from Member m";
            List<String> functionResult = em.createQuery(function, String.class).getResultList();
            System.out.println(functionResult);

            String sizeQuery = "select size(t.memberList) from Team t";
            List<Integer> sizeResult = em.createQuery(sizeQuery, Integer.class).getResultList();
            System.out.println(sizeResult);

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
