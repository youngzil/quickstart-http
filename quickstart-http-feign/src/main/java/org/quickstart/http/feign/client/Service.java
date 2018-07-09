package org.quickstart.http.feign.client;

public class Service {

	protected String Id;
	protected String Acl;

	public Service() {
	}

	public Service(String id, String acl) {
		Id = id;
		Acl = acl;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getAcl() {
		return Acl;
	}

	public void setAcl(String acl) {
		Acl = acl;
	}

}
