package apiRequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteOrg {

	private List<Map<String, String>> invited_members;
	private String name;
	private String type;
	private List<String> partners;

	public InviteOrg() {
		setDefaultValues();
	}

	public List<Map<String, String>> getInvited_members() {
		return invited_members;
	}

	public void setInvited_members(List<Map<String, String>> invited_members) {
		this.invited_members = invited_members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getPartners() {
		return partners;
	}

	public void setPartners(List<String> partners) {
		this.partners = partners;
	}

	private void setDefaultValues() {
		Map<String, String> invtMem = new HashMap<String, String>();
		invtMem.put("email", "{{email}}");
		invtMem.put("role", "admin");
		List<Map<String, String>> mem = new ArrayList<Map<String, String>>();
		mem.add(invtMem);
		setInvited_members(mem);
		setName("Refiner 110");
		setType("Refiner Operator");
		List<String> part = new ArrayList<>();
		setPartners(part);

	}
}
