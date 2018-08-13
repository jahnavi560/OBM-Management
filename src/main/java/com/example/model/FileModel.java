package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="file")
/*@NamedQueries({
    @NamedQuery(
        name = "findFilesByEmail",
        query = "from FileModel u where u.owner = :email"
        )})*/
public class FileModel {

		@Id
		@GeneratedValue
	    @Column(name = "file_id")
	    private Long id;
		
	    @Column(name = "file_name")
		private String name;
	    
	    @Column(name = "owner")
		private String owner;
	    
	    @Column(name = "file_path")
		private String path;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public FileModel() {
			
		}
		public FileModel(Long id, String name, String owner, String path) {
			super();
			this.id = id;
			this.name = name;
			this.owner = owner;
			this.path = path;
		}
		
		

}
