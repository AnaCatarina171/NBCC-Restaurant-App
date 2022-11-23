package ca.nbcc.restapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeDetails extends Employee implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeDetails(Employee emp) {
		super(emp);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
		
		//authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return null;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}
	
	@Override
	public String getUsername() {
		return super.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
