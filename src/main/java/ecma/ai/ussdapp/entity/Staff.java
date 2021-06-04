package ecma.ai.ussdapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ecma.ai.ussdapp.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.security.DenyAll;
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Staff extends AbsEntity implements UserDetails {

    @ManyToOne
    @JoinColumn(name = "staff")
//    @JsonBackReference
    private Filial filial;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;  //ROLE MANAGER

    @Column(unique = true)
    private String userName;

    private String fullName;

    private String turniket = "ID" + UUID.randomUUID().toString();

    private String password;

    private String position; //hr manager raqamlar manager,


    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
