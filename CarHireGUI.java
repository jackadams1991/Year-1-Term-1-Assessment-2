// Programmer: Jack Adams S0201412
// File: CarHireGUI.java
// Date: 27 September 2018
// Assessment: COIT11222 assignment two, T2-18
// Purpose: Class generates the Java GUI for the user and allows the user to enter a name, license number and the ammount of hire days. This is then displayed within the text field.
// Purpose: The class also displays all entered customers and allows the user to search for a customer.


import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

// class definition
public class CarHireGUI extends JFrame implements ActionListener
{
        //GUI components building
        private JLabel nameLabel=new JLabel("Customer name");
        private JLabel licenseLabel=new JLabel("License number");
	private JLabel daysLabel=new JLabel("Hired days");
	private JTextField nameField=new JTextField(28);
        private JTextField licenseField=new JTextField(14);
        private JTextField daysField=new JTextField(7);

        private JButton enterButton=new JButton("Enter");   //
	private JButton displayButton=new JButton("Display");
        private JButton searchButton= new JButton("Search");
        private JButton exitButton=new JButton("Exit");
        private JTextArea textArea=new JTextArea(16,38);
        private JScrollPane scrollPane; // scroll pane for the text area


	private String customerName = ""; //Variable for customer's name
	private String licenseNumber = ""; //Variable for customer's license number
	private int hireDays = 0; //Variable for customer's amount of days car was hired for
	private double rentalCost = 0; //Variable for customer's total rental cost
	private double rentalDayAvg = 0; //Variable for statistical information, used to hold the average days of hire
	private double rentalTotal = 0; //Variable for statistical information, used to hold total rental amount received for all customers


        private CarHire [] carHireArray = new CarHire[MAX_NUM]; //Declaration of object array
        private int currentCustomer = 0; //Variable used to keep track of current customer count


        private static final int MAX_NUM = 10; //Constant declared to be used as array size

        private static final int FRAME_WIDTH = 490;// window size
        private static final int FRAME_HEIGHT = 430;

        //Constructs
        public CarHireGUI()
        {
           super("  XYZ Car Hire App   ");
           setLayout(new FlowLayout());  //FlowLayout

           add(nameLabel);              //add componts to JFrame
           add(nameField);
           add(licenseLabel);
           add(licenseField);
           add(daysLabel);
           add(daysField);

           scrollPane = new JScrollPane(textArea); 	// add text area to the scroll pane
           scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // just need vertical scrolling
	   add(scrollPane);

           add(enterButton);
           add(displayButton);
           add(searchButton);
           add(exitButton);

           add(textArea);
           enterButton.addActionListener(this);  //listen to event
           displayButton.addActionListener(this);
           searchButton.addActionListener(this);
           exitButton.addActionListener(this);
        }

        //event handling method
        public void actionPerformed(ActionEvent e)
        {
			String actionString=e.getActionCommand();
			switch(actionString)
			{
				case "Enter":
					enterData();
					break;
				case "Display":
					displayAll();
					break;
				case "Search":
					search();
					break;
				case "Exit":
					exit();
					break;
				default:
					System.out.println("invalid input");
			}
		}

        //Method to take in user input, calculate data and then display data to JTextArea
        public void enterData()
        {

           if(nameField.getText().compareTo("") == 0) //Check for data in name field
           {
                    JOptionPane.showMessageDialog(null, "You must enter a customer name"); //Error message displayed if no name has been entered
                    nameField.requestFocus();
                    return;
		   }
		   else
		   if(licenseField.getText().compareTo("") == 0) //Check for data in license number field
		   {
			   JOptionPane.showMessageDialog(null, "You must enter a license number"); //Error message displayed if no license number has been entered
			   licenseField.requestFocus();
			   return;
		   }
		   else
		   if(daysField.getText().compareTo("") == 0) //Check for data in hire days field
		   {
			   JOptionPane.showMessageDialog(null, "You must enter days hired"); //Error message displayed if no hire days has been entered
			   daysField.requestFocus();
			   return;
		   }
		   else
		   {
			   displayHeading(); //Call displayHeading() method

			   //Read in customer name, license number, days hired from JTextFields
			   customerName = nameField.getText();
			   licenseNumber = licenseField.getText();
			   hireDays = Integer.parseInt(daysField.getText());

			   carHireArray[currentCustomer] = new CarHire(customerName, licenseNumber, hireDays, rentalCost); //Creation of CarHire objects and adding as constructor parameters to the CarHire array

			   //Assigning data to variables
			   customerName = carHireArray[currentCustomer].getCustomerName();
			   licenseNumber = carHireArray[currentCustomer].getLicenseNumber();
			   hireDays = carHireArray[currentCustomer].getDaysHired();
			   rentalCost = carHireArray[currentCustomer].calculateHireRental();

			   printCustomer(customerName, licenseNumber, hireDays, rentalCost); //Call of printCustomer method to display entered customer details

			   currentCustomer = currentCustomer + 1; //Incremental current cusomer number

			   //Clear textfields and return focus to Customer Name field
			   nameField.setText("");
			   licenseField.setText("");
			   daysField.setText("");
			   nameField.requestFocus();
			}
		}

