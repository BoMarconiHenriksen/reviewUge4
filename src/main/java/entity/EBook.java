package entity;

import javax.persistence.Entity;

/**
 *
 * @author Bo
 */
@Entity
public class EBook extends Book {

   private String downloadUrl;
   private int sizeMB;
   
    public EBook(String downloadUrl, int sizeMB, String title, String author, double price) {
        super(title, author, price);
        this.downloadUrl = downloadUrl;
        this.sizeMB = sizeMB;
    }
    
    public EBook() {
        
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getSizeMB() {
        return sizeMB;
    }

    public void setSizeMB(int sizeMB) {
        this.sizeMB = sizeMB;
    }

}
