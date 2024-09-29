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

            Member member = new Member();
            member.setName("hi");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", "hi")
                    .getSingleResult();

            //이렇게 하는 거 보다 조인 하는 것을 나타내는 것이 좋음
//            List<Team> teams = em.createQuery("select m.team from Member m", Team.class)
//                    .getResultList();

            // 조인을 알아서 해주지만 형태를 나타내서 하자
            List<Team> teams = em.createQuery("select t from Member m inner join m.team t", Team.class)
                    .getResultList();

            //임베디드 타입 프로젝션은 조인 같은게 아니므로(그냥 값들이 있는거니까) 그냥 하면됨
            List<Address> addressList = em.createQuery("select o.Address from Order o", Address.class).getResultList();

            List<MemberDTO> memberDTOList = em.createQuery("select new hellojpa.jpqlpractice.MemberDTO(m.name, m.age) from Member m", MemberDTO.class).getResultList();

            System.out.println(findMember.getName());
            System.out.println(memberDTOList.get(0).toString());

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
