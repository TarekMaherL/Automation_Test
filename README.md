# MDI_Test
Amazon_Test
# Amazon Test Automation

This project contains automated tests for Amazon website scenarios using Selenium WebDriver and TestNG. 

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 8 or later is installed on your machine.
- Apache Maven 3.6 or later is installed.
- An IDE Installed.


configuration:

First Scenario:

	you need to provide a valid Email that not registared on amazon in (Email.sendKeys) Line 44


Second Scenario:

	you need to choose which category you want to select , it's on the Second category by defualt you can change it by changing the number [2] in the end of line 62
	
	you need to choose which product you want to select , it's on the first product by defualt you can change it by changing the number [1] in the end of line 65

	you need to choose which item you want to select , it's on the Second item by defualt you can change it by changing the number [2] in the end of line 68 

	you can choose the quantity it's two by defualt ( say you want to get 3 of that Item you need to change "quantity_1" --> "quantity_2"  ##Note the quantity you want  = "quantity_your quantity -1"    Line 76

	in Assertion:

		put the name of the Item in Line 92
		
		put the price of the Item in Line 93 ##Note (put the currancy also)

		Put the quantity of the item in Line 94 

		that will be as you expected Results



and Thats It 
Thank you,


	
