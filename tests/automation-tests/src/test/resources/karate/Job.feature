Feature: Job - Create, Update, Get by Id, Get All, Delete
  Background:
    * def company_url = host + config.uri.company

  @JobService
  @CreatingJob
  Scenario: Creating a Job
    * def companyResponse = call read('Company.feature@CreateCompany')
    And match companyResponse.response.id == '#notnull'
    * def companyId = companyResponse.response.id
    * def jobResponse = call read('Job.feature@CreateJob') { companyId: '#(companyId)'}
    * def jobId = jobResponse.response.id
    * call read('Job.feature@DeleteJob') { companyId: '#(companyId)', id: '#(jobId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @JobService
  @UpdatingJob
  Scenario: Updating a Job
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def jobResponse = call read('Job.feature@CreateJob') { companyId: '#(companyId)'}
    * def jobId = jobResponse.response.id
    * call read('Job.feature@UpdateJob') { companyId: '#(companyId)', id: '#(jobId)', title: "Job title changed", description: "Job description changed", minSalary: 60000, maxSalary: 90000, location: "Dublin, Ireland"}
    * def jobGetResponse = call read('Job.feature@GetJobById') { companyId: '#(companyId)', id: '#(jobId)' }
    And match jobGetResponse.response.title == 'Job title changed'
    And match jobGetResponse.response.description == 'Job description changed'
    And match jobGetResponse.response.minSalary == 60000
    And match jobGetResponse.response.maxSalary == 90000
    And match jobGetResponse.response.location == 'Dublin, Ireland'
    * call read('Job.feature@DeleteJob') { companyId: '#(companyId)', id: '#(jobId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @JobService
  @GettingJob
  Scenario: Getting a Job
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def jobResponse = call read('Job.feature@CreateJob') { companyId: '#(companyId)'}
    * def jobId = jobResponse.response.id
    * def jobGetResponse = call read('Job.feature@GetJobById') { companyId: '#(companyId)', id: '#(jobId)' }
    * call read('Job.feature@DeleteJob') { companyId: '#(companyId)', id: '#(jobId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @JobService
  @DeletingJob
  Scenario: Deleting a Job
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def jobResponse = call read('Job.feature@CreateJob') { companyId: '#(companyId)'}
    * def jobId = jobResponse.response.id
    * call read('Job.feature@DeleteJob') { companyId: '#(companyId)', id: '#(jobId)'}
    * def companyGetResponse = call read('Company.feature@GetJobByIdNotFound') { companyId: '#(companyId)', id: '#(jobId)' }
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @JobService
  @GetJobList
  Scenario: Get Job List
    * def companyCreateResponse = call read('Company.feature@CreateCompany')
    And match companyCreateResponse.response.id == '#notnull'
    * def companyId = companyCreateResponse.response.id
    * def jobResponse = call read('Job.feature@CreateJob') { companyId: '#(companyId)'}
    * def jobId = jobResponse.response.id
    Given url company_url + '/' + companyId + '/jobs'
    When method GET
    Then status 200
    And match each response.[*] == '#notnull'
    And match each response.[*] == '#notnull'
    And match each response.[*] contains deep {id: '#number'}
    And match each response.[*] contains deep {title: '#string'}
    And match each response.[*] contains deep {description: '#string'}
    And match each response.[*] contains deep {minSalary: '#number'}
    And match each response.[*] contains deep {maxSalary: '#number'}
    And match each response.[*] contains deep {location: '#string'}
    * call read('Job.feature@DeleteJob') { companyId: '#(companyId)', id: '#(jobId)'}
    * call read('Company.feature@DeleteCompany') { id: '#(companyId)'}

  @CreateJob
  @Ignore
  Scenario: Insert Job
    * def req = read('data/job_request.json')
    * req.name = config.params.job.name
    * req.description = config.params.job.description
    * req.minSalary = config.params.job.minSalary
    * req.maxSalary = config.params.job.maxSalary
    * req.location = config.params.job.location
    Given url company_url +  '/' + companyId + '/jobs'
    And request req
    When method POST
    Then status 201

  @CreateJobWithParams
  @Ignore
  Scenario: Insert Job
    * def req = read('data/job_request.json')
    * req.name = name
    * req.description = description
    * req.minSalary = minSalary
    * req.maxSalary = maxSalary
    * req.location = location
    Given url company_url +  '/' + companyId + '/jobs'
    And request req
    When method POST
    Then status 201

  @GetJobByIdNotFound
  @Ignore
  Scenario: Get Job By Id
    Given url company_url + '/' + companyId + '/jobs/' + id
    When method GET
    Then status 404
    And match response == '#notnull'
    And match response == '#notnull'
    And match response contains deep {code: '#number'}
    And match response contains deep {status: '#string'}
    And match response contains deep {description: '#string'}

  @GetJobById
  @Ignore
  Scenario: Get Job By Id
    Given url company_url + '/' + companyId + '/jobs/' + id
    When method GET
    Then status 200
    And match response == '#notnull'
    And match response contains deep {company: {id: '#number'}}
    And match response contains deep {id: '#number'}
    And match response contains deep {title: '#string'}
    And match response contains deep {description: '#string'}
    And match response contains deep {minSalary: '#number'}
    And match response contains deep {maxSalary: '#number'}
    And match response contains deep {location: '#string'}

  @UpdateJob
  @Ignore
  Scenario: Update Job
    * def req = read('data/job_request.json')
    * req.title = title
    * req.description = description
    * req.minSalary = minSalary
    * req.maxSalary = maxSalary
    * req.location = location
    Given url company_url + '/' + companyId + '/jobs/' + id
    And request req
    When method PUT
    Then status 200

  @DeleteJob
  @Ignore
  Scenario: Delete Job
    Given url company_url + '/' + companyId + '/jobs/' + id
    When method DELETE
    Then status 204