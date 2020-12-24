Feature: Purchase Process Automation

Scenario: Test scenario for automating Purchase process

Then Create a Purchase Order with excel data at "C:\Users\Sravan Kumar\Desktop\PO Process Data.xlsx,Purchase Data"
Then Approve PO & create Item Receipt
Then Generate Vendor Invoice
Then Approve Bill & accept payment
Then Generate Vendor Return Authorization
Then Generate Item Shipment
Then Generate Bill Credit