package ru.otus.models;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.otus.adapters.json.UserEntityJsonAdapter;
import ru.otus.adapters.xml.LocalDateTimeXMLAdapter;
import ru.otus.adapters.xml.UserEntityTypeXMLAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "statistic")
@NamedStoredProcedureQuery(
    name = "insert_statistic",
    procedureName = "insert_db_statistic",
    parameters = {
        @StoredProcedureParameter(name = "name_marker",   mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = "jsp_page_name", mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = "ip_address",    mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = "user_agent",    mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = "client_time",   mode = ParameterMode.IN, type = Date.class),
        @StoredProcedureParameter(name = "server_time",   mode = ParameterMode.IN, type = Date.class),
        @StoredProcedureParameter(name = "session_id",    mode = ParameterMode.IN, type = String.class),
        @StoredProcedureParameter(name = "user_id",       mode = ParameterMode.IN, type = Long.class),
        @StoredProcedureParameter(name = "prev_id",       mode = ParameterMode.IN, type = Long.class),
    }
)
@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticEntity implements DataSet, Serializable
{
    @Id
    @SequenceGenerator(name="stat_identifier", sequenceName="statistic_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stat_identifier")
    @Column(name = "id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(name = "name_marker", nullable = false)
    @XmlAttribute(required = true)
    private String nameMarker;

    @Basic
    @Column(name = "jsp_page_name", nullable = false)
    @XmlElement
    private String jspPageName;

    @Basic
    @Column(name = "ip_address", nullable = false)
    @XmlElement
    private String ipAddress;

    @Basic
    @Column(name = "user_agent", nullable = false)
    @XmlElement
    private String userAgent;

    @Basic
    @Column(name = "client_time")
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime clientTime;

    @Basic
    @Column(name = "server_time")
    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime serverTime;

    @Basic
    @Column(name = "session_id", nullable = false)
    @XmlElement
    private String sessionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @XmlJavaTypeAdapter(UserEntityTypeXMLAdapter.class)
    @JsonbTypeAdapter(UserEntityJsonAdapter.class)
    private UserEntity user;

    @Basic
    @Column(name = "prev_id")
    @XmlAttribute
    private Long previousId;

    @Override
    public String getName()
    {
        return getNameMarker();
    }

    @Override
    public void setName(String name)
    {
        setNameMarker(name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
