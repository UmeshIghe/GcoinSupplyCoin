package apiRequests;

import java.util.ArrayList;
import java.util.List;

public class LinkPartners {

	public LinkPartners(List<String> part) {
		setDefaultValues(part);
	}

	private List<String> partners;

	public List<String> getPartners() {
		return partners;
	}

	public void setPartners(List<String> partners) {
		this.partners = partners;
	}

	private void setDefaultValues(List<String> part) {
		setPartners(part);

	}
}
