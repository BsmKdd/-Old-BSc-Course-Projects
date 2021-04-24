
-- Selecting Employees in a specific branch

select EmployeeID 
From Employee 
where EBranchNumber = 1

-- Selecting Employees of a specific position

Select * 
from Employee 
where Position = 'Bagger'

-- Selecting Employees with highest salaries 

Select * 
From Employee 
Where Salary in ( Select MAX(Employee.Salary) from employee ) 

-- Selecting Employees that live in a specific state

Select * 
from Employee 
where EAddress like '%(NC)%'

-- Selecting the names of the employees and the customers they served 

Select EName, CName 
From Employee, Customer, Serves 
where Serves.CID = Customer.CID and Serves.EID = Employee.EmployeeID

-- Selecting the product that has been ordered the most within a specific branch

Select P.BranchNumber, P.Price, P.ProductID, P.Stock, P.Type 
From Product as P , IncludedProducts 
where IncludedProducts.ProductID = P.ProductID and IncludedProducts.Quantity =  (Select MAX(IncludedProducts.Quantity) from IncludedProducts)  

-- Selecting Customers that have ordered 3 or more times

Select C.CID 
from Customer as C, ProductOrder as P 
where P.CID = C.CID  
group by C.CID 
having count(P.CID) >= 3

-- Showing the stock of each Product in a specific branch

Select ProductID, Product.type, Product.Stock 
from product 
where BranchNumber = 1

-- Showing the total stock of a specific type of product in all branches

Select product.type, sum(product.stock) 
from product 
where product.type = 'meat' group by product.type

-- Shows the total cost of each order

Select Productorder.OrderID, SUM(IncludedProducts.Quantity*Product.Price) as TotalCost 
from IncludedProducts, Product, ProductOrder 
Where ProductOrder.OrderID = IncludedProducts.OrderID and IncludedProducts.ProductID = Product.ProductID 
Group by ProductOrder.OrderID

-- Shows the total cost of a specific order

Select Productorder.OrderID, SUM(IncludedProducts.Quantity*Product.Price) as TotalCost 
from IncludedProducts, Product, ProductOrder 
Where ProductOrder.OrderID = IncludedProducts.OrderID and IncludedProducts.ProductID = Product.ProductID and ProductOrder.OrderID = 1001112463 
Group by ProductOrder.OrderID





