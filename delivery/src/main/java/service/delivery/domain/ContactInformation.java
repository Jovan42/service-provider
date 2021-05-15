package service.delivery.domain;

import lombok.Getter;
import lombok.Setter;
import service.sharedlib.domain.BaseEntity;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ContactInformation extends BaseEntity {
    private String phoneNumber;
    private String emailAddress;
}
