/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.storage.accountcreation;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author Sely
 */
@Entity
public class OneToOneChat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //Members
    @ManyToOne
    private Profile senderProfile;
    private Timestamp sentDateTime;
    @ManyToOne
    private Profile receiverProfile;
    private Timestamp receptDateTime;
    private String messageType;
    @Lob    
    private String textMessage;
    private String filePath;
    private String messageStatus;//Deleted/Spammed/
    private long serialNumber;
    private boolean synchronised;

    public OneToOneChat() {
    }

    public OneToOneChat(Profile senderProfile, Timestamp sentDateTime, Profile receiverProfile, String messageType, String textMessage, String filePath, String messageStatus, long serialNumber, boolean synchronised) {
        this.senderProfile = senderProfile;
        this.sentDateTime = sentDateTime;
        this.receiverProfile = receiverProfile;
        this.messageType = messageType;
        this.textMessage = textMessage;
        this.filePath = filePath;
        this.messageStatus = messageStatus;
        this.serialNumber = serialNumber;
        this.synchronised = synchronised;
    }

    public Timestamp getReceptDateTime() {
        return receptDateTime;
    }

    public void setReceptDateTime(Timestamp receptDateTime) {
        this.receptDateTime = receptDateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Profile getSenderProfile() {
        return senderProfile;
    }

    public void setSenderProfile(Profile senderProfile) {
        this.senderProfile = senderProfile;
    }

    public Timestamp getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(Timestamp sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public Profile getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(Profile receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isSynchronised() {
        return synchronised;
    }

    public void setSynchronised(boolean synchronised) {
        this.synchronised = synchronised;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OneToOneChat)) {
            return false;
        }
        OneToOneChat other = (OneToOneChat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lychee.dote.server.storage.accountcreation.OneToOneChat[ id=" + id + " ]";
    }
    
}
