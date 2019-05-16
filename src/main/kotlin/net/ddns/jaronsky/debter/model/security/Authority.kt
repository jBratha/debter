package net.ddns.jaronsky.debter.model.security

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "AUTHORITY")
class Authority(
        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
        @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1, initialValue = 3)
        var id: Long? = null,

        @Column(name = "NAME", length = 50)
        @NotNull
        @Enumerated(EnumType.STRING)
        var name: AuthorityName? = null,

        @JsonIgnore
        @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
        var users: List<User>? = null
)