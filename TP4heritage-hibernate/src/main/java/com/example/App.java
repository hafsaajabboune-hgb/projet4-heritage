package com.example;

import com.example.model.joined.Developpeur;
import com.example.model.joined.Employe;
import com.example.model.joined.Manager;
import com.example.model.singletable.Moto;
import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.tableperclass.Electronique;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");

        try {
            System.out.println("\n=== Test de la stratégie SINGLE_TABLE ===");
            testSingleTable(emf);

            System.out.println("\n=== Test de la stratégie JOINED ===");
            testJoined(emf);

            System.out.println("\n=== Test de la stratégie TABLE_PER_CLASS ===");
            testTablePerClass(emf);

        } finally {
            emf.close();
        }
    }

    private static void testSingleTable(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Voiture voiture1 = new Voiture(
                    "Toyota", "Corolla", LocalDate.of(2021, 4, 12), 17000.0,
                    5, true, "Hybride"
            );

            Voiture voiture2 = new Voiture(
                    "Volkswagen", "Golf", LocalDate.of(2018, 9, 5), 15500.0,
                    5, true, "Essence"
            );

            Moto moto1 = new Moto(
                    "Yamaha", "R1", LocalDate.of(2022, 6, 18), 14000.0,
                    1000, "Manuelle"
            );

            em.persist(voiture1);
            em.persist(voiture2);
            em.persist(moto1);

            em.getTransaction().commit();

            em.clear();

            List<Vehicule> vehicules = em.createQuery("SELECT v FROM Vehicule v", Vehicule.class)
                    .getResultList();
            vehicules.forEach(System.out::println);

            List<Voiture> voitures = em.createQuery("SELECT v FROM Voiture v", Voiture.class)
                    .getResultList();
            voitures.forEach(System.out::println);

            List<Moto> motos = em.createQuery("SELECT m FROM Moto m", Moto.class)
                    .getResultList();
            motos.forEach(System.out::println);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void testJoined(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Developpeur dev1 = new Developpeur(
                    "hassou", "fatimaazzahra", "fatimaazzahra.hassou@example.com", LocalDate.of(2019, 7, 15),
                    "Java", "Backend", 5
            );

            Developpeur dev2 = new Developpeur(
                    "El Idrissi", "Nadia", "nadia.elidrissi@example.com", LocalDate.of(2020, 3, 10),
                    "JavaScript", "Frontend", 3
            );

            Manager manager1 = new Manager(
                    "sabbi", "youssfe", "youssfe.sabbi@example.com", LocalDate.of(2018, 4, 20),
                    "IT", 10, 5000.0
            );

            em.persist(dev1);
            em.persist(dev2);
            em.persist(manager1);

            em.getTransaction().commit();

            em.clear();

            List<Employe> employes = em.createQuery("SELECT e FROM Employe e", Employe.class)
                    .getResultList();
            employes.forEach(System.out::println);

            List<Developpeur> devs = em.createQuery("SELECT d FROM Developpeur d", Developpeur.class)
                    .getResultList();
            devs.forEach(System.out::println);

            List<Manager> managers = em.createQuery("SELECT m FROM Manager m", Manager.class)
                    .getResultList();
            managers.forEach(System.out::println);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void testTablePerClass(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Livre livre1 = new Livre(
                    "Le Petit Prince",
                    11.99,
                    "Conte poétique sur l’amitié, l’enfance et la vie",
                    "Antoine de Saint-Exupéry",
                    "978-0156013987",
                    96,
                    "Folio"
            );

            Livre livre2 = new Livre(
                    "L’Étranger",
                    18.50,
                    "Roman philosophique sur l’absurdité et l’isolement",
                    "Albert Camus",
                    "978-2070360024",
                    180,
                    "Gallimard"
            );

            Electronique elec1 = new Electronique(
                    "Ordinateur Portable HP", 749.99, "PC portable pour bureautique et études",
                    "HP", "Pavilion 15", 12, "Intel i5, 16Go RAM, SSD 512Go"
            );

            em.persist(livre1);
            em.persist(livre2);
            em.persist(elec1);

            em.getTransaction().commit();

            em.clear();

            List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class)
                    .getResultList();
            produits.forEach(System.out::println);

            List<Livre> livres = em.createQuery("SELECT l FROM Livre l", Livre.class)
                    .getResultList();
            livres.forEach(System.out::println);

            List<Electronique> elecs = em.createQuery("SELECT e FROM Electronique e", Electronique.class)
                    .getResultList();
            elecs.forEach(System.out::println);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}