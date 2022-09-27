package lychee.dote.server.storage.accountcreation;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lychee.dote.server.storage.accountcreation.Profile;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-18T12:52:48")
@StaticMetamodel(Contact.class)
public class Contact_ { 

    public static volatile SingularAttribute<Contact, String> newContactType;
    public static volatile SingularAttribute<Contact, Profile> profile;
    public static volatile SingularAttribute<Contact, String> name;
    public static volatile SingularAttribute<Contact, Boolean> synchronised;
    public static volatile SingularAttribute<Contact, String> id;
    public static volatile SingularAttribute<Contact, String> oldContactType;
    public static volatile SingularAttribute<Contact, String> phoneName;
    public static volatile SingularAttribute<Contact, Timestamp> addedOn;

}