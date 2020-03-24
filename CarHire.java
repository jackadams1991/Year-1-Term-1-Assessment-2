// Programmer: Jack Adams S0201412
// File: CarHire.java
// Date: 27 September 2018
// Assessment: COIT11222 assignment two, T2-18
// Purpose: CarHire class takes in data from CarHireGUI class and passes it through the constructors. This is then fed back to the CarHireGUI class.

public class CarHire
{
	private String customerName; //Variable for customer's name
	private String licenseNumber; //Variable for customer's license number
	private int hireDays; //Variable for customer's number of hire days
	private double rentalCost; //Variable for customer's rental total

	//Default constructor
	public CarHire()
	{
		customerName = "";
		licenseNumber = "";
		hireDays = 0;
		rentalCost = 0;

	}

	//Parameterised constructor
	public CarHire(String customer, String number, int days, double rental)
	{
		customerName = customer;
		licenseNumber = number;
		hireDays = days;
		rentalCost = calculateHireRental();
	}

	//Four set (mutators) methods
	public void setCustomerName(String customer)
	{
		customerName = customer;
	}

	public void setLicenseNumber(String number)
	{
		licenseNumber = number;
	}

	public void setDaysHired(int days)
	{
		hireDays = days;
	}

	public void setRentalCost(double rental)
	{
		rentalCost = rental;
	}


	//Four get (accessor) methods
	public String getCustomerName()
	{
		return customerName;
	}

	public String getLicenseNumber()
	{
		return licenseNumber;
	}

	public int getDaysHired()
	{
		return hireDays;
	}

	public double getRentalCost()
	{
		return rentalCost;
	}

	//Method created to handle calculation of rental amount based on number of hire days
	public double calculateHireRental()
	{
		double rentalTotal; //Variable for the total rental amount
		int num; //Variable used for loop control variable

		final double COST_MAX_3_DAYS = 34.50D; //Set constant for the 1 - 3 days rental amount
		final double COST_MAX_7_DAYS = 30.50D; //Set constant for the 4 - 7 days rental amount
		final double COST_OVER_7_DAYS = 22.50D; //Set constant for more than 7 days rental amount
		final int DAY_LIMIT_1 = 3; //Set constant for day limit up to 3
		final int DAY_LIMIT_2 = 7; //Set constant for day limit up to 7

		//Comparing entered number of days to calculate correct rental total
		if(hireDays <= DAY_LIMIT_1 && hireDays > 0)
		{
			rentalTotal = hireDays * COST_MAX_3_DAYS;
			//If number of days entered falls between 1 - 3, COST_MAX_3_DAYS is used to calculate total.
		}
		else
		if(hireDays > DAY_LIMIT_1 && hireDays <= DAY_LIMIT_2)
		{
			rentalTotal = ((DAY_LIMIT_1 * COST_MAX_3_DAYS) + (hireDays - DAY_LIMIT_1) * COST_MAX_7_DAYS);
			//If number of days entered falls between 4 - 7, then the first 3 days is calculated at the COST_MAX_3_DAYS
			//the remaining number of days are calulated at COST_MAX_7_DAYS
		}
		else
		if(hireDays > 7)
		{
			rentalTotal = ((DAY_LIMIT_1 * COST_MAX_3_DAYS) + ((DAY_LIMIT_2 - DAY_LIMIT_1) * COST_MAX_7_DAYS)
			+ (hireDays - DAY_LIMIT_2) * COST_OVER_7_DAYS);
			//If total number of days entered above 7, then the first 3 days are calculated at COST_MAX_3_DAYS, the next 4 calculated at COST_MAX_7_DAYS
			//the remaining number of days are calulated at COST_OVER_7_DAYS
		}
		else
		{
			rentalTotal = 0;
			//If an invalid number of days is entered, the program returns zero.
		}
        return rentalTotal;
	}
}