# CalculationProject

Create the web application in Java which meets the following criteria:
* The web application should offer an REST-like API interface (HTTP/JSON) for simple arithmetic calculations.
* The API offers an operation which accepts a list of numbers,

does some calculations and returns calculation result.
Calculation result is always a single number.

It should be defined in configuration how the calculations are done.
It is up to you to define how the configuration looks like.

For example, if the inputs are `X1` and `X2`, the calculation could be `X1 + 5*X2/X1`.
Optional additional features:
* The web application should feature configurable logging.
* The web application should contain an HTML page

where the user can enter inputs, trigger API call and view calculation result,
as well as view current configuration.

* The web application should be packaged as an EAR and work on either Oracle WebLogic or IBM WebSphere or Apache TomEE.

HTML page is located over http://localhost:8080/calculation/index.html
