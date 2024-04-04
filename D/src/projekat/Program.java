package projekat;

import java.util.List;

public class Program {
	private String name;
	private List<String> subjects;

	
	
	public Program() {
		super();
	}
	
	
	public Program(String name, List<String> subjects) {
		this.name = name;
		this.subjects = subjects;
	}
	

	public Program(String name) {
		this.name = name;
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Program) {
			Program p = (Program) obj;
			if(p.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public String toString() {
		return getName();
	}
	
	
	
	
}
