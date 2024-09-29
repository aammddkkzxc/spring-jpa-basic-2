package hellojpa;

import hellojpa.jpqlpractice.Member;
import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("hi");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", "hi")
                    .getSingleResult();

            System.out.println(findMember.getName());

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
