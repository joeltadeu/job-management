Feature: Company - Create, Update, Get by Id, Get All, Delete
  Background:
    * def company_url = host + config.uri.company

  @CompanyService
  @CreatingCompany
  Scenario: Creating a Company
    * def companyResponse = call read('Company.feature@CreateCompany')
    And match companyResponse.response.id == '#notnull'
    * call read('Company.feature@DeleteCompany') { id: '#(companyResponse.response.id)'}


  @CompanyService
  @UpdatingCompany
  Scenario: Updating a Company
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * call read('Company.feature@UpdateCompany') { id: '#(companyId)', name: 'Company Karate'}
    * def companyGetResponse = call read('Company.feature@GetCompanyById') { id: '#(companyId)' }
    And match companyGetResponse.response.name == 'company name'
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @CompanyService
  @GettingCompany
  Scenario: Getting a Company
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyGetResponse = call read('Company.feature@GetCompanyById') { id: '#(companyCreateResponse.response.id)' }
    * call read('Company.feature@DeleteCompany') { id: '#(companyGetResponse.response.id)'}

  @CompanyService
  @DeletingCompany
  Scenario: Deleting a Company
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * print companyCreateResponse
    * def companyId = companyCreateResponse.response.id
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}
    * def companyGetResponse = call read('Company.feature@GetCompanyByIdNotFound') { id: '#(companyId)' }

  @CompanyService
  @GetCompanyList
  Scenario: Get Company List
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    Given url company_url
    When method GET
    Then status 200
    And match each response.[*] == '#notnull'
    And match each response.[*] contains deep {id: '#number'}
    And match each response.[*] contains deep {name: '#string'}
    And match each response.[*] contains deep {description: '#string'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyCreateResponse.response.id)'}

  @CreateCompany
  @Ignore
  Scenario: Insert Company
    * def req = read('data/company_request.json')
    * req.name = config.params.company.name
    * req.description = config.params.company.description
    Given url company_url
    And request req
    When method POST
    Then status 201

  @CreateCompanyWithParams
  @Ignore
  Scenario: Insert Company
    * def req = read('data/company_request.json')
    * req.name = name
    * req.description = description
    Given url company_url
    And request req
    When method POST
    Then status 201

  @GetCompanyByIdNotFound
  @Ignore
  Scenario: Get Company By Id
    Given url company_url + '/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetCompanyById
  @Ignore
  Scenario: Get Company By Id
    Given url company_url + '/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    And match response contains deep {id: '#number'}
    And match response contains deep {name: '#string'}
    And match response contains deep {description: '#string'}

  @UpdateCompany
  @Ignore
  Scenario: Update Company
    * def req = read('data/company_request.json')
    * req.name = config.params.company.name
    * req.description = config.params.company.description
    Given url company_url + '/' + id
    And request req
    When method PUT
    Then status 200

  @DeleteCompany
  @Ignore
  Scenario: Delete Company
    Given url company_url + '/' + id
    When method DELETE
    Then status 204