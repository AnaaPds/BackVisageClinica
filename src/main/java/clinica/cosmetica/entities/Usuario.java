package clinica.cosmetica.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class Usuario {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String email;
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Roles role; 

		public Usuario(Long id, String email, String password, Roles role) {
			this.id = id;
			this.email = email;
			this.password = password;
			this.role = role;
		}

		public Usuario() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Roles getRole() {
			return role;
		}

		public void setRole(Roles role) {
			this.role = role;
		}
}