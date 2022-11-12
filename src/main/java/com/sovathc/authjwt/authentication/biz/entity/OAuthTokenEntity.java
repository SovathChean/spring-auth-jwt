package com.sovathc.authjwt.authentication.biz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name=OAuthTokenEntity.TABLE_NAME)
public class OAuthTokenEntity {
    public static final String TABLE_NAME = "oauthtokens";
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String uniqueKey;
    private LocalDateTime requestAt;
}
