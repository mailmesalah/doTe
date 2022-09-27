package lychee.dote.server.storage.accountcreation;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lychee.dote.server.storage.accountcreation.Contact;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-18T12:52:48")
@StaticMetamodel(Profile.class)
public class Profile_ { 

    public static volatile SingularAttribute<Profile, String> profilePassword;
    public static volatile SingularAttribute<Profile, String> phoneNumber;
    public static volatile SingularAttribute<Profile, String> name;
    public static volatile SingularAttribute<Profile, String> about;
    public static volatile SingularAttribute<Profile, String> id;
    public static volatile SingularAttribute<Profile, String> profileImage;
    public static volatile SingularAttribute<Profile, Timestamp> addedOn;
    public static volatile ListAttribute<Profile, Contact> contacts;

}