        //Method to display all entered customers into the JTextArea
		public void displayAll()
		{
			if(currentCustomer == 0)
			{
				JOptionPane.showMessageDialog(null, "No customer entered"); //Error message to be displayed if no customers have been entered into the array
			}
			else
			{
				displayHeading(); //Call of displayHeading() method
				for(int x = 0; x < currentCustomer; ++x) //for loop that checks against the currentCustomer variable
				{
                    customerName = carHireArray[x].getCustomerName();
				    licenseNumber = carHireArray[x].getLicenseNumber();
				    hireDays = carHireArray[x].getDaysHired();
				    rentalCost = carHireArray[x].getRentalCost();


                    //Displays customer details for each entry found in the car hire array
                    printCustomer(customerName, licenseNumber, hireDays, rentalCost); //Call of printCustomer method to display entered customer details

                    rentalDayAvg = rentalDayAvg + carHireArray[x].getDaysHired(); //Adding the days hired amount for each entry to a variable
                    rentalTotal = rentalTotal + carHireArray[x].getRentalCost(); //Adding up the total rental ammounts received and adding to a variable
				}

				//Displaying statistical information for all entries in the array, including the number of entries, the average hire days and the total rental amount received for all entries
				textArea.append("----------------------------------------------------------------------------------------------------\n");
				textArea.append(currentCustomer + " entries\n");
				textArea.append(String.format("Average days hired: %.2f\n", (rentalDayAvg / currentCustomer)));
				textArea.append("Total rental recieved: ");
				textArea.append(String.format("$%.2f", rentalTotal));
			}
			//Resetting variables back to 0, this ensures data is not compounded
			rentalDayAvg = 0;
			rentalTotal = 0;

			nameField.requestFocus(); //Returns focus back to the name field
			return;

		}


        //Method allows users to search through the array using a customer name
	    public void search()
	    {
            String customerSearchName; //String variable used to have search term assigned to it
            boolean searchResultFound = false; //Boolean variable for if a customer has been found, automatically set to false

            customerSearchName = JOptionPane.showInputDialog(null, "Enter a customer name"); //Input box for search term

			for(int x = 0; x < currentCustomer; ++x)
			{
				if(customerSearchName.equals(carHireArray[x].getCustomerName())) //Searching through array with user entered search term
				{
					searchResultFound = true; //Boolean variable set to true if search term matches an array object

					//Getting customer details for the given customer and setting them to the global variables
					customerName = carHireArray[x].getCustomerName();
					licenseNumber = carHireArray[x].getLicenseNumber();
					hireDays = carHireArray[x].getDaysHired();
			   		rentalCost = carHireArray[x].calculateHireRental();
				}
			}

			if(searchResultFound == true) //If-else loop, if the searchResultFound = true, the customer details displayed.
			{
				//Printing customer info of found customer
				displayHeading();
				printCustomer(customerName, licenseNumber, hireDays, rentalCost); //Call of printCustomer method to display entered customer details
			}
			else //Else if the searchResultFound = false, an error message is displayed and the focus is returned to Customer Name field
			{
                            JOptionPane.showMessageDialog(null, "No such customer found"); //Error message if search term is not found in Array
                            nameField.requestFocus(); //Returns focus back to the name field
			    return;
			}
	    }


	public void exit() //Exit the appilication
	{
            JOptionPane.showMessageDialog(null, "Thank you for using the XYZ Car Hire App"); //Dialog box displayed before application closes
            dispose();
            System.exit(0);
	}

       private void displayHeading() //Display heading - called for all other methods
       {
            textArea.setText(String.format("%-23s%-25s%-28s%-26s\n", "Customer name", "License number", "Days hired", "Rental"));
            textArea.append("----------------------------------------------------------------------------------------------------\n");
	}

	private void printCustomer(String customerName, String licenseNumber, int hireDays, double rentalCost) //Method to handle printing of customer details
	{
            textArea.append(String.format("%-30s%-33s%-28s$%.2f\n", customerName, licenseNumber, hireDays, rentalCost));
	}

       //main method
       public static void main(String[] args)
       {
            JFrame frame = new CarHireGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setVisible(true);
            frame.setResizable(false);
	}

}// end of class definition
