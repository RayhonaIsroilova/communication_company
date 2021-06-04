package ecma.ai.ussdapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ecma.ai.ussdapp.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SimCard extends AbsEntity implements UserDetails {

    private String name;

    private String code; //90 91

    private String number; //7 xonali soni

    @Column(unique = true)
    private String simCardNumber;

    @ManyToOne
//    @JsonBackReference
    private Client client;

    private double balance;

    @ManyToOne
    private Tariff tariff;

    private String pinCode;
//    private boolean tariffIsActive = false;//bu ta'rifni

    private boolean active = false; //simcard xolati

    private boolean isCredit = false; //qarz berishi mnligi


    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.pinCode;
    }

    @Override
    public String getUsername() {
        return null;
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


    //

}
