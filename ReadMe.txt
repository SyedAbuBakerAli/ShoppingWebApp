Tech Stack : Java, Springboot and MySQL 
follow Spring MVC model

All APIs implemented with Error Handling and API Response Folder have api response.

to run this project create db and tables given in schema forlder and changle application.properties config for database

then  open project in intellij and click on run button 

first populate schema like 
1. get inventory (by populate data)          end points: http://localhost:8080/inventory
2. fetch Coupon                              end points:  http://localhost:8080/fetchCoupons
3. create order          end points: http://localhost:8080/{userId}/order?qty=10&coupon=OFF5
4. make payment          end points: http://localhost:8080/{userId}/{orderId}/pay?amount=1000
5. get list of order                         end points: http://localhost:8080/{userId}/orders
6. get list of of transaction with userId and orderId          end points: http://localhost:8080/{userId}/orders/{orderId}

