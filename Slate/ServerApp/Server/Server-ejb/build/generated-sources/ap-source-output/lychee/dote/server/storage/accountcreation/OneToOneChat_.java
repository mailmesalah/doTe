package lychee.dote.server.storage.accountcreation;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lychee.dote.server.storage.accountcreation.Profile;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-18T12:52:48")
@StaticMetamodel(OneToOneChat.class)
public class OneToOneChat_ { 

    public static volatile SingularAttribute<OneToOneChat, Profile> senderProfile;
    public static volatile SingularAttribute<OneToOneChat, String> textMessage;
    public static volatile SingularAttribute<OneToOneChat, String> messageStatus;
    public static volatile SingularAttribute<OneToOneChat, Profile> receiverProfile;
    public static volatile SingularAttribute<OneToOneChat, Long> serialNumber;
    public static volatile SingularAttribute<OneToOneChat, String> messageType;
    public static volatile SingularAttribute<OneToOneChat, String> filePath;
    public static volatile SingularAttribute<OneToOneChat, Timestamp> receptDateTime;
    public static volatile SingularAttribute<OneToOneChat, Boolean> synchronised;
    public static volatile SingularAttribute<OneToOneChat, Long> id;
    public static volatile SingularAttribute<OneToOneChat, Timestamp> sentDateTime;

}