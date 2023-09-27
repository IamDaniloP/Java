package application;

import domain.Pessoa;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Program {
  public static void main(String[] args) {
    // TODA VEZ QUE FOR REALIZAR UMA TRANSAÇÃO, OU SEJA, SEM SER UMA CONSULTA SIMPLES
    // É PRECISO INSERIR O getTransaction.begin() e o getTransaction.commit().


    /*Pessoa p1 = new Pessoa("a", "a@gmail.com");
    Pessoa p2 = new Pessoa("b", "b@gmail.com");
    Pessoa p3 = new Pessoa("c", "c@gmail.com");
    */


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
    EntityManager em = emf.createEntityManager();

    //em.getTransaction().begin();
    //em.persist(p1);
    //em.persist(p2);
    //em.persist(p3);
    //em.getTransaction().commit();

    Pessoa p = em.find(Pessoa.class, 2);// recuperando dados

    em.getTransaction().begin();
    em.remove(em.find(Pessoa.class, 5)); // seu parâmetro é um objeto.
    // para que a remoção funcione, o objeto precisa ser monitorado, ou seja, é preciso passar o objeto sendo recuperado do banco de dados.
    em.getTransaction().commit();
    System.out.println("Pronto! ");
    em.close();
    emf.close();
  }
}
