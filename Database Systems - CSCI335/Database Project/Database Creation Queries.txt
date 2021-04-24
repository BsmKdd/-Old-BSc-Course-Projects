Create table Supermarket( Franchise varchar(20) primary key,
			  OwnerName varchar(20),
			  MainLocation varchar(40)
			)

Create table Employee( EmployeeID int primary key,
                       EName varchar(40),
		       EAddress varchar(100),
		       Position varchar(20),
		       Salary int,
		       HoursWorked int,
		       EBranchNumber int foreign key references branch(BranchNumber),
                       Phone varchar(20)
		      )

alter table branch add MgrID int foreign key references Employee(EmployeeID)

Create table Product( ProductID varchar(20) primary key,
                      Price int,
		      Type varchar(20),
                      Stock int,
		      BranchNumber int foreign key references Branch(BranchNumber)
	            )

Create table Customer(CID int primary key,
                      CName varchar(40),
		      CAddress varchar(100),
		      Phone varchar(20)
		     )

Create table Payment( PaymentID int primary key,
                      Amount int,
		      PaymentDate date,
		      CID int foreign key references Customer(CID)
		    )



Create table ProductOrder( OrderID int primary key,
			   OrderingDate date,
			   TotalCost int,
			   Tax int
			 )




Create table InpersonPurchase(OrderID int primary key foreign key references ProductOrder(OrderID),
                              PickupTime time,
                              PickupDate date,
			     )



create table OnlinePurchase(OrderID int primary key references ProductOrder(OrderID),
                            ProductsURL varchar(100),
			    ShippingDate date,
			    ExpectedArrivalDate date
			   )



Create table Shipper(ShipperID varchar(20) primary key,
                     Fees int,
		     ShipperLocation varchar(40)
		    )



alter table OnlinePurchase add ShipperID int foreign key references Shipper(ShipperID)



Create table ProductsContainted( ProductID varchar(20) foreign key references Product(ProductID),
                                 OrderID int foreign key references ProductOrder(OrderId),
				 Primary key (ProductID, OrderID)
			       )



Create table IncludedProducts(   ProductID varchar(20) foreign key references Product(ProductID),
                                 OrderID int foreign key references ProductOrder(OrderId),
				 Quantity int,
				 Primary key (ProductID, OrderID)
                              )



Create table Serves(EID int foreign key references Employee(EmployeeID),
                    CID int foreign key references Customer(CID),
		    Primary key(CID,EID)
		   )

alter table ProductOrder drop column TotalCost, Tax

alter table ProductOrder add CID int foreign key references Customer(CID)

alter table Employee add SuperID







						



