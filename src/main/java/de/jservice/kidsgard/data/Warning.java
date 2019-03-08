package de.jservice.kidsgard.data;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

/**
 *
 * @author Amr Reda
 */

@Entity
@Table(name = "warning")
@AttributeOverride(name = "id", column = @Column(name = "warning_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Warning extends BaseEntityAudit{
    
    private String sender;
    @Column
    @ElementCollection(targetClass=String.class,fetch = FetchType.EAGER)
    private Set<String> emailList= new HashSet<>();

    public Set<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(Set<String> emailList) {
        this.emailList = emailList;
    }
    
    private String subject;
    private String content;

    public Warning() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }   
    
    @Override
    public String toString() {
        return "Warning{" + "sender=" + sender + ", emailList=" + emailList + ", subject=" + subject + ", content=" + content + '}';
    }

}
