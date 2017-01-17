package com.wsh.base.model;


import java.io.Serializable;
import java.util.Date;
/**
 * 图书类实体
 * @author Wally
 *
 */
public class BookInfo implements Serializable{

	private static final long serialVersionUID = -870313435021098002L;

	private Integer bookId;

    private String bookTitle;

    private String bookPicId;  //为了简单，暂时用来保存：图片名字吧。单独创建一个文件夹。下面存放书的图片。因为不会有太多图片。

    private String bookAuthor;

    private Date bookPubdate;

    private String bookIntro;

    private Boolean isfree;

    private Integer price;

    private Integer status;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle == null ? null : bookTitle.trim();
    }

    public String getBookPicId() {
        return bookPicId;
    }

    public void setBookPicId(String bookPicId) {
        this.bookPicId = bookPicId == null ? null : bookPicId.trim();
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public Date getBookPubdate() {
        return bookPubdate;
    }

    public void setBookPubdate(Date bookPubdate) {
        this.bookPubdate = bookPubdate;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
    }

    public Boolean getIsfree() {
        return isfree;
    }

    public void setIsfree(Boolean isfree) {
        this.isfree = isfree;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
    public String getBookInfo(){
    	return "[id="+this.bookId+"][title="+this.bookTitle+"][book_author="+this.bookAuthor+"][出版日期="+this.bookPubdate+"]";                
    }
    
}