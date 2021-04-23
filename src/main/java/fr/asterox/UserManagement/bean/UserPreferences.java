package fr.asterox.UserManagement.bean;

public class UserPreferences {

	private int attractionProximity = Integer.MAX_VALUE;
	private String currency = "USD";
	private int lowerPricePoint = 0;
	private int highPricePoint = Integer.MAX_VALUE;
	private int tripDuration = 1;
	private int ticketQuantity = 1;
	private int numberOfAdults = 1;
	private int numberOfChildren = 0;

	public UserPreferences() {
	}

	public UserPreferences(int attractionProximity, String currency, int lowerPricePoint, int highPricePoint,
			int tripDuration, int ticketQuantity, int numberOfAdults, int numberOfChildren) {
		super();
		this.attractionProximity = attractionProximity;
		this.currency = currency;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}

	public int getAttractionProximity() {
		return attractionProximity;
	}

	public void setAttractionProximity(int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getLowerPricePoint() {
		return lowerPricePoint;
	}

	public void setLowerPricePoint(int lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}

	public int getHighPricePoint() {
		return highPricePoint;
	}

	public void setHighPricePoint(int highPricePoint) {
		this.highPricePoint = highPricePoint;
	}

	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public int getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attractionProximity;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + highPricePoint;
		result = prime * result + lowerPricePoint;
		result = prime * result + numberOfAdults;
		result = prime * result + numberOfChildren;
		result = prime * result + ticketQuantity;
		result = prime * result + tripDuration;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPreferences other = (UserPreferences) obj;
		if (attractionProximity != other.attractionProximity)
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (highPricePoint != other.highPricePoint)
			return false;
		if (lowerPricePoint != other.lowerPricePoint)
			return false;
		if (numberOfAdults != other.numberOfAdults)
			return false;
		if (numberOfChildren != other.numberOfChildren)
			return false;
		if (ticketQuantity != other.ticketQuantity)
			return false;
		if (tripDuration != other.tripDuration)
			return false;
		return true;
	}

}
