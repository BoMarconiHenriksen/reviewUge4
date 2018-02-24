package test;

import entity.Book;
import entity.EBook;
import entity.PaperBook;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Bo
 */
public class Tester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("examPrepInheritancePU");
        EntityManager em = emf.createEntityManager();

        Persistence.generateSchema("examPrepInheritancePU", null);
        
        createEBook("Heksejagt", "Arthur Miller", 5.45, "www.hj.dk/download", 50, em);
        createEBook("Romeo og Julie", "Sheakspear", 2.95, "www.sheakspear.dk/download", 75, em);
        createPaperBook("Rasmus Klup på Eventyr", "Carla og Wilhelm Hansen", 100, 1.5, 25, em);
        createPaperBook("Prop og Berta", "Bent Solhof", 59.95, 3.5, 140, em);
        
        readEBook(1L, em);
        readPaperBook(3L, em);
        
        //For det som ikke skal opdateres skrives der null. 
        //Dog for price og sizeMB skal der skrives 0, hvis det ikke skal opdaters. 
        //updateEbook(1L, null, "asb", 0, null, 0, em);
        
        //For det som ikke skal opdateres skrives der null. 
        //Dog for price, shippiingweight og inStock skal der skrives 0, hvis det ikke skal opdaters. 
        //updatePaperBook(3L, "Jeg blev opdateret", "Den Franske Opdatering", 0, 0, 10, em);
        
        //Metoden til at slette
        //deleteBook(1L, em);
        
    }

    public static void createEBook(String title, String author, double price, String downloadUrl, int sizeMB, EntityManager em) {
        EBook ebook = new EBook();
        
        em.getTransaction().begin();
        
        ebook.setTitle(title);
        ebook.setAuthor(author);
        ebook.setPrice(price);
        ebook.setDownloadUrl(downloadUrl);
        ebook.setSizeMB(sizeMB);
        
        em.persist(ebook);
        em.getTransaction().commit();
    }
    
    public static void createPaperBook(String title, String author, double price, double shippingWeight, int inStock, EntityManager em) {
        PaperBook paperBook = new PaperBook();
        
        em.getTransaction().begin();
        
        paperBook.setTitle(title);
        paperBook.setAuthor(author);
        paperBook.setPrice(price);
        paperBook.setShippingWeight(shippingWeight);
        paperBook.setInStock(inStock);
        
        em.persist(paperBook);
        em.getTransaction().commit();
    }
    
    //Read metoder
    public static void readEBook(Long isbn, EntityManager em) {
        Query query = em.createQuery("SELECT e FROM EBook e WHERE e.isbn = :isbn");
        query.setParameter("isbn", isbn);
        
        EBook result = (EBook)query.getSingleResult();
        
        System.out.println("ISBN: " + result.getIsbn());
        System.out.println("Title: " + result.getTitle());
        System.out.println("Forfatter: " + result.getAuthor());
        System.out.println("Pris: " + result.getPrice() + " kr.");
        System.out.println("Download url : " + result.getDownloadUrl());
        System.out.println("Størrelse i megabyte : " + result.getSizeMB() + " megabyte.");
        System.out.println("");//Hvis der udskrives flere bøger er der mellem rum mellem hver bog
    }
    
    public static void readPaperBook(Long isbn, EntityManager em) {
        Query query = em.createQuery("SELECT e FROM PaperBook e WHERE e.isbn = :isbn");
        query.setParameter("isbn", isbn);
        
        PaperBook result = (PaperBook)query.getSingleResult();
        
        System.out.println("ISBN: " + result.getIsbn());
        System.out.println("Title: " + result.getTitle());
        System.out.println("Forfatter: " + result.getAuthor());
        System.out.println("Pris: " + result.getPrice() + " kr.");
        System.out.println("Forsendelsesvægt : " + result.getShippingWeight());
        System.out.println("Antal på lager : " + result.getInStock());
        System.out.println(""); //Hvis der udskrives flere bøger er der et lille mellem rum mellem hver bog
    }
    
    //Update metoder
    public static void updateEbook(Long isbn, String title, String author, double price, String downloadUrl, int sizeMB, EntityManager em) {
        //Finder ebook ud fra isbn
        EBook ebook = em.find(EBook.class, isbn);
        
        //Tjekker for hvad der skal ændres og sætter de nye værdier
        if(title != null) {
            ebook.setTitle(title);
            System.out.println("Titlen er ændret til " + title);
        } if(author != null) {
            ebook.setAuthor(author);
            System.out.println("Forfatter er ændret til " + author);
        } if (price != 0) {
            ebook.setPrice(price);
            System.out.println("Prisen er ændret til " + price + "kr.");
        } if(downloadUrl != null) {
            ebook.setDownloadUrl(downloadUrl);
            System.out.println("download url er ændret til " + downloadUrl);
        } if (sizeMB != 0) {
            ebook.setSizeMB(sizeMB);
            System.out.println("Megabyte er ændret til " + sizeMB + "MB");
        } else {
            System.out.println("Ingen ændringer!");
        }
        
        //Ændringerne i det "nye" objekt merges med det gamle.
        em.getTransaction().begin();
        em.merge(ebook);
        em.getTransaction().commit();
    }
    
    public static void updatePaperBook(Long isbn, String title, String author, double price, double shippingWeight, int inStock, EntityManager em) {
        PaperBook paperBook = em.find(PaperBook.class, isbn);
        
        //Tjekker for hvad der skal ændres og sætter de nye værdier
        if(title != null) {
            paperBook.setTitle(title);
            System.out.println("Ændre er ændret til " + title);
        } if(author != null) {
            paperBook.setAuthor(author);
            System.out.println("Forfatter er ændret til " + author);
        } if (price != 0) {
            paperBook.setPrice(price);
            System.out.println("Prisen er ændret til " + price + "kr.");
        } if(shippingWeight != 0) {
            paperBook.setShippingWeight(shippingWeight);
            System.out.println("Forsendelsesvægt er ændret til " + shippingWeight);
        } if (inStock != 0) {
            paperBook.setInStock(inStock);
            System.out.println("Antal på lager er ændret til " + inStock);
        } else {
            System.out.println("Ingen ændringer!");
        }
        
        //Ændringerne i det "nye" objekt merges med det gamle.
        em.getTransaction().begin();
        em.merge(paperBook);
        em.getTransaction().commit();
    }
    
    //Metoden for delete og mit forsøg på  polymorphism
    public static void deleteBook(Long isbn, EntityManager em) {
        //Henter en liste af både ebook og paperbook
        Query query = em.createQuery("Select e FROM Book e WHERE e.isbn > 0");
        List<Book> result = query.getResultList();
        
        int id = isbn.intValue() - 1; //-1 da de ligger i en List
        Book book = result.get(id);
        
        em.getTransaction().begin();
        em.remove(book);
        em.getTransaction().commit();
        System.out.println(book.getTitle() + " af " + book.getAuthor() + " er nu slettet.");
    }
    
}
