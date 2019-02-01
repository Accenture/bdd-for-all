@Xml
Feature: Testing out the basic step definitions for SOAP

  @Smoke @RemoteServer
  Scenario: Simple SOAP endpoint (BSS1)
    Given I am a XML API consumer
      And I am executing test "BSS1"
     When I request GET "/CountryInfoService.wso" on "http://webservices.oorsprong.org/websamples.countryinfo"
      And I set the XML body to
      """
        <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
            <Body>
                <ListOfContinentsByName xmlns="http://www.oorsprong.org/websamples.countryinfo"/>
            </Body>
        </Envelope>
      """
     Then I should get a status code of 200

  @Smoke @RemoteServer
  Scenario: Simple SOAP endpoint with SOAPAction (BSS2)
    Given I am a XML API consumer
      And I am executing test "BSS2"
     When I request GET "/CountryInfoService.wso" on "http://webservices.oorsprong.org/websamples.countryinfo"
      And I set the SOAPAction to "http://www.SoapClient.com/SQLDataSQL" and body as
      """
        <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
            <Body>
                <ProcessSQL xmlns="http://www.SoapClient.com/xml/SQLDataSoap.wsdl">
                    <DataSource>[string]</DataSource>
                    <SQLStatement>[string]</SQLStatement>
                    <UserName>[string]</UserName>
                    <Password>[string]</Password>
                </ProcessSQL>
            </Body>
        </Envelope>
      """
     Then I should get a status code of 200


