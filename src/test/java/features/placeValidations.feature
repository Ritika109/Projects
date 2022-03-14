Feature: Validating Place API

@AddPlace
Scenario Outline: Validating if place is added using AddPlace API
Given Add Place Payload with "<name>" "<language>" "<address>"
When user call 'AddPlaceAPI' with 'POST' request
Then The API call gets success with 200 status code
And The 'status' in response code is 'OK'
And The 'scope' in response code is 'APP'
And Verify the placeId created maps to "<name>" using 'GetPlaceAPI'


Examples:
|  name                  |language       | address		                    |
|  Frontline house       | French-IN     | 29, side layout, cohen 09      |
|  Maybelline house      | English-IN    | Sea cross cetre                |


@DeletePlace
Scenario: Validating if place is deleted using DeletePlace API
Given Delete Place API
When user call 'DeletePlaceAPI' with 'POST' request
Then The API call gets success with 200 status code
And The 'status' in response code is 'OK'



